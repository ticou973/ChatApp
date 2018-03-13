package com.example.thierrycouilleault.chatapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by thierrycouilleault on 28/02/2018.
 */

class SectionsPagerAdapter extends FragmentPagerAdapter{

    private Context ctxApp;


    public SectionsPagerAdapter(FragmentManager fm, Context ctx) {

        super(fm);

        ctxApp = ctx;

    }



    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 2 :
                RequestsFragment requestsFragment=new RequestsFragment();
                return requestsFragment;

            case 0 :
                ChatFragment chatFragment = new ChatFragment();
                return chatFragment;

            case 1 :
                FriendsFragment friendsFragment = new FriendsFragment();
                return friendsFragment;

                default: return null;
        }

    }

    @Override
    public int getCount() {

        //3 pour indiquer le nombre de tabs
        return 3;
    }

    //méthode pour mettre les titres pour les tabs avec un view Pager
    @Override
    public CharSequence getPageTitle (int position){

        switch (position){

            case 2 :
                return ctxApp.getString(R.string.requests);

            case 0 :
                return ctxApp.getString(R.string.chats);

            case 1 :
                return ctxApp.getString(R.string.friends);

                default:
                    return null;
        }


    }

}
