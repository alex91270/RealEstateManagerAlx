package com.example.realestatemanageralx.fragments.offers_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.realestatemanageralx.R;
import com.example.realestatemanageralx.model.OfferMedia;
import com.example.realestatemanageralx.model.Property;
import com.example.realestatemanageralx.viewmodels.OfferMediaViewModel;
import com.example.realestatemanageralx.viewmodels.PropertyViewModel;

import java.util.ArrayList;
import java.util.List;

public class OffersListFragment extends Fragment {

    private PropertyViewModel propertyViewModel;
    private List<Property> propertiesList = new ArrayList<>();
    private OfferMediaViewModel mediaViewModel;
    private List<OfferMedia> mediasList = new ArrayList<>();

    public static OffersListFragment newInstance() {
        return (new OffersListFragment());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_list_offers, container, false);

        initObservers();

        return root;
    }





    private void initObservers() {
        propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel.class);
        propertyViewModel.getProjectsList().observe(this, new Observer<List<Property>>() {
            public void onChanged(@Nullable List<Property> properties) {
                propertiesList = properties;

            }
        });

        mediaViewModel = ViewModelProviders.of(this).get(OfferMediaViewModel.class);
        mediaViewModel.getAllMedias().observe(this, new Observer<List<OfferMedia>>() {
            public void onChanged(@Nullable List<OfferMedia> medias) {
                mediasList = medias;
            }
        });
    }

    private void updateRecycler() {

    }
}
