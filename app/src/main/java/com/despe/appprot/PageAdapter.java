package com.despe.appprot;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    // 1 - Default Constructor
    public PageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // 3 - Page to return
        switch (position){
            case 0: //Page number 1
                return FragmentProfil.newInstance();
            case 1: //Page number 2
                return FragmentAffichage.newInstance();
            case 2: //Page number 3
                return FragmentHistorique.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return(3); // 2 - Number of page to show
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: //Page number 1
                return "Profil";
            case 1: //Page number 2
                return "Affichage";
            case 2: //Page number 3
                return "Historique";
            default:
                return null;
        }
    }

    @Override
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }
}
