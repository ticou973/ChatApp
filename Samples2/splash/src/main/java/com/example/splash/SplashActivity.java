package com.example.splash;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/*
1) Création de l'activité splash pouuis de l'activité principale
1)bis : sur le xmk indiquer en background la photo de splash dans les drawables
2) dans le manifest mettre le launcher et main sur splash et l'aue sur défaut
3) Création un thread avec la méthode run à l'intérieur puis lancer le thread avec la méthode start
4) dans le finally dyu try catch lancer un intent vers l'activité principale après l'attente du splash

 */





public class SplashActivity extends AppCompatActivity {

    MediaPlayer sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //création d'un son qui va être joué !
        sound = MediaPlayer.create(this, R.raw.splash);
        sound.start();;


        //création d'un Thread pour ne pas être sur le Thread UI
        Thread chrono = new Thread(){
            @Override
            public void run() {
                super.run();

                try {

                    sleep(5000);

                }catch (InterruptedException e){

                    e.printStackTrace();

                }finally {

                    Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                }

            }
        };

        chrono.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

      //arrêt du son
       sound.release();

       //libération de mémoire puis si retour arrière on ne revient pas au splah
       finish();

    }
}
