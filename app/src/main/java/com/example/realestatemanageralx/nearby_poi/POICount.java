package com.example.realestatemanageralx.nearby_poi;

import android.os.StrictMode;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class POICount {

    private OnPOICountDone onPOICountDone;
    private static String result;
    private LatLng location;
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    private int schoolsCount;
    private int storesCount;
    private int parksCount;
    private int restosCount;
    private int subwaysCount;
    private static final int radius = 200;


    public POICount(OnPOICountDone onPOICountDone)  {
        this.onPOICountDone = onPOICountDone;
    }

    public void requestNearbyPois(String apiKey, LatLng loc) {

        Log.i("alex", "request nearby thread: " + Thread.currentThread());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        result = "";
        location = loc;
        Double lat = location.latitude;
        Double lng = location.longitude;

        //********
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();

        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE);

            sb.append("location=" + String.valueOf(lat) + "," + String.valueOf(lng));
            sb.append("&type=restaurant");
            sb.append("&radius=" + String.valueOf(radius));
            sb.append("&key=" + apiKey);

            URL url = new URL(sb.toString());

            Log.i("alex", "url: " + sb.toString() );
            conn = (HttpURLConnection) url.openConnection();
            //opens up an inputStreamReader to intercept the response from that url
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                //builds the JSON data out of that stream
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e("alex", "Error processing Places API URL", e);
        } catch (IOException e) {
            Log.e("alex", "Error connecting to Places API", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            //converts JSON raw data into JSON objects
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("results");

            Log.i("alex", "number restaurants: " + predsJsonArray.length());
            restosCount = predsJsonArray.length();

        } catch (JSONException e) {
            Log.e("alex", "Error processing JSON results", e);
        }

        //********

        conn = null;
        jsonResults = new StringBuilder();

        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE);

            sb.append("location=" + String.valueOf(lat) + "," + String.valueOf(lng));
            sb.append("&type=school");
            sb.append("&radius=" + String.valueOf(radius));
            sb.append("&key=" + apiKey);

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e("alex", "Error processing Places API URL", e);
        } catch (IOException e) {
            Log.e("alex", "Error connecting to Places API", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("results");

            Log.i("alex", "number schools: " + predsJsonArray.length());
            schoolsCount = predsJsonArray.length();

        } catch (JSONException e) {
            Log.e("alex", "Error processing JSON results", e);
        }

        //********

        conn = null;
        jsonResults = new StringBuilder();

        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE);

            sb.append("location=" + String.valueOf(lat) + "," + String.valueOf(lng));
            sb.append("&type=store");
            sb.append("&radius=" + String.valueOf(radius));
            sb.append("&key=" + apiKey);

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e("alex", "Error processing Places API URL", e);
        } catch (IOException e) {
            Log.e("alex", "Error connecting to Places API", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("results");

            Log.i("alex", "number stores: " + predsJsonArray.length());
            storesCount = predsJsonArray.length();

        } catch (JSONException e) {
            Log.e("alex", "Error processing JSON results", e);
        }

        //********

        conn = null;
        jsonResults = new StringBuilder();

        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE);

            sb.append("location=" + String.valueOf(lat) + "," + String.valueOf(lng));
            sb.append("&type=park");
            sb.append("&radius=" + String.valueOf(radius));
            sb.append("&key=" + apiKey);

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e("alex", "Error processing Places API URL", e);
        } catch (IOException e) {
            Log.e("alex", "Error connecting to Places API", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("results");

            Log.i("alex", "number parks: " + predsJsonArray.length());
            parksCount = predsJsonArray.length();

        } catch (JSONException e) {
            Log.e("alex", "Error processing JSON results", e);
        }

        //********

        conn = null;
        jsonResults = new StringBuilder();

        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE);

            sb.append("location=" + String.valueOf(lat) + "," + String.valueOf(lng));
            sb.append("&type=subway_station");
            sb.append("&radius=" + String.valueOf(radius));
            sb.append("&key=" + apiKey);

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e("alex", "Error processing Places API URL", e);
        } catch (IOException e) {
            Log.e("alex", "Error connecting to Places API", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("results");

            Log.i("alex", "number subways: " + predsJsonArray.length());
            subwaysCount = predsJsonArray.length();

        } catch (JSONException e) {
            Log.e("alex", "Error processing JSON results", e);
        }
        onPOICountDone.OnPoiCountDone(schoolsCount + "," + storesCount + "," + parksCount + "," + restosCount + "," + subwaysCount);
    }
}
