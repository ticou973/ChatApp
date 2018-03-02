package com.example.intent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by thierrycouilleault on 05/02/2018.
 */

public class CoucouReceiver extends BroadcastReceiver {
    private static final String NOM_USER = "Intent.intent.extra.NOM";

    // Déclenché dès qu'on reçoit un broadcast intent qui réponde aux filtres déclarés dans le Manifest
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();

        // On vérifie qu'il s'agit du bon intent
        if(intent.getAction().equals("ACTION_COUCOU")) {
            // On récupère le nom de l'utilisateur
            String nom = intent.getStringExtra(NOM_USER);
            Toast.makeText(context, "Coucou " + nom + " !", Toast.LENGTH_LONG).show();
        }
    }
}
