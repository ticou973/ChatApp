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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG ="essai" ;
    private TextInputLayout mDisplayName, mEmail, mPassword;
    private Button mRegBtn;
    private Toolbar mToolbar;
    private ProgressDialog mRegProgress;

    private FirebaseAuth mAuth;

    private DatabaseReference mDataBase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        //findview

        mDisplayName = findViewById(R.id.display_name);
        mEmail=findViewById(R.id.reg_email);
        mPassword = findViewById(R.id.reg_password);
        mRegBtn =findViewById(R.id.reg_create_btn);

        //Progress Dialog
        mRegProgress = new ProgressDialog(this);

        //Gestion du btn de register

        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String display_name = mDisplayName.getEditText().getText().toString();
                String email = mEmail.getEditText().getText().toString();
                String password = mPassword.getEditText().getText().toString();

                if (!TextUtils.isEmpty(display_name)||!TextUtils.isEmpty(email)||!TextUtils.isEmpty(password)){

                    mRegProgress.setTitle("Registering User");
                    mRegProgress.setMessage("Please wait while we create your account!");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();

                    register_user(display_name,email,password);

                }

            }

        });

        //toolbar set
        mToolbar = findViewById(R.id.register_toolabr);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    private void register_user(final String display_name, String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser current_user=FirebaseAuth.getInstance().getCurrentUser();
                            String uid = current_user.getUid();

                            //gestion de la database de Firebase

                            mDataBase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                            HashMap<String, String> userMap = new HashMap<>();
                            userMap.put("name", display_name);
                            userMap.put("status", "Hi, I'm using Chat App");
                            userMap.put("image", "default");
                            userMap.put("thumb_image", "default");

                            mDataBase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){

                                        mRegProgress.dismiss();
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");

                                        Intent mainIntent = new Intent (RegisterActivity.this, MainActivity.class);

                                        //ce code pour lorsque l'on appuie sur le bouton retour de Android, cela nous envoie sur l'accueil Android et non pas Start Activity
                                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                        startActivity(mainIntent);

                                    }


                                }
                            });




                        } else {
                            mRegProgress.hide();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });

    }
}
