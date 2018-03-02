package com.example.database.BDD;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

/**
 * Created by thierrycouilleault on 23/01/2018.
 */


@Database(entities ={Partie.class, Donne.class }, version =1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract PartieDao partieDao();
    public abstract DonneDao donneDao();


}
