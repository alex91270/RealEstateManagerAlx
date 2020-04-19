package com.example.realestatemanageralx.fragments.create_offer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.realestatemanageralx.R;
import com.example.realestatemanageralx.helpers.TypesConversions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationPickerFragment extends Fragment implements OnMapReadyCallback {
    private Context mContext;
    private GoogleMap mMap;
    private View mapView;
    private LatLng selectedLocation = null;
    private TypesConversions tc = new TypesConversions();
    private Button validButton;

    public static LocationPickerFragment newInstance() {
        return (new LocationPickerFragment());
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = this.getActivity();
        View root = inflater.inflate(R.layout.fragment_location_picker, container, false);
        validButton = root.findViewById(R.id.picker_button);

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
            CreateFragment createFrag= new CreateFragment();
            Bundle bundle=new Bundle();
            bundle.putString("location",tc.getStringFromLatLng(selectedLocation));
            createFrag.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_master_frame_layout, createFrag, "fragment create")
                    .addToBackStack(null)
                    .commit();
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
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(latLng));
                    selectedLocation = latLng;
                    validButton.setVisibility(View.VISIBLE);
                    validButton.setEnabled(true);
            }
        });
    }
}
