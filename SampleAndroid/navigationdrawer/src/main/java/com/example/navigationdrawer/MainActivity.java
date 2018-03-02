package com.example.navigationdrawer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ListView mDrawerList;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout=findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //permet d'avoir le bouton de retour à Home

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //mettre un listener sur les itesm de la navigation view

        navigationView = findViewById(R.id.navigation);

        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id==R.id.it_preferences) {
            Toast.makeText(this, "preférences", Toast.LENGTH_SHORT).show();
        }else if (id==R.id.it_mode) {
            Toast.makeText(this, "modes", Toast.LENGTH_SHORT).show();
        }else if (id==R.id.it_noter_app) {
            Toast.makeText(this, "notes", Toast.LENGTH_SHORT).show();

        }else if (id==R.id.it_plus) {
            Toast.makeText(this, "plus", Toast.LENGTH_SHORT).show();
        }

        return false;
    }
}
