package com.example.realestatemanageralx.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.realestatemanageralx.R;
import com.example.realestatemanageralx.model.OfferMedia;
import com.example.realestatemanageralx.model.Property;
import com.example.realestatemanageralx.viewmodels.OfferMediaViewModel;
import com.example.realestatemanageralx.viewmodels.PropertyViewModel;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;

public class ResearchFragment extends Fragment {
    private PropertyViewModel propertyViewModel;
    private List<Property> propertiesList = new ArrayList<>();
    private OfferMediaViewModel mediaViewModel;
    private List<OfferMedia> mediasList = new ArrayList<>();
    private RangeSeekBar seekBar;

    public static ResearchFragment newInstance() {
        return (new ResearchFragment());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_research, container, false);

        seekBar=root.findViewById(R.id.seekBar1);
        seekBar.setRangeValues(200, 1000);
        seekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                seekBar.setTextAboveThumbsColor(R.color.colorPurple);
                int min = (Integer) minValue;
                Log.i("alex", String.valueOf(min));
            }
        });


        //seekBar1.set
        initObservers();

        return root;
    }

    private void initObservers() {
        propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel.class);
        propertyViewModel.getPropertiesList().observe(this, new Observer<List<Property>>() {
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


}
