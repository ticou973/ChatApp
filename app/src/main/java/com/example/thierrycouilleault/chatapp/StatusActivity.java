package com.example.thierrycouilleault.chatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public class StatusActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private TextInputLayout mStatus;
    private Button mSaveStatusBtn;
    private TextView mCurrentStatus;

    private DatabaseReference mStatusDatabase;
    private FirebaseUser mCurrentUser;

    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        mStatus = findViewById(R.id.status_text_input);

        String status = getIntent().getStringExtra("status_change");


        mCurrentStatus = findViewById(R.id.current_status);
        mCurrentStatus.setText(status);


        mSaveStatusBtn = findViewById(R.id.save_status_btn);
        mSaveStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Progress

                mProgress = new ProgressDialog(StatusActivity.this);
                mProgress.setTitle(R.string.save_status);
                mProgress.setMessage(getString(R.string.please_wait_saving_status));
                mProgress.setCanceledOnTouchOutside(false);
                mProgress.show();

                String status = mStatus.getEditText().getText().toString();

                mCurrentStatus.setText(status);

                mStatusDatabase.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){

                            mProgress.dismiss();

                            Intent SettingsIntent = new Intent(StatusActivity.this, SettingsActivity.class);
                            startActivity(SettingsIntent);


                        }else{

                            mProgress.hide();
                            Toast.makeText(StatusActivity.this, R.string.error_saving_changes, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

        });


        //gestion de la database
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        String currentUserId = mCurrentUser.getUid();

        mStatusDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

        //gestion de la toolbar
        mToolbar = findViewById(R.id.status_appbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.account_status);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mCurrentUser==null){

            Toast.makeText(this, R.string.person_doesnt_exist, Toast.LENGTH_SHORT).show();

        } else {


            mStatusDatabase.child("online").setValue(true);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCurrentUser==null){

            Toast.makeText(this, R.string.person_doesnt_exist, Toast.LENGTH_SHORT).show();

        } else {

            mStatusDatabase.child("online").setValue(ServerValue.TIMESTAMP);

        }

    }
}
