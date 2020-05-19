package com.example.realestatemanageralx.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.realestatemanageralx.datas.DataHolder;

class NetworkUtil {
    public static String getConnectivityStatusString(Context context) {
        String status = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                status = "Wifi enabled";
                DataHolder.getInstance().setLastNetworkState("wifi");
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                status = "Mobile data enabled";
                DataHolder.getInstance().setLastNetworkState("mobile");
            }
        } else {
            status = "No internet is available";
            DataHolder.getInstance().setLastNetworkState("none");
        }
        return status;
    }
}