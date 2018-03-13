package com.example.thierrycouilleault.chatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;


/*
device token Id pour identifier les devices. ici ce n'est que du simple device a voir pour plusieurs
 */


public class LoginActivity extends AppCompatActivity {

    private static final String TAG ="essai" ;
    private TextInputLayout mLoginEmail, mLoginPassword;
    private Button mLoginBtn;
    private Toolbar mToolbar;

    private ProgressDialog mLoginProgress;

    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Firebase auth
        mAuth = FirebaseAuth.getInstance();

        mLoginEmail=findViewById(R.id.login_email);
        mLoginPassword =findViewById(R.id.login_password);

        //database

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        //gestion Toolbar
        mToolbar = findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLoginProgress = new ProgressDialog(this);


        //gestion du bouton pour s'enregistrer

        mLoginBtn = findViewById(R.id.login_btn);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mLoginEmail.getEditText().getText().toString();
                String password = mLoginPassword.getEditText().getText().toString();

                if (!TextUtils.isEmpty(email)||!TextUtils.isEmpty(password)){

                    mLoginProgress.setTitle(getString(R.string.logging_in));
                    mLoginProgress.setMessage(getString(R.string.check_credentials));
                    mLoginProgress.setCanceledOnTouchOutside(false);
                    mLoginProgress.show();


                    logInUser(email, password);
                }

            }
        });

    }

    //Méthdde pour le Login connaissant l'email et le password

    private void logInUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            String deviceToken = FirebaseInstanceId.getInstance().getToken();
                            String current_user_id = mAuth.getCurrentUser().getUid();

                            mUserDatabase.child(current_user_id).child("device_token").setValue(deviceToken).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    mLoginProgress.dismiss();

                                    Intent mainIntent = new Intent (LoginActivity.this, MainActivity.class);

                                    //ce code pour lorsque l'on appuie sur le bouton retour de Android, cela nous envoie sur l'accueil Android et non pas Start Activity
                                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                    startActivity(mainIntent);


                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");

                                    finish();

                                }
                            });





                        } else {

                            mLoginProgress.hide();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });


    }
}
