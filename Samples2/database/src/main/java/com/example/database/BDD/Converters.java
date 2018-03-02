package com.example.database.BDD;

import android.arch.persistence.room.TypeConverter;

/**
 * Created by thierrycouilleault on 23/01/2018.
 */

public class Converters {
    @TypeConverter
    public static int couleurToCodeCouleur(Couleur couleur) {

        if (couleur == Couleur.CARREAU) {

            return 1;

        }else if (couleur == Couleur.COEUR) {

            return 2;
        }else if (couleur == Couleur.PIQUE) {

            return 3;
        }else if (couleur == Couleur.TREFLE) {

            return 4;
        }

        return 5;
    }

    @TypeConverter
    public static Couleur CodeCouleurToCouleur (int codeCouleur) {

        if (codeCouleur == 1) {

            return Couleur.CARREAU;

        }else if (codeCouleur == 2) {

            return Couleur.COEUR;

        }else if (codeCouleur == 3) {

            return Couleur.PIQUE;

        }else if (codeCouleur == 4) {

            return Couleur.TREFLE;
        }

        return null;

    }

}
