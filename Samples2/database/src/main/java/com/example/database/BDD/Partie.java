package com.example.database.BDD;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by thierrycouilleault on 13/11/2017.
 */


@Entity
public class Partie {

    // Variables d'instance

    @PrimaryKey(autoGenerate = true)
    private int partieId;


    @Embedded
    private Equipes equipes;

    @ColumnInfo(name ="score_equipeA")
    private int scoreEquipeA;

    @ColumnInfo(name="score_equipeB")
    private int scoreEquipeB;

    @ColumnInfo(name="etat_partie")
    private boolean partieterminee;



    //Méthodes constructeurs

    public Partie(Equipes equipes, int scoreEquipeA, int scoreEquipeB, boolean partieterminee) {
        this.equipes = equipes;
        this.scoreEquipeA = scoreEquipeA;
        this.scoreEquipeB = scoreEquipeB;
        this.partieterminee = partieterminee;
    }

    //Autres méthodes



    //Getter et Setter


    public int getPartieId() {
        return partieId;
    }

    public void setPartieId(int partieId) {
        this.partieId = partieId;
    }


    public Equipes getEquipes() {
        return equipes;
    }

    public void setEquipes(Equipes equipes) {
        this.equipes = equipes;
    }

    public int getScoreEquipeA() {
        return scoreEquipeA;
    }

    public void setScoreEquipeA(int scoreEquipeA) {
        this.scoreEquipeA = scoreEquipeA;
    }

    public int getScoreEquipeB() {
        return scoreEquipeB;
    }

    public void setScoreEquipeB(int scoreEquipeB) {
        this.scoreEquipeB = scoreEquipeB;
    }

    public boolean isPartieterminee() {
        return partieterminee;
    }

    public void setPartieterminee(boolean partieterminee) {
        this.partieterminee = partieterminee;
    }
}
