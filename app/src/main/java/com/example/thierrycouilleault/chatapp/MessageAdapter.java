package com.example.thierrycouilleault.chatapp;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by thierrycouilleault on 08/03/2018.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder>{

    private List<Messages> mMessagesList;
    private FirebaseAuth mAuth;

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
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

        mAuth = FirebaseAuth.getInstance();



        //pour ne pas que cela crash avec un nul object
        if (mAuth.getCurrentUser() != null){


            String current_user_id = mAuth.getCurrentUser().getUid();





            Messages c = mMessagesList.get(position);
            String from_user = c.getFrom();

            Log.d("UID", from_user);

            if(from_user.equals(current_user_id)){

                holder.messageText.setBackgroundColor(Color.WHITE);
                holder.messageText.setTextColor(Color.BLACK);

            }else{

                holder.messageText.setBackgroundResource(R.drawable.message_text_background);
                holder.messageText.setTextColor(Color.WHITE);



            }

            holder.messageText.setText(c.getMessage());



        }



    }

    @Override
    public int getItemCount() {
        return mMessagesList.size();
    }
}

class MessageViewHolder extends RecyclerView.ViewHolder {

    public TextView messageText;
    public CircleImageView profileImage;


    public MessageViewHolder(View itemView) {
        super(itemView);

        messageText = itemView.findViewById(R.id.message_text_layout);
        profileImage = itemView.findViewById(R.id.message_profile_layout);


    }
}
