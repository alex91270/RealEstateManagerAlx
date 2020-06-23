package com.example.realestatemanageralx.helpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Broadcast receiver triggered by a connectivity change
 */

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Utils.isInternetAvailable(context);
    }
}