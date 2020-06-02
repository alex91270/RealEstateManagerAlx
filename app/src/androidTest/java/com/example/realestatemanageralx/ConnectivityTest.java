package com.example.realestatemanageralx;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.test.platform.app.InstrumentationRegistry;
import com.example.realestatemanageralx.datas.DataHolder;
import com.example.realestatemanageralx.helpers.MyReceiver;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ConnectivityTest {

    @Test
    public void testConnectivity() {

        ConnectivityManager cm = (ConnectivityManager) InstrumentationRegistry.getInstrumentation()
                .getTargetContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        DataHolder.getInstance().setLastNetworkState("null");


        MyReceiver receiver = new MyReceiver();
        Intent i = new Intent("android.net.conn.CONNECTIVITY_CHANGE");

        receiver.onReceive(InstrumentationRegistry.getInstrumentation().getTargetContext(), i);


        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                assertEquals(DataHolder.getInstance().getLastNetworkState(), "wifi");
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                assertEquals(DataHolder.getInstance().getLastNetworkState(), "mobile");
            }
        } else {
            assertEquals(DataHolder.getInstance().getLastNetworkState(), "none");
        }
    }
}
