package com.example.thierrycouilleault.chatapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AppCompatActivity {

    private String mChatUser, mChatUserName;
    private android.support.v7.widget.Toolbar mChatToolbar;
    private DatabaseReference mRootRef;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mChatUser =getIntent().getStringExtra("user_id");
        mChatUserName =getIntent().getStringExtra("user_name");



        mChatToolbar =findViewById(R.id.chat_app_bar);
        setSupportActionBar(mChatToolbar);

        actionBar = getSupportActionBar();


       actionBar.setDisplayHomeAsUpEnabled(true);
       actionBar.setDisplayShowCustomEnabled(true);

        getSupportActionBar().setTitle(mChatUserName);


        mRootRef = FirebaseDatabase.getInstance().getReference();

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View action_bar_view = inflater.inflate(R.layout.chat_custom_bar,null);

        actionBar.setCustomView(action_bar_view);



    }
}
