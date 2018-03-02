package com.example.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


/*
On peut comme autre exemple des pending intent faire des alarmes qui permettent d'excuter du code de service à des moments données.



A partir d'oreo (non réalisé ici car mon tel est API 23 :
1) création d'un channel de notifications
2) création de la notification avec Notification.builder
3) Création de l'intent
4) création d'un TaskStack Builder pour gérer les retours à l'accueil de Mainactivity
5) création du pending
6) envoi de notification au système

 */


public class MainActivity extends AppCompatActivity {

    public int ID_NOTIFICATION = 0;
    public String ID_CHANNEL = "my_channel_01";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Définition d'une notification Channel à partir de Oreo

//création du manager de notification
        final NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
/*
// The user-visible name of the channel.
        CharSequence name = getString(R.string.channel_name);

// The user-visible description of the channel.
        String description = getString(R.string.channel_description);

        int importance = NotificationManager.IMPORTANCE_HIGH;


        //CREATION CHANNEL

        NotificationChannel mChannel = new NotificationChannel(ID_CHANNEL, name, importance);

// Configure the notification channel.
        mChannel.setDescription(description);
        mChannel.enableLights(true);

// Sets the notification light color for notifications posted to this channel, if the device supports this feature.
        mChannel.setLightColor(Color.RED);
        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

        mNotificationManager.createNotificationChannel(mChannel);
*/



        //lancement de la notification dans le channel

        Button b = (Button) findViewById(R.id.launch);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // L'icône sera une petite loupe
                int icon = R.drawable.ic_launcher_background;
                // Le premier titre affiché
                CharSequence tickerText = "Notification de la mort qui tue";
                // Du Texte explicatif
                CharSequence text = "Trop géniale cette Notification";

                // La notification est créée
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this, ID_CHANNEL)
                        .setSmallIcon(icon)
                        .setContentTitle(tickerText)
                        .setContentText(text);

                //pour avoir une big box pour les notifications
                NotificationCompat.InboxStyle inboxStyle =
                        new NotificationCompat.InboxStyle();

                String[] events = new String[6];
// Sets a title for the Inbox in expanded layout
                inboxStyle.setBigContentTitle("Events details:");

// Moves events into the expanded layout
                for (int i=0; i < events.length; i++) {
                    events[i]="voici la ligne N° " + i;
                    inboxStyle.addLine(events[i]);
                }
// Moves the expanded layout object into the notification object.
                mBuilder.setStyle(inboxStyle);



                // l'intent est créé pour le pending intent
                Intent notificationIntent = new Intent(MainActivity.this, Main2Activity.class);



                // The stack builder object will contain an artificial back stack for the started Activity. This ensures that navigating backward from the Activity leads out of your app to the Home screen.
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(MainActivity.this);

                // Adds the back stack for the Intent (but not the Intent itself)
                stackBuilder.addParentStack(MainActivity.class);

                // Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(notificationIntent);

                //gestion Du Pending Intent
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );


                mBuilder.setContentIntent(resultPendingIntent);


// mNotificationId is a unique integer your app uses to identify the notification. For example, to cancel the notification, you can pass its ID number to NotificationManager.cancel().

                mNotificationManager.notify(ID_NOTIFICATION, mBuilder.build());


            }
        });
    }




}
