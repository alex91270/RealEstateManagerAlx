package com.example.realestatemanageralx.helpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String status = NetworkUtil.getConnectivityStatusString(context);
        Log.i("alex", "broadcast receiver status: " +  status);

        Handler mainHandler = new Handler(context.getMainLooper());
        Runnable myRunnable = () -> {
            Toast.makeText(context, status, Toast.LENGTH_LONG).show();
        };

        mainHandler.post(myRunnable);
    }
}