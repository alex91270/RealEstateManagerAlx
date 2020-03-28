package com.example.realestatemanageralx.interest_rates;

import android.os.StrictMode;
import android.util.Log;
import com.example.realestatemanageralx.service.DI;
import com.example.realestatemanageralx.service.RealApiService;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

public class GetInterestRates {

    private static final String CURRENCY_API_BASE = "https://www.quandl.com/api/v3/datasets/FED/SVENPY.json?api_key=";
    private static final String LOG_TAG = "RealEstateManager";
    private RealApiService service = DI.getRestApiService();

    public void updateInterestRates(String loan_API_key) {

        //Log.i("alex", "key: " + loan_API_key);
        Calendar cal = Calendar.getInstance();
        String today = String.valueOf(cal.get(Calendar.YEAR)) + "-"
                + String.valueOf(cal.get(Calendar.MONTH) + 1) + "-"
                + String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        String last_month = String.valueOf(cal.get(Calendar.YEAR)) + "-"
                + String.valueOf(cal.get(Calendar.MONTH)) + "-"
                + String.valueOf(cal.get(Calendar.DAY_OF_MONTH));



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();

        try {
            StringBuilder sb = new StringBuilder(CURRENCY_API_BASE);
            sb.append(loan_API_key);
            sb.append("&start_date=" + last_month + "&end_date=" + today);

            //Log.i("alex", "date: " + sb.toString());

            URL url = new URL(sb.toString());
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

        String raw_result = jsonResults.toString();

        Log.i("alex", raw_result);

        getRatesFromJson(raw_result);
    }


    private void getRatesFromJson(String result) {
        String result_last_day = result.substring((result.indexOf("data\":[[") + 9), (result.indexOf("],[") + 3));
        String last_date_available = result_last_day.substring((result_last_day.indexOf(":[[") + 3), (result_last_day.indexOf("\",")));

        String rates_only = result_last_day.substring(result_last_day.indexOf("\",") + 2, result_last_day.indexOf("],["));

        Log.i("alex", "last date: " + last_date_available);
        Log.i("alex", "cut_result: " + result_last_day);
        Log.i("alex", "rates only: " + rates_only);

        String[] ratesArray = rates_only.split(",", -1);

        Log.i("alex", "rates array size : " + ratesArray.length);
        Log.i("alex", "rate one year: " + ratesArray[0]);
    }

}

