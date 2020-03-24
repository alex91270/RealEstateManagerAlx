package com.example.realestatemanageralx.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.realestatemanageralx.R;
import com.example.realestatemanageralx.fragments.loans.LoanPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class LoanFragment extends Fragment {

    public static LoanFragment newInstance() {
        return (new LoanFragment());
    }



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_loan, container, false);

        ViewPager viewPager = root.findViewById(R.id.container);
        TabLayout tabLayout = root.findViewById(R.id.tabs);

        PagerAdapter pagerAdapter = new LoanPagerAdapter(getFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager)
        {
            @Override
            public void onTabSelected(TabLayout.Tab tabSelected){
                Log.i("alex", "tab tapped");
                viewPager.setCurrentItem(tabSelected.getPosition());
        }

            @Override
            public void onTabUnselected(TabLayout.Tab tabSelected){
            }

            @Override
            public void onTabReselected(TabLayout.Tab tabSelected){
            }
        });

        return root;
    }

}
