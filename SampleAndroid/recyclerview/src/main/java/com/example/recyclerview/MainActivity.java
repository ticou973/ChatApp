package com.example.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

RecyclerView recyclerView;
RecyclerView.Adapter adapter;


Personne personne;
ArrayList<Personne> personnes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView =findViewById(R.id.rvtest);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        personnes = new ArrayList<>();


        for (int i = 0; i <10 ; i++) {
            personne = new Personne("titi " + i,"toto " + 10*i);
            personnes.add(personne);
        }

        adapter = new ListeAdapter(personnes);

        recyclerView.setAdapter(adapter);


    }
}
