package com.example.thierrycouilleault.chatapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Toolbar mToolbar;

    private ViewPager mViewPager;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private TabLayout mTabLayout;

    private DatabaseReference mUserRef;
    private DatabaseReference mFriendsRef;

    private String mCurrentUserId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();


        //pour ne pas que cela crash avec un nul object
        if (mAuth.getCurrentUser() != null) {
            mCurrentUserId = mAuth.getCurrentUser().getUid();

            mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUserId);
            mFriendsRef = FirebaseDatabase.getInstance().getReference().child("Friends");

        }

        //gestion de la toolbar
        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("ChatApp");



        //gestion de tabs
        mViewPager = findViewById(R.id.tab_pager);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mTabLayout = findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        //Pas d'amis

        mFriendsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!dataSnapshot.hasChild(mCurrentUserId)){

                    CharSequence options[] = new CharSequence[] {"Yes, I want it...", "No, that's sucks"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    builder.setTitle("Do you want invite some friends ?");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            if (i==0){

                                Intent usersIntent = new Intent (MainActivity.this, UsersActivity.class);
                                startActivity(usersIntent);

                            } else if(i==1) {

                                Toast.makeText(MainActivity.this, "Friendship is important...", Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                    builder.show();


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }


    @Override
    protected void onStart() {
        super.onStart();



        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser==null){

            sendToStart();

        } else {

            mUserRef.child("online").setValue(true);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null) {

            //Pour mettre un tampon pour la dernière fois vue
            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);

        }
    }

    private void sendToStart() {
        Intent startIntent = new Intent(MainActivity.this, StartActivity.class);
        startActivity(startIntent);
        finish();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId()==R.id.main_logout_btn) {

            FirebaseAuth.getInstance().signOut();

            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);

            sendToStart();

        } else if (item.getItemId()==R.id.main_settings_btn) {

            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        } else if (item.getItemId()==R.id.main_all_users_btn) {

            Intent usersIntent = new Intent (MainActivity.this, UsersActivity.class);
            startActivity(usersIntent);

        }

        return true;
    }
}
