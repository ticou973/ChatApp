package com.example.thierrycouilleault.chatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    TextView tv_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        tv_user_id = findViewById(R.id.tv_user_id);

        String user_id=getIntent().getStringExtra("user_id");

        tv_user_id.setText(user_id);
    }
}
