package com.example.thierrycouilleault.chatapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by thierrycouilleault on 08/03/2018.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder>{

    private List<Messages> mMessagesList;

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

        Messages c = mMessagesList.get(position);
        holder.messageText.setText(c.getMessage());

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
