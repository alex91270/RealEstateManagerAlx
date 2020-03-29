package com.example.realestatemanageralx.helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateToTimestamp {

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
}
