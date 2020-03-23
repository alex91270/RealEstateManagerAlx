package com.example.realestatemanageralx.fragments.loans;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LoanPagerAdapter extends FragmentPagerAdapter {

    public LoanPagerAdapter (FragmentManager fm) {
        super(fm);
    }

    /**
     * getItem is called to instantiate the fragment for the given page.
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        Fragment fm=null;
        switch (position) {
            case 0:
                //If first tab tapped, launch simulator
                fm = new LoanSimulatorFragment();
                break;
            case 1:
                //If second one, launch ability
                fm = new LoanAbilityFragment();
                break;
        }
        //Then builds the fragment
        return fm;
    }

    /**
     * get the number of pages
     * @return
     */
    @Override
    public int getCount() {
        return 2;
    }
}
