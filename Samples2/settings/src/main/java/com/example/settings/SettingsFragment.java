package com.example.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.widget.Toast;


public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (key.equals("crit_fin_partie")) {

            sharedPreferences.getString(key,"coucou");

            Toast.makeText(getActivity(), sharedPreferences.getString(key,"coucou"), Toast.LENGTH_LONG).show();

        } else if (key.equals("nb_points_gagnés")){

            sharedPreferences.getString(key,"coucou");

            Toast.makeText(getActivity(), sharedPreferences.getString(key,"coucou"), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }


}