package com.example.realestatemanageralx.helpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String status = NetworkUtil.getConnectivityStatusString(context);
        /**if(status.isEmpty()) {
            status="No Internet Connection";
        }*/
        Log.i("alex", "broadcast receiver status: " +  status);
        Toast.makeText(context, status, Toast.LENGTH_LONG).show();
    }
}