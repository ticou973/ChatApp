package ticou973.sampleandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    Button button, button3;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        constraintLayout=findViewById(R.id.clayout);

        tv = findViewById(R.id.tv);

        button = findViewById(R.id.button);
        button3=findViewById(R.id.button3);

        final Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_base);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // On crée un utilitaire de configuration pour cette animation


// On l'affecte au widget désiré, et on démarre l'animation

                tv.startAnimation(animation);

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(intent);
            }
        });


        //Evenementiel avec les animations

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
}
