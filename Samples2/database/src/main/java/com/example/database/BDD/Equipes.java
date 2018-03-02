package com.example.database.BDD;

import android.arch.persistence.room.ColumnInfo;

/**
 * Created by thierrycouilleault on 22/01/2018.
 */

public class Equipes {

   @ColumnInfo(name="nom_equipeA")
    private String nomequipeA;

   @ColumnInfo(name="nom_joueur1")
   private String nomJoueur1;

   @ColumnInfo(name="nom_joueur2")
   private String nomJoueur2;

   @ColumnInfo(name="nom_equipeB")
    private String nomequipeB;;

    @ColumnInfo(name="nom_joueur3")
    private String nomJoueur3;

    @ColumnInfo(name="nom_joueur4")
    private String nomJoueur4;




// Constructeurs


    public Equipes(String nomequipeA, String nomJoueur1, String nomJoueur2, String nomequipeB, String nomJoueur3, String nomJoueur4) {
        this.nomequipeA = nomequipeA;
        this.nomJoueur1 = nomJoueur1;
        this.nomJoueur2 = nomJoueur2;
        this.nomequipeB = nomequipeB;
        this.nomJoueur3 = nomJoueur3;
        this.nomJoueur4 = nomJoueur4;
    }

    public Equipes(String nomequipeA, String nomequipeB) {
        this.nomequipeA = nomequipeA;
        this.nomequipeB = nomequipeB;
    }

    public Equipes(){
    }




    //Getter et setter


    public String getNomequipeA() {
        return nomequipeA;
    }

    public void setNomequipeA(String nomequipeA) {
        this.nomequipeA = nomequipeA;
    }

    public String getNomJoueur1() {
        return nomJoueur1;
    }

    public void setNomJoueur1(String nomJoueur1) {
        this.nomJoueur1 = nomJoueur1;
    }

    public String getNomJoueur2() {
        return nomJoueur2;
    }

    public void setNomJoueur2(String nomJoueur2) {
        this.nomJoueur2 = nomJoueur2;
    }

    public String getNomequipeB() {
        return nomequipeB;
    }

    public void setNomequipeB(String nomequipeB) {
        this.nomequipeB = nomequipeB;
    }

    public String getNomJoueur3() {
        return nomJoueur3;
    }

    public void setNomJoueur3(String nomJoueur3) {
        this.nomJoueur3 = nomJoueur3;
    }

    public String getNomJoueur4() {
        return nomJoueur4;
    }

    public void setNomJoueur4(String nomJoueur4) {
        this.nomJoueur4 = nomJoueur4;
    }
}
