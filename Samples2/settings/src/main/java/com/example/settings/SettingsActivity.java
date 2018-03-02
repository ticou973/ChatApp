package com.example.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by thierrycouilleault on 14/02/2018.
 */

public class SettingsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

}
