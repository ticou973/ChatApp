package com.example.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by thierrycouilleault on 01/02/2018.
 */

class ListeAdapter extends RecyclerView.Adapter<ListeAdapter.ViewHolder> {

    ArrayList<Personne> personnes = new ArrayList<>();


    public ListeAdapter(ArrayList<Personne> personnes) {
        this.personnes=personnes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tv.setText(personnes.get(position).nom);
        holder.tv1.setText(personnes.get(position).prenom);

    }

    @Override
    public int getItemCount() {
        return personnes.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv, tv1;

        public ViewHolder(View itemView) {
            super(itemView);

            tv = itemView.findViewById(R.id.textView);
            tv1 = itemView.findViewById(R.id.textView2);


        }
    }
}
