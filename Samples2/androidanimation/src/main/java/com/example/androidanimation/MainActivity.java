package com.example.androidanimation;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/*
pour faire une share activity il faut aller dans style et mettre window content transitions
Mettre les "transitions name" dans les éléménets dans le layout main
puis reporter les mêmes noms dans l'autre layout
Voir ensuite dans le OnclickListener plus bas

 */



public class MainActivity extends AppCompatActivity {

private RelativeLayout mListLayout;
private TextView mNameText;
private TextView mDescText;
private ImageView mProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListLayout = findViewById(R.id.relativeLayout);
        mNameText = findViewById(R.id.profile_name);
        mDescText = findViewById(R.id.profile_desc);
        mProfileImage = findViewById(R.id.profile_image);


        mListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharedIntent = new Intent(MainActivity.this, SharedAnimationActivity.class);

                //Pour ajouter les transitions

                Pair[] pairs = new Pair[3];
                pairs[0] = new Pair<View,String> (mProfileImage,"imageTransition");
                pairs[1] = new Pair<View,String> (mNameText,"nameTransition");
                pairs[2] = new Pair<View,String> (mDescText,"descTransition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);


                startActivity(sharedIntent, options.toBundle());


            }
        });

    }
}
