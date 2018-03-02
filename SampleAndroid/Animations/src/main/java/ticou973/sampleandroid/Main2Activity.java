package ticou973.sampleandroid;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    TextView tv, tv1;
    Button button;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        constraintLayout=findViewById(R.id.clayout2);

        tv = findViewById(R.id.tva);
        tv1=findViewById(R.id.tv1a);


        button = findViewById(R.id.button2);

        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.animation_layout);
        constraintLayout.setLayoutAnimation(animation);


    }

}
