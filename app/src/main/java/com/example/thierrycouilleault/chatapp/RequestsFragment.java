package com.example.thierrycouilleault.chatapp;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class RequestsFragment extends android.support.v4.app.Fragment {


    private RecyclerView mReqList;

    private DatabaseReference mReqDatabase;
    private DatabaseReference mUsersDatabase;

    private FirebaseAuth mAuth;

    private String mCurrent_user_id;

    private View mMainView;



    public RequestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMainView = inflater.inflate(R.layout.fragment_requests, container, false);

        mAuth = FirebaseAuth.getInstance();

        //pour ne pas que cela crash avec un nul object
        if (mAuth.getCurrentUser() != null) {

            mCurrent_user_id = mAuth.getCurrentUser().getUid();

        }


        mReqDatabase = FirebaseDatabase.getInstance().getReference().child("Friend_req/").child(mCurrent_user_id);
        mReqDatabase.keepSynced(true);

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUsersDatabase.keepSynced(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        mReqList = mMainView.findViewById(R.id.req_list);
        mReqList.setHasFixedSize(true);
        mReqList.setLayoutManager(linearLayoutManager);


        // Inflate the layout for this fragment
        return mMainView;
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Requests, RequestsFragment.ReqViewHolder> firebaseReqAdapter = new FirebaseRecyclerAdapter<Requests, RequestsFragment.ReqViewHolder>(
                Requests.class,
                R.layout.user_req_single_layout,
                RequestsFragment.ReqViewHolder.class,
                mReqDatabase
        ) {
            @Override
            protected void populateViewHolder(final ReqViewHolder viewHolder, Requests model, int position) {

                //gestion de la BD de req pour connaitre la date de req

                viewHolder.setDate("Request sent : " + model.getRequest_date());



                //gestion de la BD pour les infos "Users
                final String list_user_id = getRef(position).getKey();


                mUsersDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        final String userName = dataSnapshot.child("name").getValue().toString();
                        String userThumb = dataSnapshot.child("thumb_image").getValue().toString();

                        viewHolder.setName(userName);
                        viewHolder.setUserImage(userThumb, getContext());

                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                Intent reqIntent = new Intent(getContext(), ProfileActivity.class);
                                reqIntent.putExtra("user_id", list_user_id);
                                reqIntent.putExtra("user_name", userName);
                                startActivity(reqIntent);

                            }
                        });


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

        };

        mReqList.setAdapter(firebaseReqAdapter);

    }

    public static class ReqViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public ReqViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDate(String date){

            TextView reqDate = mView.findViewById(R.id.req_user_date);
            reqDate.setText(date);

        }

        public void setName(String name){

            TextView userNameView = mView.findViewById(R.id.req_user_name);
            userNameView.setText(name);

        }

        public void setUserImage(String thumb_image, Context ctx){

            CircleImageView userImageView = mView.findViewById(R.id.req_user_image);
            Picasso.with(ctx).load(thumb_image).placeholder(R.drawable.default_avatar).into(userImageView);

        }

    }

}
