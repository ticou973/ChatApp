package com.example.thierrycouilleault.chatapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    private String mChatUser, mChatUserName;
    private android.support.v7.widget.Toolbar mChatToolbar;
    private DatabaseReference mRootRef;
    private ActionBar actionBar;

    private TextView mTitleView;
    private TextView mLastSeenView;
    private CircleImageView mProfileImage;

    private FirebaseUser mCurrent_user;
    private DatabaseReference mUserRef;

    private ImageButton mChatSendBtn;
    private ImageButton mChatAddBtn;
    private EditText mChatMessage;
    private RecyclerView mMessagesList;
    private SwipeRefreshLayout mRefreshLayout;


    private final List<Messages> messagesList = new ArrayList<>();
    private LinearLayoutManager mLinearLayout;

    private MessageAdapter messageAdapter;

    private static final int TOTAL_ITEMS_TO_LOAD = 10;
    private int mCurrentPage = 1;

    private int itemPos = 0;
    private String mMlastKey = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mChatUser = getIntent().getStringExtra("user_id");
        mChatUserName = getIntent().getStringExtra("user_name");


        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();

        mChatToolbar = findViewById(R.id.chat_app_bar);
        setSupportActionBar(mChatToolbar);

        actionBar = getSupportActionBar();


        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        getSupportActionBar().setTitle(mChatUserName);


        mRootRef = FirebaseDatabase.getInstance().getReference();

        mUserRef = mRootRef.child("Users").child(mCurrent_user.getUid());


// gestion de la custumisation de l'action bar
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View action_bar_view = inflater.inflate(R.layout.chat_custom_bar, null);

        actionBar.setCustomView(action_bar_view);

        // ----Custum Action bar Items

        mTitleView = findViewById(R.id.custom_bar_title);
        mLastSeenView = findViewById(R.id.custom_bar_seen);
        mProfileImage = findViewById(R.id.custom_bar_image);

        mChatMessage = findViewById(R.id.chat_message);


       //gestion des boutons d'envoi et de message

        mChatAddBtn = findViewById(R.id.chat_add_btn);
        mChatAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        mChatSendBtn = findViewById(R.id.chat_send_btn);
        mChatSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                sendMessage();

            }
        });


        //Gestion du swipe
        mRefreshLayout = findViewById(R.id.message_swipe_layout);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mCurrentPage++;

               itemPos =0; //pour éviter de garder les 10 précédents

                loadMoreMessages();

            }
        });



        //gestion de l'adapter
        messageAdapter = new MessageAdapter(messagesList);

        mMessagesList =findViewById(R.id.messages_list);

        mLinearLayout = new LinearLayoutManager(this);

        mMessagesList.setHasFixedSize(true);
        mMessagesList.setLayoutManager(mLinearLayout);


        mMessagesList.setAdapter(messageAdapter);

        loadMessage();


        mTitleView.setText(mChatUserName);

        mRootRef.child("Users").child(mChatUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String online = dataSnapshot.child("online").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();

                if (online.equals("true")) {

                    mLastSeenView.setText("Online");


                } else {

                    GetTimeAgo getTimeAgo = new GetTimeAgo();

                    long lastTime = Long.parseLong(online);

                    String lastSeenTime = getTimeAgo.getTimeAgo(lastTime, getApplicationContext());


                    mLastSeenView.setText(lastSeenTime);

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mRootRef.child("Chat").child(mCurrent_user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if(!dataSnapshot.hasChild(mChatUser)){

                    Map chatAddMap = new HashMap();
                    chatAddMap.put("seen", false);
                    chatAddMap.put("timestamp", ServerValue.TIMESTAMP);

                    Map chatUserMap = new HashMap();
                    chatUserMap.put("Chat/" + mCurrent_user.getUid() + "/" + mChatUser, chatAddMap);
                    chatUserMap.put("Chat/" + mChatUser + "/" + mCurrent_user.getUid(), chatAddMap);

                    mRootRef.updateChildren(chatUserMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                            if(databaseError != null){

                                Log.d("CHAT_LOG", databaseError.getMessage().toString());
                            }


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
    protected void onStart() {
        super.onStart();


        if (mCurrent_user == null) {

            Toast.makeText(this, "This person doesn't exist !", Toast.LENGTH_SHORT).show();

        } else {

            mUserRef.child("online").setValue(true);

        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mCurrent_user == null) {

            Toast.makeText(this, "This person doesn't exist !", Toast.LENGTH_SHORT).show();

        } else {

            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);

        }
    }






    private void sendMessage (){

        String message = mChatMessage.getText().toString();

        if (!TextUtils.isEmpty(message)){

            String current_user_ref = "messages/" + mCurrent_user.getUid() + "/" + mChatUser;
            String chat_user_ref = "messages/" + mChatUser + "/" + mCurrent_user.getUid();

            DatabaseReference user_message_push = mRootRef.child("messages").child(mCurrent_user.getUid()).child(mChatUser).push();

            String push_id = user_message_push.getKey();

            Map messageMap = new HashMap();
            messageMap.put("message", message);
            messageMap.put("seen", false);
            messageMap.put( "type", "text");
            messageMap.put("time", ServerValue.TIMESTAMP);
            messageMap.put("from", mCurrent_user.getUid());

            Map messageUserMap = new HashMap();
            messageUserMap.put(current_user_ref + "/" + push_id, messageMap);
            messageUserMap.put(chat_user_ref + "/" + push_id, messageMap);

            mRootRef.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                    if(databaseError != null){

                        Log.d("CHAT_LOG", databaseError.getMessage().toString());
                    }


                }
            });

            mChatMessage.setText("");

        }


    }

    private void loadMoreMessages (){
        DatabaseReference messageRef = mRootRef.child("messages").child(mCurrent_user.getUid()).child(mChatUser);

        Query messageQuery = messageRef.orderByKey().endAt(mMlastKey).limitToLast(10);

        messageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Messages message = dataSnapshot.getValue(Messages.class);

                messagesList.add(itemPos++, message);

                if(itemPos == 1){

                    String messageKey = dataSnapshot.getKey();

                    mMlastKey =messageKey;

                }

                messageAdapter.notifyDataSetChanged();

                // Pour placer le ddernier envoyer en bas directement sans scroll.

               // mMessagesList.scrollToPosition(messagesList.size()-1);

                //pour arrêter le refresh
                mRefreshLayout.setRefreshing(false);

                mLinearLayout.scrollToPositionWithOffset(10,0);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void loadMessage (){

        DatabaseReference messageRef = mRootRef.child("messages").child(mCurrent_user.getUid()).child(mChatUser);

        Query messageQuery = messageRef.limitToLast(mCurrentPage*TOTAL_ITEMS_TO_LOAD);


        messageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                itemPos++;

                if(itemPos == 1){

                    String messageKey = dataSnapshot.getKey();

                    mMlastKey =messageKey;

                }

                Messages message = dataSnapshot.getValue(Messages.class);

                messagesList.add(message);
                messageAdapter.notifyDataSetChanged();

                // Pour placer le ddernier envoyer en bas directement sans scroll.

                mMessagesList.scrollToPosition(messagesList.size()-1);

                //pour arrêter le refresh
                mRefreshLayout.setRefreshing(false);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}

