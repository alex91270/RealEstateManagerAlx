package com.example.realestatemanageralx.helpers;

import com.google.android.gms.maps.model.LatLng;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TypesConversionsTest {

    LatLng latLng;

    @Before
    public void setup() {
        latLng = new LatLng(40,-73);
    }

    @Test
    public void getStringFromTimestampTest() {
        assertEquals(TypesConversions.getStringFromTimestamp(1591105742000L), "2020-06-02");
    }

    @Test
    public void getTimeStampFromStringTest() {
        assertTrue(TypesConversions.getTimeStampFromString("2020-06-01") == 1.5909624E9);
    }

    @Test
    public void getLatLngFromStringTest() {
        assertEquals(TypesConversions.getLatLngFromString("40,-73"), latLng);
    }

    @Test
    public void getStringFromLatLngTest() {
        assertEquals(TypesConversions.getStringFromLatLng(latLng), "40.0,-73.0");
    }

    @Test
    public void formatPriceNicelyTest() {
        assertEquals(TypesConversions.formatPriceNicely(1000000), "1 000 000");
    }

    @Test
    public void roundTest() {
        assertTrue(TypesConversions.round(100.6786876,2) == 100.68);
    }
}