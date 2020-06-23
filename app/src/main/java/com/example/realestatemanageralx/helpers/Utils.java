package com.example.realestatemanageralx.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.example.realestatemanageralx.datas.DataHolder;

import java.io.IOException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Philippe on 21/02/2018.
 */

public class Utils {

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @param dollars
     * @return
     */
    public static int convertDollarToEuro(int dollars, double rate) {
        return (int) Math.round(dollars * rate);
    }

    /**
     * Conversion d'un prix d'un bien immobilier (Euros vers Dollars)
     *
     * @param euros
     * @param rate
     * @return
     */

    public static int convertEuroToDollar(int euros, double rate) {
        return (int) Math.round(euros / rate);
    }

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @return
     */
    public static String getTodayDate() {
        // DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
        return dateFormat.format(new Date());
    }


    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @param context
     * @return
     */
    public static boolean isInternetAvailable(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();


        if (activeNetwork != null) {
            DataHolder.getInstance().setLastConnectionType(activeNetwork.getTypeName());
        } else {
            DataHolder.getInstance().setLastConnectionType("none");
        }

        if (activeNetwork == null || !activeNetwork.isConnected()) {
            DataHolder.getInstance().setLastConnectionState(false);
            return false;
        }

        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            DataHolder.getInstance().setLastConnectionState(!ipAddr.equals(""));
            return !ipAddr.equals("");

        } catch (Exception e) {
            DataHolder.getInstance().setLastConnectionState(false);
            Log.e("REMAlx", "an exception occured: " + e.toString());
            return false;
        }
    }
}
