package com.example.realestatemanageralx.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.realestatemanageralx.R;
import com.example.realestatemanageralx.datas.DataHolder;
import com.example.realestatemanageralx.fragments.offers_list.OffersListFragment;
import com.example.realestatemanageralx.helpers.SearchQueryBuilder;
import com.example.realestatemanageralx.model.Filter;
import com.example.realestatemanageralx.model.Property;
import com.example.realestatemanageralx.viewmodels.PropertyViewModel;
import org.florescu.android.rangeseekbar.RangeSeekBar;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ResearchFragment extends Fragment {

    private PropertyViewModel propertyViewModel;
    private ArrayList<Property> filteredPropertiesList = new ArrayList<>();
    private Context context;
    @BindView(R.id.research_spinner_type) Spinner spinnerType;
    @BindView(R.id.research_spinner_location) Spinner spinnerLocation;
    @BindView(R.id.research_seekBar_price) RangeSeekBar seekBarPrice;
    @BindView(R.id.research_seekBar_surface) RangeSeekBar seekBarSurface;
    @BindView(R.id.research_spinner_rooms) Spinner spinnerRooms;
    @BindView(R.id.research_spinner_date) Spinner spinnerDate;
    @BindView(R.id.research_radio_group_sold) RadioGroup radioGroup;
    @BindView(R.id.research_radio_button_on_sale) RadioButton radioButtonOnSale;
    @BindView(R.id.research_radio_button_sold) RadioButton radioButtonSold;
    @BindView(R.id.research_radio_button_both) RadioButton radioButtonBoth;
    @BindView(R.id.research_convenience_checkbox) CheckBox checkBoxConvenience;
    @BindView(R.id.research_switch_schools) Switch switchSchools;
    @BindView(R.id.research_switch_stores) Switch switchStores;
    @BindView(R.id.research_switch_restaurants) Switch switchRestaurants;
    @BindView(R.id.research_switch_parks) Switch switchParks;
    @BindView(R.id.research_switch_subways) Switch switchSubways;
    @BindView(R.id.research_button_offers) Button buttonOffers;
    @BindView(R.id.cardviewConveniences) CardView cardViewSwitches;
    private int minPriceSelected;
    private int maxPriceSelected;
    private int minSurfaceSelected;
    private int maxSurfaceSelected;
    private int minRoomsSelected;
    private String typeSelected;
    private String locationSelected;
    private int spinnerDatePosition;
    private ArrayList<String> typeSpinnerArray = new ArrayList<>();
    private ArrayList<String> locationSpinnerArray = new ArrayList<>();
    private ArrayList<String> roomsSpinnerArray = new ArrayList<>();
    private ArrayList<String> dateSpinnerArray = new ArrayList<>();

    public static ResearchFragment newInstance() {
        return (new ResearchFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_research, container, false);
        context = root.getContext();
        ButterKnife.bind(this, root);
        propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel.class);
        fillValues();
        return root;
    }

    private void fillValues() {

        propertyViewModel.getPropertiesList().observe(this, new Observer<List<Property>>() {
            public void onChanged(@Nullable List<Property> allPropertiesList) {

                int minPrice = 1000000000;
                int maxPrice = 0;
                int minSurface = 1000000000;
                int maxSurface = 0;

                typeSpinnerArray.add("Any");
                ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(context, R.layout.my_spinner, typeSpinnerArray);
                typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerType.setAdapter(typeAdapter);
                spinnerType.setSelection(0);

                locationSpinnerArray.add("Any");
                for (Property prop : allPropertiesList) {
                    if (!locationSpinnerArray.contains(prop.getCity()))
                        locationSpinnerArray.add(prop.getCity());
                    if (!locationSpinnerArray.contains(prop.getDistrict()) && !prop.getDistrict().equals("")) {
                        locationSpinnerArray.add(prop.getDistrict());
                    }
                    if (!typeSpinnerArray.contains(prop.getBuildType()))
                        typeSpinnerArray.add(prop.getBuildType());
                    if (prop.getPrice() > maxPrice) maxPrice = prop.getPrice();
                    if (prop.getPrice() < minPrice) minPrice = prop.getPrice();
                    if (prop.getSurface() > maxSurface) maxSurface = prop.getSurface();
                    if (prop.getSurface() < minSurface) minSurface = prop.getSurface();
                }

                ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(context, R.layout.my_spinner, locationSpinnerArray);
                locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerLocation.setAdapter(locationAdapter);
                spinnerLocation.setSelection(0);

                seekBarPrice.setRangeValues(minPrice, maxPrice);
                seekBarSurface.setRangeValues(minSurface, maxSurface);
                minPriceSelected = minPrice;
                maxPriceSelected = maxPrice;
                minSurfaceSelected = minSurface;
                maxSurfaceSelected = maxSurface;

                seekBarPrice.setTextAboveThumbsColor(R.color.colorPurple);
                seekBarSurface.setTextAboveThumbsColor(R.color.colorPurple);

                roomsSpinnerArray.add("Any");
                for (int i = 1; i < 11; i++) {
                    roomsSpinnerArray.add(String.valueOf(i));
                }

                ArrayAdapter<String> roomsAdapter = new ArrayAdapter<String>(context, R.layout.my_spinner, roomsSpinnerArray);
                roomsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerRooms.setAdapter(roomsAdapter);
                spinnerRooms.setSelection(0);
                minRoomsSelected = 0;
                cardViewSwitches.setVisibility(View.INVISIBLE);
                checkBoxConvenience.setOnClickListener(v -> {
                    if (checkBoxConvenience.isChecked()) {
                        cardViewSwitches.setVisibility(View.VISIBLE);
                    } else {
                        cardViewSwitches.setVisibility(View.INVISIBLE);
                    }
                    filter();
                });

                spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        typeSelected = spinnerType.getSelectedItem().toString();

                        filter();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        locationSelected = spinnerLocation.getSelectedItem().toString();
                        filter();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                spinnerRooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (spinnerRooms.getSelectedItemPosition() != 0) {
                            minRoomsSelected = Integer.valueOf(spinnerRooms.getSelectedItem().toString());
                        }
                        filter();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                seekBarPrice.setOnRangeSeekBarChangeListener((bar, minValue, maxValue) -> {
                    minPriceSelected = (Integer) minValue;
                    maxPriceSelected = (Integer) maxValue;
                    filter();
                });
                seekBarSurface.setOnRangeSeekBarChangeListener((bar, minValue, maxValue) -> {
                    minSurfaceSelected = (Integer) minValue;
                    maxSurfaceSelected = (Integer) maxValue;
                    filter();
                });

                radioButtonOnSale.setChecked(true);
                radioGroup.setOnCheckedChangeListener((group, checkedId) -> filter());
                dateSpinnerArray.add("Any");
                dateSpinnerArray.add("Less than a week ago");
                dateSpinnerArray.add("Less than a month ago");
                dateSpinnerArray.add("Less than a year ago");
                ArrayAdapter<String> dateAdapter = new ArrayAdapter<String>(context, R.layout.my_spinner, dateSpinnerArray);
                dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDate.setAdapter(dateAdapter);
                spinnerDate.setSelection(0);
                spinnerDatePosition = 0;
                spinnerDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        spinnerDatePosition = position;
                        filter();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                switchSchools.setOnCheckedChangeListener((buttonView, isChecked) -> filter());
                switchStores.setOnCheckedChangeListener((buttonView, isChecked) -> filter());
                switchRestaurants.setOnCheckedChangeListener((buttonView, isChecked) -> filter());
                switchParks.setOnCheckedChangeListener((buttonView, isChecked) -> filter());
                switchSubways.setOnCheckedChangeListener((buttonView, isChecked) -> filter());
                buttonOffers.setText(String.valueOf(allPropertiesList.size()) + " Offers found");
                buttonOffers.setOnClickListener(v -> {
                    DataHolder.getInstance().setSearchedPropertiesList(filteredPropertiesList);
                    OffersListFragment offerListFrag = new OffersListFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("filter", "filter");
                    offerListFrag.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.activity_master_frame_layout, offerListFrag, "fragment offer list")
                            .addToBackStack(null)
                            .commit();
                });
            }
        });
    }


    private Filter getTheFilter() {
        Filter filter = new Filter();
        filter.setFilterByType(spinnerType.getSelectedItemPosition() != 0);
        filter.setType(typeSelected);
        filter.setFilterByLocation(spinnerLocation.getSelectedItemPosition() != 0);
        filter.setLocation(locationSelected);
        filter.setMinPrice(minPriceSelected);
        filter.setMaxPrice(maxPriceSelected);
        filter.setMinSurface(minSurfaceSelected);
        filter.setMaxSurface(maxSurfaceSelected);
        filter.setFilterByRooms(spinnerRooms.getSelectedItemPosition() != 0);
        filter.setMinRooms(minRoomsSelected);
        filter.setOnSaleChecked(radioButtonOnSale.isChecked());
        filter.setSoldChecked(radioButtonSold.isChecked());
        filter.setFilterByDate(spinnerDatePosition != 0);
        filter.setDateCase(spinnerDatePosition);
        filter.setFilterByConveniences(checkBoxConvenience.isChecked());
        filter.setFilterBySchool(switchSchools.isChecked());
        filter.setFilterByStores(switchStores.isChecked());
        filter.setFilterByParks(switchParks.isChecked());
        filter.setFilterByRestaurants(switchRestaurants.isChecked());
        filter.setFilterBySubways(switchSubways.isChecked());

        String query = new SearchQueryBuilder().buildQuery(filter);

        return filter;
    }

    private void filter() {
        propertyViewModel.getFilteredPropertiesList(getTheFilter()).observe(this, new Observer<List<Property>>() {
            public void onChanged(@Nullable List<Property> properties) {
                buttonOffers.setText(properties.size() + " Offers found");
                filteredPropertiesList = (ArrayList) properties;
            }
        });
    }

}

