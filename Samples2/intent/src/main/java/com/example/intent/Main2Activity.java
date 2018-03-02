package com.example.intent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    TextView tv, tv2, tv3;
    private static final String TAG = "MainActivity";
    private static final String TAG2 = "MainActivitybis";

    int entier;

    Button bt4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tv = findViewById(R.id.textView);
        tv2=findViewById(R.id.textView2);
        tv3=findViewById(R.id.textView3);

        bt4 =findViewById(R.id.button4);

        Intent intent = getIntent();

        Bundle bundle=intent.getExtras();

        String  message = bundle.getString(TAG);

        int entier = bundle.getInt(TAG2);

        //Autres solutions avec intent.getString et intentgetInt

        tv.setText(message);
        tv2.setText(String.valueOf(entier));

        Contact c = bundle.getParcelable(TAG);

        tv3.setText(c.getmNom()+" "+c.getmPrenom()+ " " + String.valueOf(c.getmNumero()));



        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result = new Intent();

                setResult(RESULT_OK, result);
                finish();

            }
        });





    }
}
