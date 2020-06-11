package com.example.realestatemanageralx.ui.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.realestatemanageralx.R;
import com.example.realestatemanageralx.ui.fragments.offer_detail.OfferDetailFragment;
import com.example.realestatemanageralx.helpers.TypesConversions;
import com.example.realestatemanageralx.model.Property;
import com.example.realestatemanageralx.viewmodels.PropertyViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import pub.devrel.easypermissions.EasyPermissions;

public class MapViewFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, EasyPermissions.PermissionCallbacks {
    private Context mContext;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private View mapView;
    private static String apiKey;

    private InputMethodManager imm;
    private final int RC_LOCATION_PERM = 123;
    private PropertyViewModel propertyViewModel;
    private List<Property> propertiesList;
    private HashMap<String, Property> markersList = new HashMap<>();

    public static MapViewFragment newInstance() {
        return (new MapViewFragment());
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContext = this.getActivity();
        apiKey = mContext.getString(R.string.google_maps_key);

        View root = inflater.inflate(R.layout.fragment_map_view, container, false);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        //checks and manages location permission
        if (hasLocationPermission()) {
            getMap();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_location), RC_LOCATION_PERM, Manifest.permission.ACCESS_FINE_LOCATION);
        }

    }

    @SuppressWarnings({"MissingPermission"})
    private void getMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map_widget);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();

        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };
        locationManager.requestLocationUpdates("gps", 1000, 50, locationListener);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(mContext, R.raw.mapstyle_retro_poi);
        mMap.setMapStyle(style);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.setOnMarkerClickListener(this);

        initObservers();
    }

    private void placeMarkers() {

        if (mMap != null) {
            mMap.clear();
            markersList.clear();
            IconGenerator iGen = new IconGenerator(mContext);

            for (Property prop : propertiesList) {
                if (prop.getLocation() != null && mMap != null) {

                    Bitmap bitmapIcon = iGen.makeIcon(TypesConversions.formatPriceNicely(prop.getPrice()) + " $");

                    MarkerOptions mo = new MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromBitmap(bitmapIcon))
                            .position(TypesConversions.getLatLngFromString(prop.getLocation()));

                    Marker marker = mMap.addMarker(mo);

                    markersList.put(marker.getId(), prop);
                }
            }
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        long propertyId = Objects.requireNonNull(markersList.get(marker.getId())).getId();
        long agentId = Objects.requireNonNull(markersList.get(marker.getId())).getAgentId();

        OfferDetailFragment offerDetailFrag = new OfferDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("propertyId", propertyId);
        bundle.putLong("agentId", agentId);
        offerDetailFrag.setArguments(bundle);
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_master_frame_layout, offerDetailFrag, "fragment offer detail")
                .addToBackStack(null)
                .commit();

        return false;

    }

    private Boolean hasLocationPermission() {
        return EasyPermissions
                .hasPermissions(mContext, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        Log.i("alex", "location request permission result");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NotNull List<String> perms) {
        getMap();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NotNull List<String> perms) {
        Toast.makeText(mContext, getString(R.string.perm_denied), Toast.LENGTH_LONG).show();
    }

    private void initObservers() {

        if (getArguments() == null) {
            propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel.class);
            propertyViewModel.getPropertiesList().observe(this, new Observer<List<Property>>() {
                public void onChanged(@Nullable List<Property> properties) {
                    propertiesList = properties;
                    LatLng NYLocation = new LatLng(40.777108, -73.971537);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(NYLocation, 12)); //between 1 and 20
                    placeMarkers();
                }
            });

        } else {

            propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel.class);
            propertyViewModel.getPropertyById(getArguments().getLong("propertyId")).observe(this, new Observer<Property>() {
                public void onChanged(@Nullable Property property) {
                    propertiesList = new ArrayList<>();
                    propertiesList.add(property);
                    LatLng location = TypesConversions.getLatLngFromString(property.getLocation());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18)); //between 1 and 20
                    placeMarkers();
                }
            });
        }

    }


}
