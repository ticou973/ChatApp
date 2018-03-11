package com.example.thierrycouilleault.chatapp;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by thierrycouilleault on 08/03/2018.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder>{

    private List<Messages> mMessagesList;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;

    public MessageAdapter(List<Messages> mMessagesList) {
        this.mMessagesList = mMessagesList;
    }


    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_single_layout, parent, false);

        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder holder, int position) {

        mAuth = FirebaseAuth.getInstance();

        //pour ne pas que cela crash avec un nul object
        if (mAuth.getCurrentUser() != null){


            String current_user_id = mAuth.getCurrentUser().getUid();

            Messages c = mMessagesList.get(position);
            String from_user = c.getFrom();
            String message_type = c.getType();


            mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(from_user);

            mUserDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String name = dataSnapshot.child("name").getValue().toString();
                    String image = dataSnapshot.child("thumb_image").getValue().toString();

                    holder.messageUserName.setText(name);

                    Picasso.with(holder.profileImage.getContext()).load(image)
                            .placeholder(R.drawable.default_avatar).into(holder.profileImage);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



            if(message_type.equals("text")){

                if(from_user.equals(current_user_id)){

                    holder.messageText.setBackgroundColor(Color.WHITE);
                    holder.messageText.setTextColor(Color.BLACK);

                }else{

                    holder.messageText.setBackgroundResource(R.drawable.message_text_background);
                    holder.messageText.setTextColor(Color.WHITE);

                }


                holder.messageText.setText(c.getMessage());
                holder.messageImage.setVisibility(View.INVISIBLE);


            }else if (message_type.equals("image")){

                holder.messageText.setVisibility(View.INVISIBLE);

                Picasso.with(holder.profileImage.getContext()).load(c.getMessage()).into(holder.messageImage);


            }

        }

    }

    @Override
    public int getItemCount() {
        return mMessagesList.size();
    }
}

class MessageViewHolder extends RecyclerView.ViewHolder {

    public TextView messageText, messageUserName;
    public CircleImageView profileImage;
    public ImageView messageImage;


    public MessageViewHolder(View itemView) {
        super(itemView);

        messageText = itemView.findViewById(R.id.message_text_layout);
        profileImage = itemView.findViewById(R.id.message_profile_layout);
        messageImage = itemView.findViewById(R.id.message_image_layout);
        messageUserName = itemView.findViewById(R.id.message_name_layout);


    }
}
