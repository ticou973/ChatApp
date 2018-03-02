package com.example.thierrycouilleault.samples2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView complete = null;
    private MultiAutoCompleteTextView mcomplete;
    // Notre liste de mots que connaîtra l'AutoCompleteTextView
    private static final String[] COULEUR = new String[] {
            "Bleu", "Vert", "Jaune", "Jaune canari", "Rouge", "Orange"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// On récupère l'AutoCompleteTextView déclaré dans notre layout
        complete = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        complete.setThreshold(2);

        // On associe un adaptateur à notre liste de couleurs…
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, COULEUR);
        // … puis on indique que notre AutoCompleteTextView utilise cet adaptateur
        complete.setAdapter(adapter);

        mcomplete=findViewById(R.id.multiAutoCompleteTextView);
        mcomplete.setThreshold(2);
        mcomplete.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        // On associe un adaptateur à notre liste de couleurs…
        ArrayAdapter<String> madapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, COULEUR);
        // … puis on indique que notre AutoCompleteTextView utilise cet adaptateur
        mcomplete.setAdapter(madapter);




    }
}
