package com.example.thierrycouilleault.chatapp;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */


public class FriendsFragment extends android.support.v4.app.Fragment {

    private View mMainView;
    private RecyclerView mFriendsList;

    private DatabaseReference mfriendsDatabase;
    private DatabaseReference mUsersDatabase;
    private FirebaseAuth mAuth;
    private String mCurrent_user_id;


    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView =inflater.inflate(R.layout.fragment_friends, container, false);

        mAuth = FirebaseAuth.getInstance();

        //pour ne pas que cela crash avec un nul object
        if (mAuth.getCurrentUser() != null) {

            mCurrent_user_id = mAuth.getCurrentUser().getUid();

        }

        mfriendsDatabase = FirebaseDatabase.getInstance().getReference().child("Friends").child(mCurrent_user_id);
        mfriendsDatabase.keepSynced(true);

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUsersDatabase.keepSynced(true);

        mFriendsList = mMainView.findViewById(R.id.friends_list);
        mFriendsList.setHasFixedSize(true);
        mFriendsList.setLayoutManager(new LinearLayoutManager(getContext()));

        return mMainView;
    }


    @Override
    public void onStart() {
        super.onStart();


        FirebaseRecyclerAdapter<Friends, FriendsViewHolder> friendsRecyclerAdapter = new FirebaseRecyclerAdapter<Friends, FriendsViewHolder>(
                Friends.class,
                R.layout.friends_row,
                FriendsViewHolder.class,
                mfriendsDatabase

        ) {
            @Override
            protected void populateViewHolder(final FriendsViewHolder viewHolder, final Friends model, final int position) {

                viewHolder.setDisplayDate("Friends since :" + model.getDate());

                final String list_user_id = getRef(position).getKey();

                mUsersDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        final String userName = dataSnapshot.child("name").getValue().toString();
                        String userThumb = dataSnapshot.child("thumb_image").getValue().toString();
                        String userStatus = dataSnapshot.child("status").getValue().toString();

                        if (dataSnapshot.hasChild("online")){

                            String userOnLineStatus = dataSnapshot.child("online").getValue().toString();
                            viewHolder.setDisplayOnLine(userOnLineStatus);
                        }

                        viewHolder.setDisplayName(userName);
                        viewHolder.setDisplayImage(userThumb,getContext());
                        viewHolder.setDisplayStatus(userStatus);



                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                //gestion de l'alertDialog

                                CharSequence options[] = new CharSequence[] {"Open Profile", "Send Message"};

                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                builder.setTitle("Select Options");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        if (i==0){

                                            Intent profileIntent = new Intent (getContext(), ProfileActivity.class);
                                            profileIntent.putExtra("user_id", list_user_id);
                                            startActivity(profileIntent);

                                        } else if(i==1) {

                                            Intent chatIntent = new Intent (getContext(), ChatActivity.class);
                                            chatIntent.putExtra("user_id", list_user_id);
                                            chatIntent.putExtra("user_name", userName);
                                            startActivity(chatIntent);
                                        }

                                    }
                                });

                                builder.show();

                            }
                        });

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

        };


        mFriendsList.setAdapter(friendsRecyclerAdapter);

    }


    public static class FriendsViewHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView tv_name, tv_date, tv_status;
        CircleImageView circleImageView;
        ImageView onlineIcon;

        public FriendsViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDisplayName (String name){
            tv_name =mView.findViewById(R.id.friend_single_name);
            tv_name.setText(name);

        }

        public void setDisplayDate (String date){
            tv_date =mView.findViewById(R.id.friend_single_date);
            tv_date.setText(date);

        }

        public void setDisplayImage (String thumb_image, Context ctx){

            circleImageView = mView.findViewById(R.id.friend_single_image);
            Picasso.with(ctx).load(thumb_image).placeholder(R.drawable.default_avatar).into(circleImageView);


        }

        public void setDisplayStatus (String status){
           tv_status = mView.findViewById(R.id.friend_single_status);
           tv_status.setText(status);

        }

        public void setDisplayOnLine(String userOnLineStatus){

            onlineIcon = mView.findViewById(R.id.friend_single_online_icon);

            if (userOnLineStatus.equals("true")){

                onlineIcon.setVisibility(View.VISIBLE);
            } else {

                onlineIcon.setVisibility(View.INVISIBLE);
            }


        }


    }

}
