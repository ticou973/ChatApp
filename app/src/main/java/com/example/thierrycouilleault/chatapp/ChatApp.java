package com.example.thierrycouilleault.chatapp;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by thierrycouilleault on 05/03/2018.
 */
/* Cette classe application est pour gérer les Offline capabilities (dans ce cas des images par Picasso ou d'autres données dans Settinsgs
Attention de bien mettre le nom dans le manifest de l'application (ici ChapApp)
et de mettre dans le gradle le bon compile pour Okhttp


plus loin gestion du Online
*/

public class ChatApp extends Application {

    private DatabaseReference mUsersDatabase;
    private FirebaseAuth mAuth;
    private String mCurrentUserId;

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        //Picasso

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this,Integer.MAX_VALUE));
        Picasso build = builder.build();
        build.setIndicatorsEnabled(true);
        build.setLoggingEnabled(true);
        Picasso.setSingletonInstance(build);

        //gestion de Online

        mAuth = FirebaseAuth.getInstance();

        //mCurrentUserId = mAuth.getCurrentUser().getUid();


        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());

        /*mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot != null){

                    mUsersDatabase.child("online").onDisconnect().setValue(false);

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


    }
}
