package com.example.realestatemanageralx.fragments.offers_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.realestatemanageralx.R;
import com.example.realestatemanageralx.comparators.SortByDate;
import com.example.realestatemanageralx.comparators.SortByPrice;
import com.example.realestatemanageralx.comparators.SortBySurface;
import com.example.realestatemanageralx.model.OfferMedia;
import com.example.realestatemanageralx.model.Property;
import com.example.realestatemanageralx.viewmodels.OfferMediaViewModel;
import com.example.realestatemanageralx.viewmodels.PropertyViewModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OffersListFragment extends Fragment {

    private PropertyViewModel propertyViewModel;
    private List<Property> propertiesList = new ArrayList<>();
    private OfferMediaViewModel mediaViewModel;
    private List<OfferMedia> mediasList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private OffersListRecyclerViewAdapter myAdapter;
    private ImageButton buttonPrice;
    private ImageButton buttonSurface;
    private ImageButton buttonDate;
    private enum SortType{priceUp, priceDown, surfaceUp, surfaceDown, dateUp, dateDown}
    private SortType currentSort = SortType.dateUp;

    public static OffersListFragment newInstance() {
        return (new OffersListFragment());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_list_offers, container, false);
        buttonDate = root.findViewById(R.id.buttonDate);
        buttonPrice = root.findViewById(R.id.buttonPrice);
        buttonSurface = root.findViewById(R.id.buttonSurface);
        mRecyclerView = root.findViewById(R.id.list_of_offers);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        initSortButtons();
        initObservers();

        return root;
    }

    private void initObservers() {
        propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel.class);
        propertyViewModel.getPropertiesList().observe(this, new Observer<List<Property>>() {
            public void onChanged(@Nullable List<Property> properties) {
                propertiesList = properties;
                updateRecycler();
            }
        });

        mediaViewModel = ViewModelProviders.of(this).get(OfferMediaViewModel.class);
        mediaViewModel.getAllMedias().observe(this, new Observer<List<OfferMedia>>() {
            public void onChanged(@Nullable List<OfferMedia> medias) {
                mediasList = medias;
                updateRecycler();
            }
        });
    }

    private void updateRecycler() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        myAdapter = new OffersListRecyclerViewAdapter(propertiesList, mediasList, fm);
        mRecyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

    private void initSortButtons() {
        buttonSurface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonPrice.setImageResource(R.drawable.price_up_un);
                buttonDate.setImageResource(R.drawable.date_up_un);

                if (currentSort.equals(SortType.surfaceUp)){
                    buttonSurface.setImageResource(R.drawable.surface_down_sel);
                    Collections.sort(propertiesList, Collections.reverseOrder(new SortBySurface()));
                    currentSort = SortType.surfaceDown;
                } else {
                    buttonSurface.setImageResource(R.drawable.surface_up_sel);
                    Collections.sort(propertiesList, new SortBySurface());
                    currentSort = SortType.surfaceUp;
                }
                updateRecycler();
            }
        });

        buttonPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSurface.setImageResource(R.drawable.surface_up_un);
                buttonDate.setImageResource(R.drawable.date_up_un);

                if (currentSort.equals(SortType.priceUp)){
                    buttonPrice.setImageResource(R.drawable.price_down_sel);
                    Collections.sort(propertiesList, Collections.reverseOrder(new SortByPrice()));
                    currentSort = SortType.priceDown;
                } else {
                    buttonPrice.setImageResource(R.drawable.price_up_sel);
                    Collections.sort(propertiesList, new SortByPrice());
                    currentSort = SortType.priceUp;
                }
                updateRecycler();
            }
        });

        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSurface.setImageResource(R.drawable.surface_up_un);
                buttonPrice.setImageResource(R.drawable.price_up_un);

                if (currentSort.equals(SortType.dateUp)){
                    buttonDate.setImageResource(R.drawable.date_down_sel);
                    Collections.sort(propertiesList, Collections.reverseOrder(new SortByDate()));
                    currentSort = SortType.dateDown;
                } else {
                    buttonDate.setImageResource(R.drawable.date_up_sel);
                    Collections.sort(propertiesList, new SortByDate());
                    currentSort = SortType.dateUp;
                }
                updateRecycler();
            }
        });
    }
}
