package com.example.realestatemanageralx.fragments.create_offer;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import com.example.realestatemanageralx.R;
import com.example.realestatemanageralx.helpers.TypesConversions;
import com.example.realestatemanageralx.model.Property;
import com.example.realestatemanageralx.apis.OnPOICountDone;
import com.example.realestatemanageralx.apis.POICount;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LocationPickerFragment extends Fragment implements OnMapReadyCallback {
    private Context mContext;
    private GoogleMap mMap;
    private View mapView;
    private TypesConversions tc = new TypesConversions();
    private Button validButton;
    private SearchView searchView;
    private Property tempProp;
   private static String apiKey;

    public static LocationPickerFragment newInstance() {
        return (new LocationPickerFragment());
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = this.getActivity();
        View root = inflater.inflate(R.layout.fragment_location_picker, container, false);
        validButton = root.findViewById(R.id.picker_button);
        searchView = root.findViewById(R.id.searchView);
        apiKey = this.getString(R.string.google_maps_key);
        tempProp = (Property) getArguments().getSerializable("prop");
        searchView.setQueryHint("Search for an address...");
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;
                Log.i("alex", "onquerysubmit ");


                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(mContext);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);

                        Log.i("alex", "adresse 0: " + addressList.get(0));

                        if (addressList == null) {
                            Toast.makeText(mContext, "Address not resolved...", Toast.LENGTH_LONG).show();
                        } else {
                            Address address = addressList.get(0);


                            String district = address.getSubLocality();
                            Log.i("alex", "sublocality: " + district);


                            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                          placeMarker(latLng);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.i("alex", e.toString());
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

       return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment locationPickerFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.picker_map_widget);
        locationPickerFragment.getMapAsync(this);
        mapView = locationPickerFragment.getView();

        validButton.setOnClickListener(v -> {
            if (!tempProp.getPois().equals("")) {
                CreateFragment createFrag = new CreateFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("prop", tempProp);
                bundle.putString("action", "modification");
                createFrag.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_master_frame_layout, createFrag, "fragment create")
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(mContext, R.raw.mapstyle_retro);
        mMap.setMapStyle(style);
        LatLng NYLocation = new LatLng(40.777108, -73.971537);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(NYLocation, 12)); //between 1 and 20

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                    placeMarker(latLng);
            }
        });

        if (tempProp.getLocation() != ""){
            placeMarker(new TypesConversions().getLatLngFromString(tempProp.getLocation()));
        }
    }

    private void placeMarker(LatLng latLng) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng));
        tempProp.setLocation(new TypesConversions().getStringFromLatLng(latLng));
        Log.i("alex", "location: " + tempProp.getLocation());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12)); //between 1 and 20

        getDatas(latLng);
    }

    private void getDatas(LatLng latLng) {

        Log.i("alex", "location picker thread: " + Thread.currentThread());

        validButton.setVisibility(View.INVISIBLE);
        validButton.setEnabled(false);

        Handler mainHandler = new Handler(mContext.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                validButton.setVisibility(View.VISIBLE);
                validButton.setEnabled(true);
            }
        };

        Geocoder geocoder = new Geocoder(mContext);
        try {
            List<Address> addressList = geocoder.getFromLocation(
                    latLng.latitude, latLng.longitude, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                tempProp.setCity(address.getLocality());

                if (address.getSubLocality() == null) {
                    tempProp.setDistrict("");
                } else {
                    tempProp.setDistrict(address.getSubLocality());}

                Log.i("alex", "city: " + tempProp.getCity());
                Log.i("alex", "district: " + tempProp.getDistrict());

            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("alex", e.toString());
        }

        POICount poiCount = new POICount(new OnPOICountDone() {
            @Override
            public void OnPoiCountDone(String result) {
                Log.i("alex", "poi stuff done");
                Log.i("alex", "result: " + result);
                tempProp.setPois(result);
                mainHandler.post(myRunnable);
            }
        });

        Executor taskExecutor = Executors.newSingleThreadExecutor();
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                poiCount.requestNearbyPois(apiKey, latLng);
            }
        });


    }
}