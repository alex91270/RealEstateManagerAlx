package com.example.realestatemanageralx.apis;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import com.example.realestatemanageralx.helpers.TypesConversions;
import com.example.realestatemanageralx.viewmodels.RateViewModel;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

public class GetInterestRatesAsync extends AsyncTask<String, Void, String> {

    private static final String CURRENCY_API_BASE = "https://www.quandl.com/api/v3/datasets/FED/SVENPY.json?api_key=";
    private static final String LOG_TAG = "RealEstateManager";
    private RateViewModel rateViewModel;
    private String raw_result;
    public GetInterestRatesAsync(RateViewModel rvm) {
        rateViewModel = rvm;
    }

    @Override
    protected String doInBackground(String... strings) {

        String loan_API_key = strings[0];

        Log.i("alex", "thread interest rates async: " + String.valueOf(Thread.currentThread().getId()));

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

        raw_result = jsonResults.toString();
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        String result_last_day = raw_result.substring((raw_result.indexOf("data\":[[") + 9), (raw_result.indexOf("],[") + 3));
        String last_date_available = "20" + result_last_day.substring((result_last_day.indexOf(":[[") + 3), (result_last_day.indexOf("\",")));
        rateViewModel.updateRateValue(3, TypesConversions.getTimeStampFromString(last_date_available));
        String rates_only = result_last_day.substring(result_last_day.indexOf("\",") + 2, result_last_day.indexOf("],["));

        Log.i("alex", "last date: " + last_date_available);
        Log.i("alex", "last date timestamp: " + TypesConversions.getTimeStampFromString(last_date_available));
        Log.i("alex", "cut_result: " + result_last_day);
        Log.i("alex", "rates only: " + rates_only);

        String[] ratesArray = rates_only.split(",", -1);

        Log.i("alex", "rates array size : " + ratesArray.length);
        Log.i("alex", "rate one year: " + ratesArray[0]);
        for (int i = 0; i<ratesArray.length; i++) {
            rateViewModel.updateRateValue(i+4, Double.valueOf(ratesArray[i]));
            //Log.i("alex", "i=" + i + " rate id: " + String.valueOf(i+4) + " rateYear" + String.valueOf(i+1) + " value: " + ratesArray[i]);
        }

        s="success";

        if (s == null) {
            Log.i("alex", "error ");
        } else {
            Log.i("alex", "result: " + s);
        }

    }
}

