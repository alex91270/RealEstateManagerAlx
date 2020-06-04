package com.example.realestatemanageralx.helpers;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TypesConversions {

    /**
     * Takes a timestamp as a Long and returns the date
     * correctly formatted, as a String
     * @param timestamp
     * @return
     */
    public static String getStringFromTimestamp(long timestamp) {

        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(date);
    }

    /**
     * Takes a date, correctly formatted as a String
     * and returns a Timestamp as a Double
     * @param dateString
     * @return
     */
    public static double getTimeStampFromString(String dateString) {
        String[] dateArray = dateString.split("-");
        int year = Integer.valueOf(dateArray[0]);
        int month = Integer.valueOf(dateArray[1]) - 1;
        int day = Integer.valueOf(dateArray[2]);

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, 00, 00, 00);

        return cal.getTimeInMillis() / 1000;
    }

    /**
     * Takes a String describing a location with comma separator
     * and return a LatLng
     * @param s
     * @return
     */
    public static LatLng getLatLngFromString(String s) {
        String[] coords = s.split(",", -1);
        return new LatLng(Double.valueOf(coords[0]), Double.valueOf(coords[1]));
    }

    /**
     * Takes a LatLng
     * and returns a String describing this location with comma separator
     * @param location
     * @return
     */
    public static String getStringFromLatLng(LatLng location) {
        return location.latitude + "," + location.longitude;
    }

    /**
     * Takes a price as an Int and returns this price as a String
     * with a space added every thousand
     * @param price
     * @return
     */
    public static String formatPriceNicely(int price) {
        String priceString = String.valueOf(price);
        String nicePriceString = priceString;

        if (priceString.length() > 3 && priceString.length() < 7) {
            nicePriceString = priceString.substring(0, priceString.length() - 3)
                    + " " + priceString.substring(priceString.length() - 3, priceString.length());
        }

        if (priceString.length() > 6 && priceString.length() < 10) {
            nicePriceString = priceString.substring(0, priceString.length() - 6)
                    + " " + priceString.substring(priceString.length() - 6, priceString.length() - 3)
                    + " " + priceString.substring(priceString.length() - 3, priceString.length());
        }

        return nicePriceString;
    }

    /**
     * Takes a Double and rounds it with as much decimals as "places" asked
     * @param value
     * @param places
     * @return
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
