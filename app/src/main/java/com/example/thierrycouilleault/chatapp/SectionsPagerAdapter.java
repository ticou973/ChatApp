package com.example.thierrycouilleault.chatapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by thierrycouilleault on 28/02/2018.
 */

class SectionsPagerAdapter extends FragmentPagerAdapter{
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                RequestsFragment requestsFragment=new RequestsFragment();
                return requestsFragment;

            case 1 :
                ChatFragment chatFragment = new ChatFragment();
                return chatFragment;

            case 2 :
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
    public CharSequence getPageTitle (int position){

        switch (position){

            case 0 :
                return "REQUESTS";

            case 1 :
                return "CHATS";

            case 2 :
                return "FRIENDS";

                default:
                    return null;
        }


    }

}