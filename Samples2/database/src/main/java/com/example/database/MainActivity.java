package com.example.database;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.database.BDD.AppDatabase;
import com.example.database.BDD.Partie;

import java.util.List;


/* Dans un premier temps, ajout
1) dans le Build Gradle des dépendencies pour Room
2) Créeation des entities
3) Création lorsque qu'un besoin se fait des CRUID dans entityDAO (interface)
4) Création de AppDatabase pour référencer les entities et DAO
5) utilisation dans Mainactivity de la DB

NB : utilisation d'un "converters" pour les cas où les données ne sont pas simples (ex ici d'une couleur). on le déclare aussi dans la classe appDatabase
NB1 : Non traité ici
a) prefix : qui permet d'avoir des embedded de mêmes types dans une même db
b) unique = true : qui permet de spécifier que l'on ne veut pas une condition (ex même nom et prénom dans la table)
c) index : pour indexer la db

NB3 : on peut aussi mettre dans la création de la db une fonction  .allowMainThreadQueries() pour être sur le même Thread que l'UI même si npn conseillé


 */

public class MainActivity extends AppCompatActivity {

    public List<Partie> parties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//Utilisation de la db
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        //utilisation d'une des méthodes définies dans le dao.

        parties =db.partieDao().getAllParties();




    }
}
