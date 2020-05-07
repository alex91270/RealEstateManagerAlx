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
    private LatLng location;
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    private static final int radius = 200;
    private final String [] typesList = {"school","store","park","restaurant","subway_station"};
    private int[] integerResults = new int[typesList.length];

    public POICount(OnPOICountDone onPOICountDone)  {this.onPOICountDone = onPOICountDone;}

    public void requestNearbyPois(String apiKey, LatLng loc) {

        Log.i("alex", "request nearby thread: " + Thread.currentThread());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        for (int i = 0; i < typesList.length; i++) {
            requestPOI(apiKey, loc, i);
        }

        String result = "";

        for (int i = 0; i < integerResults.length; i++) {
            result += integerResults[i];
            if (i != integerResults.length - 1) result += ",";
        }
        onPOICountDone.OnPoiCountDone(result);
    }

    private void requestPOI(String apiKey, LatLng loc, int i) {

        Log.i("alex", "starting retrieval of " + typesList[i]);

        location = loc;
        Double lat = location.latitude;
        Double lng = location.longitude;
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();

        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE);

            sb.append("location=" + String.valueOf(lat) + "," + String.valueOf(lng));
            sb.append("&type=" + typesList[i]);
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

            Log.i("alex", "number of " + typesList[i] + "s : " + predsJsonArray.length());
            integerResults[i] = predsJsonArray.length();

        } catch (JSONException e) {
            Log.e("alex", "Error processing JSON results", e);
        }
    }
}
