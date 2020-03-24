package com.example.realestatemanageralx.currency;

import android.content.res.Resources;
import android.os.StrictMode;
import android.util.Log;

import com.example.realestatemanageralx.R;
import com.example.realestatemanageralx.service.DI;
import com.example.realestatemanageralx.service.RealApiService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetCurrencyRate {

    private static final String CURRENCY_API_BASE = "http://data.fixer.io/api/latest?access_key=";
    private static final String LOG_TAG = "RealEstateManager";
    private static String result;
    private RealApiService service =DI.getRestApiService();

    public void updateRate2() {

    }

    public void updateRate(String currency_API_key) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //builds the request URL for GooglePlaces API with as parameters, the location of the center
        //of research, the type od establishment, the ranking, and API key
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();

        try {
            StringBuilder sb = new StringBuilder(CURRENCY_API_BASE);
            sb.append(currency_API_key);
            sb.append("&base=EUR&symbols=USD");


            URL url = new URL(sb.toString());
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
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            result = Resources.getSystem().getString(R.string.error_api_url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            result = Resources.getSystem().getString(R.string.error_connecting_api);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }


        getRateFromJson(jsonResults.toString());


    }

    private int getRateFromJson(String result) {
        try {
            //converts JSON raw data into JSON objects
            JSONObject jsonObj = new JSONObject(result);
            JSONArray predsJsonArray = jsonObj.getJSONArray("results");

            for (int i = 0; i < predsJsonArray.length(); i++) {
                JSONObject jsonObject = predsJsonArray.getJSONObject(i);



                try {
                    String reference = jsonObject.getString("reference");
                    //builds a Restaurant with it's ID only, which is the GooglePlaces identifier
                    //return new Restaurant(reference);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(LOG_TAG, "Error processing JSON results", e);
                    result = Resources.getSystem().getString(R.string.error_processing_result);
                }



            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error processing JSON results", e);
            result = Resources.getSystem().getString(R.string.error_processing_result);
        }

        return 0;
    }

}
