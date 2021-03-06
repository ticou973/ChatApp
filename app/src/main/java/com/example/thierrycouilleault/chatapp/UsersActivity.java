package com.example.thierrycouilleault.chatapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private RecyclerView mUsersList;

    private DatabaseReference usersDatabase;
    private DatabaseReference mUserRef;
    private FirebaseUser mCurrent_user;
    private String mCurrentUserUid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        mToolbar = findViewById(R.id.users_appbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.users);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();
        mCurrentUserUid = mCurrent_user.getUid();


        usersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUserUid);

        mUsersList = findViewById(R.id.users_list);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(new LinearLayoutManager(this));



    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mCurrent_user==null){

            Toast.makeText(getApplicationContext(), R.string.person_doesnt_exist, Toast.LENGTH_SHORT).show();

        } else {

            mUserRef.child("online").setValue(true);

        }


        FirebaseRecyclerAdapter<Users, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>(
                Users.class,
                R.layout.users_row,
                UsersViewHolder.class,
                usersDatabase

        ) {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, Users model, final int position) {

               viewHolder.setDisplayName(model.getName());
               viewHolder.setDisplayStatus(model.getStatus());
               viewHolder.setDisplayImage(model.getThumb_image(),getApplicationContext());

               viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       String user_id = getRef(position).getKey();

                       Intent profileIntent = new Intent (UsersActivity.this, ProfileActivity.class);
                       profileIntent.putExtra("user_id", user_id);
                       startActivity(profileIntent);
                   }
               });

            }
        };


        mUsersList.setAdapter(firebaseRecyclerAdapter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCurrent_user==null){

            Toast.makeText(this, R.string.person_doesnt_exist, Toast.LENGTH_SHORT).show();

        } else {

            usersDatabase.child(mCurrentUserUid).child("online").setValue(ServerValue.TIMESTAMP);

        }

    }




    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView tv_name, tv_status;
        CircleImageView circleImageView;

        public UsersViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDisplayName (String name){
            tv_name =mView.findViewById(R.id.user_single_name);
            tv_name.setText(name);

        }

        public void setDisplayStatus (String status){
            tv_status =mView.findViewById(R.id.user_single_status);
            tv_status.setText(status);

        }

        public void setDisplayImage (String thumb_image, Context ctx){

            circleImageView = mView.findViewById(R.id.user_single_image);
            Picasso.with(ctx).load(thumb_image).placeholder(R.drawable.default_avatar).into(circleImageView);


        }


    }



}
