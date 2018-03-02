package com.example.localisation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Dans le manifest les permissions ont été réalisées

    //On peut aussi mettre au lieu de juste la localisation mettre un ProximityAlert

    public static final int MY_PERMISSIONS_REQUEST_LOCATION=0;
    public LocationManager locationManager;
    public TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tv=findViewById(R.id.tv);


        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        // Register the listener with the Location Manager to receive location updates


        //Pour faire la demande de permissions avec onrequest, une boite de dialogue s'ouvre.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                Toast.makeText(this, "Pour la bonne cause", Toast.LENGTH_SHORT).show();

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

            Toast.makeText(this, "coucou", Toast.LENGTH_SHORT).show();


            return;
        }


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        String locationProvider = LocationManager.GPS_PROVIDER;
        // Or use LocationManager.GPS_PROVIDER

        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);

        tv.setText(lastKnownLocation.toString());

        Toast.makeText(this, lastKnownLocation.toString(), Toast.LENGTH_LONG).show();


    }


    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

                    String locationProvider = LocationManager.NETWORK_PROVIDER;
                    // Or use LocationManager.GPS_PROVIDER

                    Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);

                    Toast.makeText(this, lastKnownLocation.toString(), Toast.LENGTH_LONG).show();



                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    Toast.makeText(this, "Mince alors...", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }



    }

    // Define a listener that responds to location updates
    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            // Called when a new location is found by the network location provider.
            //makeUseOfNewLocation(location);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };

    /*Intent intent = new Intent(this, AlertReceiver.class);

    PendingIntent pending = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

// On ajoute une alerte de proximité si on s'approche ou s'éloigne du bâtiment de Simple IT
locationManager.addProximityAlert(48.872808, 2.33517, 150, -1, pending);

public class AlertReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    // Vaudra true par défaut si on ne trouve pas l'extra booléen dont la clé est LocationManager.KEY_PROXIMITY_ENTERING
    bool entrer = booleanValue(intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, true));
  }
}




*/
}
