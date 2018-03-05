package com.example.thierrycouilleault.chatapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    private TextView profileDisplayName;
    private TextView profileDisplayStatus;
    private TextView profileDisplayTotalFriends;
    private  ImageView profileImage;
    private Button profileSendRequestBtn, profileDeclineRequest;

    private DatabaseReference mUsersDatabase;
    private DatabaseReference mFriendsReqDatabase;
    private DatabaseReference mFriendsDatabase;
    private DatabaseReference mNotificationsDatabase;
    private FirebaseUser mCurrent_user;

    private ProgressDialog mProgressDialog;
    private String mCurrent_state;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //récupération des données de l'Intent
        final String user_id=getIntent().getStringExtra("user_id");

        //Déclaration de la database et positionnement dans la DB.
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
        mFriendsReqDatabase = FirebaseDatabase.getInstance().getReference().child("Friends_req");
        mFriendsDatabase = FirebaseDatabase.getInstance().getReference().child("Friends");
        mNotificationsDatabase = FirebaseDatabase.getInstance().getReference().child("notifications");

        mCurrent_state = "Not friends";

        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();


        //findview
        profileDisplayName = findViewById(R.id.profile_display_name);

        profileDisplayName.setText(user_id);


        profileDisplayStatus = findViewById(R.id.profile_status);

        profileDisplayTotalFriends = findViewById(R.id.profile_totalFriends);

        profileImage = findViewById(R.id.profile_image);


        //gestion du bon d'envoi de la requête
        profileSendRequestBtn = findViewById(R.id.profile_send_req_btn);
        profileSendRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                profileSendRequestBtn.setEnabled(false);

                // -------------- NOT FRIENDS STATE---------

                if (mCurrent_state.equals("Not friends")){

                    mFriendsReqDatabase.child(mCurrent_user.getUid()).child(user_id).child("request_type").setValue("sent")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){

                                        mFriendsReqDatabase.child(user_id).child(mCurrent_user.getUid()).child("request_type").setValue("received")
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                        HashMap<String,String> notificationData = new HashMap<>();
                                                        notificationData.put("from",mCurrent_user.getUid());
                                                        notificationData.put("type", "request");


                                                        mNotificationsDatabase.child(user_id).push().setValue(notificationData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {

                                                                mCurrent_state ="request sent";
                                                                profileSendRequestBtn.setText("Cancel Friend Request");

                                                                profileDeclineRequest.setVisibility(View.INVISIBLE);
                                                                profileDeclineRequest.setEnabled(false);

                                                            }
                                                        });




                                                        Toast.makeText(ProfileActivity.this, "Requests sent successfull", Toast.LENGTH_SHORT).show();

                                                    }
                                                });



                                    }else{

                                        Toast.makeText(ProfileActivity.this, "Failed to send requests", Toast.LENGTH_SHORT).show();
                                    }

                                    profileSendRequestBtn.setEnabled(true);

                                }
                            });

                }

                // -------------- CANCEL REQUEST STATE  ---------

                if (mCurrent_state.equals("request sent")){

                    mFriendsReqDatabase.child(mCurrent_user.getUid()).child(user_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mFriendsReqDatabase.child(user_id).child(mCurrent_user.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    profileSendRequestBtn.setEnabled(true);
                                    mCurrent_state ="Not friends";
                                    profileSendRequestBtn.setText("Send Friend Request");

                                    profileDeclineRequest.setVisibility(View.INVISIBLE);
                                    profileDeclineRequest.setEnabled(false);

                                }
                            });
                        }
                    });
                }

                // ------------REQ RECEIVED STATE ------------

                if (mCurrent_state.equals("req_received")){

                    final String currentDate = DateFormat.getDateTimeInstance().format(new Date());

                    mFriendsDatabase.child(mCurrent_user.getUid()).child(user_id).setValue(currentDate).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mFriendsDatabase.child(user_id).child(mCurrent_user.getUid()).setValue(currentDate).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    mFriendsReqDatabase.child(mCurrent_user.getUid()).child(user_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            mFriendsReqDatabase.child(user_id).child(mCurrent_user.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    profileSendRequestBtn.setEnabled(true);
                                                    mCurrent_state ="friends";
                                                    profileSendRequestBtn.setText(" Unfriend this person");

                                                    profileDeclineRequest.setVisibility(View.INVISIBLE);
                                                    profileDeclineRequest.setEnabled(false);

                                                }
                                            });
                                        }
                                    });


                                }
                            });



                        }
                    });


                }

                //-----------FRIEND STATE------------

                if (mCurrent_state.equals("friends")){

                    mFriendsDatabase.child(mCurrent_user.getUid()).child(user_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mFriendsDatabase.child(user_id).child(mCurrent_user.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    profileSendRequestBtn.setEnabled(true);
                                    mCurrent_state ="Not friends";
                                    profileSendRequestBtn.setText("Send Friend Request");

                                    profileDeclineRequest.setVisibility(View.INVISIBLE);
                                    profileDeclineRequest.setEnabled(false);

                                }
                            });
                        }
                    });
                }
            }
        });


        //gestion du bourton d'envoi du refus de requ^e
        profileDeclineRequest = findViewById(R.id.profile_decline_request);
        profileDeclineRequest.setVisibility(View.INVISIBLE);
        profileDeclineRequest.setEnabled(false);
        profileDeclineRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

        //gestion de la ProgressDialog le temps de chargement des datas

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading User Data.. ");
        mProgressDialog.setMessage("please wait while loading the user data ");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();


        //gestion de l'évolution des données dans la DB user
        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String display_name = dataSnapshot.child("name").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();

                profileDisplayName.setText(display_name);
                profileDisplayStatus.setText(status);

                Picasso.with(ProfileActivity.this).load(image).placeholder(R.drawable.default_avatar_square).into(profileImage);

                // -----------FRIENDS LIST - REQUEST FEATURE

                mFriendsReqDatabase.child(mCurrent_user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.hasChild(user_id)){
                            String req_type = dataSnapshot.child(user_id).child("request_type").getValue().toString();

                            if (req_type.equals("received")){

                                mCurrent_state ="req_received";
                                profileSendRequestBtn.setText("Accept Friend Request");

                                profileDeclineRequest.setVisibility(View.VISIBLE);
                                profileDeclineRequest.setEnabled(true);

                            }else if (req_type.equals("sent")){
                                mCurrent_state = "req_sent";
                                profileSendRequestBtn.setText("Cancel Friend Request");

                                profileDeclineRequest.setVisibility(View.INVISIBLE);
                                profileDeclineRequest.setEnabled(false);
                            }

                            mProgressDialog.dismiss();

                        } else {

                            mFriendsDatabase.child(mCurrent_user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.hasChild(user_id)){
                                        mCurrent_state ="friends";
                                        profileSendRequestBtn.setText(" Unfriend this person");

                                        profileDeclineRequest.setVisibility(View.INVISIBLE);
                                        profileDeclineRequest.setEnabled(false);
                                    }

                                    mProgressDialog.dismiss();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                    mProgressDialog.dismiss();
                                }
                            });


                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
