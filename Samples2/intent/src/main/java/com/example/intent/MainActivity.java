package com.example.intent;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String TAG2 = "MainActivitybis";
    private static final String NOM_USER = "Intent.intent.extra.NOM";



    String message = "Coucou Hello";
    int anInt =4;
    Button bt1, bt2, bt3;
    TextView tv4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bt1 = findViewById(R.id.button);
        bt2 = findViewById(R.id.button2);
        bt3 = findViewById(R.id.button3);

        tv4=findViewById(R.id.textView4);


        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //création d'un Intent explicite
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);

                intent.putExtra(TAG, message);
                intent.putExtra(TAG2, anInt);

                Contact c = new Contact("Dupont", "Dupond", 06);

                intent.putExtra(TAG, c);

                startActivityForResult(intent,0);

            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri telephone = Uri.parse("tel:0606060606");
                Uri sms = Uri.parse("sms:0606060606,0606060607?body=Salut%20les%20potes");
                Uri siteeurosport = Uri.parse("https://www.eurosport.fr/");
                Uri mail = Uri.parse("mailto:jsmith@example.com?subject=A%20Test&body=My%20idea%20is%3A%20%0A");
                Intent secondeActivite = new Intent(Intent.ACTION_SENDTO, mail);


                //Pour savoir si il exite une activité qui gère ce type d'activité
                PackageManager manager = getPackageManager();

                ComponentName component = secondeActivite.resolveActivity(manager);
                // On vérifie que component n'est pas null
                if(component != null) {
                    //Alors c'est qu'il y a une activité qui va gérer l'intent

                    Toast.makeText(MainActivity.this, "ok cela marche", Toast.LENGTH_SHORT).show();

                }


                startActivity(secondeActivite);

            }
        });


        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo a faire fonctionner , pb sur le manifest ?
                Intent intent = new Intent();
                intent.setAction("Intent.intent.action.coucou");
                intent.putExtra(NOM_USER,"Jojo");
                sendBroadcast(intent);


            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0&&resultCode==Main2Activity.RESULT_OK){

            tv4.setText("Tout s'est bien déroulé !");


        } else {
            tv4.setText("oups il y a un problème !");
        }


    }
}
