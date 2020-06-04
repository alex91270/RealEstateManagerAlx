package com.example.realestatemanageralx.helpers;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class UtilsTest {

    @Test
    public void convertDollarToEuro() {
        assertEquals(Utils.convertDollarToEuro(100, 0.812), (int) Math.round(100 * 0.812));
    }

    @Test
    public void convertEuroToDollar() {
        assertEquals(Utils.convertEuroToDollar(100, 0.812), (int) Math.round(100 / 0.812));
    }

    @Test
    public void getTodayDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
        assertEquals(Utils.getTodayDate(), dateFormat.format(new Date()) );
    }
}