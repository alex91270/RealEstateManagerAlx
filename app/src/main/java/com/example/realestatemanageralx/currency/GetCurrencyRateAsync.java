package com.example.realestatemanageralx.currency;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import com.example.realestatemanageralx.service.DI;
import com.example.realestatemanageralx.service.RealApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetCurrencyRateAsync extends AsyncTask<String, Void, String> {

    private static final String CURRENCY_API_BASE = "http://data.fixer.io/api/latest?access_key=";
    private static final String LOG_TAG = "RealEstateManager";
    private RealApiService service =DI.getRestApiService();
    private String result;


    @Override
    protected String doInBackground(String... strings) {

        String currency_API_key = strings[0];

        Log.i("alex", "thread currency rate async: " + String.valueOf(Thread.currentThread().getId()));

        Log.i("alex", "key: " + currency_API_key);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();

        try {
            StringBuilder sb = new StringBuilder(CURRENCY_API_BASE);
            sb.append(currency_API_key);
            sb.append("&base=EUR&symbols=USD");


            URL url = new URL(sb.toString());
            //URL url = new URL ("http://httpbin.org/ip");
            //url = new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=40.712775,-74.0059717&type=restaurant&rankby=distance&key=AIzaSyAhwPQxQ6UU4V7VQ7IxYsvFa3WzoNJ2qDg");
            Log.i("alex", "url: " + url);
            conn = (HttpURLConnection) url.openConnection();
            //opens up an inputStreamReader to intercept the response from that url
            InputStreamReader in = new InputStreamReader(conn.getInputStream(), "UTF-8");

            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                //builds the JSON data out of that stream
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing the API URL", e);
            Log.i("alex", "Error processing the API URL ");

        } catch (IOException e) {
            Log.i("alex", "Error connecting to the API");
            Log.e(LOG_TAG, "Error connecting to the API", e);

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        Log.i("alex", "json got: " + jsonResults.toString());

        result = jsonResults.toString();

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            //converts JSON raw data into JSON objects
            JSONObject jsonObj = new JSONObject(result);
            String rates = jsonObj.getString("rates");
            Log.i("alex", "rates: " +rates);
            JSONObject jsonObj2 = new JSONObject(rates);

            String usd = jsonObj2.getString("USD");
            Log.i("alex", "usd: " + usd);

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error processing JSON results", e);
            //result = Resources.getSystem().getString(R.string.error_processing_result);
        }

        s="success";

        if (s == null) {
            Log.i("alex", "error ");
        } else {
            Log.i("alex", "result: " + s);

        }
    }
}
