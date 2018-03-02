package com.example.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView textView, textView2, textView3, textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);


        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String nb_points = sharedPref.getString("nb_points_gagnés", "ouhou");
        String type_partie = sharedPref.getString("crit_fin_partie", "coucou2");
        String nb_donnes = sharedPref.getString("nb_donnes_gagner", "coucou3");
        Boolean distrib = sharedPref.getBoolean("distrib", true);



        button = findViewById(R.id.button);
        textView=findViewById(R.id.textView);
        textView2=findViewById(R.id.textView2);
        textView3=findViewById(R.id.textView3);
        textView4=findViewById(R.id.textView4);

        textView.setText(type_partie);
        textView2.setText(nb_points);
        textView3.setText(nb_donnes);
        textView4.setText(distrib.toString());



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });



    }
}
