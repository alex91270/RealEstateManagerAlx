package com.example.realestatemanageralx.helpers;

import com.google.android.gms.maps.model.LatLng;

public class LatLngToString {

    public LatLng getLatLngFromString(String s) {
        String[] coords = s.split(",", -1);
        return new LatLng(Double.valueOf(coords[0]), Double.valueOf(coords[1]));
    }

    public String getStringFromLatLng(LatLng location) {
        return location.latitude + "," + location.longitude;
    }
}
