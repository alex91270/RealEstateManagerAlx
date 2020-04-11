package com.example.realestatemanageralx.helpers;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

//import java.security.Timestamp;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TypesConversions {

    public String getStringFromTimestamp(long timestamp) {

        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(date);
    }

    public double getTimeStampFromString(String dateString) {
        String[] dateArray = dateString.split("-");
        int year = Integer.valueOf(dateArray[0]);
        int month = Integer.valueOf(dateArray[1]) -1;
        int day = Integer.valueOf(dateArray[2]);

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);

        return cal.getTimeInMillis();
    }

    public LatLng getLatLngFromString(String s) {
        String[] coords = s.split(",", -1);
        return new LatLng(Double.valueOf(coords[0]), Double.valueOf(coords[1]));
    }

    public String getStringFromLatLng(LatLng location) {
        return location.latitude + "," + location.longitude;
    }

    public String formatPriceNicely(int price) {
        String priceString = String.valueOf(price);
        String nicePriceString = priceString;

        if (priceString.length() > 3 && priceString.length() < 7) {
            nicePriceString = priceString.substring(0, priceString.length() -3)
                    + " " + priceString.substring(priceString.length() -3, priceString.length());
        }

        if (priceString.length() > 6 && priceString.length() < 10) {
            nicePriceString = priceString.substring(0, priceString.length() -6)
                    + " " + priceString.substring(priceString.length() -6, priceString.length() -3)
                    + " " + priceString.substring(priceString.length() -3, priceString.length());
        }

        return nicePriceString;
    }
}
