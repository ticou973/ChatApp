package com.example.menu;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    public android.support.v7.widget.Toolbar toolbar;
    public ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.tv_HW);

        toolbar = findViewById(R.id.toolbar1);


        //register le menu contextuel
        registerForContextMenu(textView);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

    }


    //Menu d'options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        //R.menu.menu est l'id de notre menu
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.it_preferences:
                Toast.makeText(this, "Préférences", Toast.LENGTH_SHORT).show();

                return true;

            case R.id.it_noter_app:
                Toast.makeText(this, "Noter", Toast.LENGTH_SHORT).show();

                return true;

        }
        return super.onOptionsItemSelected(item);
    }






    // Menu contextuel (voir le register plus haut)
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, "Supprimer cet élément");
        menu.add(Menu.NONE, Menu.FIRST+1, Menu.NONE, "Retour");



    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case Menu.FIRST:
                Toast.makeText(this, "Premier", Toast.LENGTH_SHORT).show();

            case Menu.FIRST+1:
                Toast.makeText(this, "deuxième", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onContextItemSelected(item);
    }

}
