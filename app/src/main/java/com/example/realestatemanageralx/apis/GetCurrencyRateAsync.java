package com.example.realestatemanageralx.apis;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import com.example.realestatemanageralx.viewmodels.RateViewModel;
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
    private String result;
    private RateViewModel rateViewModel;

    /**
     * Gets the last up to date Dollar-Euro exchange rate from the data.fixer.io API
     * @param rvm
     */

    public GetCurrencyRateAsync(RateViewModel rvm) {
        rateViewModel = rvm;
    }


    @Override
    protected String doInBackground(String... strings) {

        String currency_API_key = strings[0];
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();

        try {
            StringBuilder sb = new StringBuilder(CURRENCY_API_BASE);
            sb.append(currency_API_key);
            sb.append("&base=EUR&symbols=USD");

            URL url = new URL(sb.toString());
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

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to the API", e);

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

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
            String ts = jsonObj.getString("timestamp");
            JSONObject jsonObj2 = new JSONObject(rates);

            String usd = jsonObj2.getString("USD");

            rateViewModel.updateRateValue(1, Double.valueOf(usd));
            rateViewModel.updateRateValue(2, Double.valueOf(ts));

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error processing JSON results", e);
        }
    }
}
