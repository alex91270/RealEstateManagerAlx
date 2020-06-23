package com.example.realestatemanageralx;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.test.platform.app.InstrumentationRegistry;
import com.example.realestatemanageralx.datas.DataHolder;
import com.example.realestatemanageralx.helpers.MyReceiver;
import org.junit.Test;
import java.net.InetAddress;
import static org.junit.Assert.assertEquals;

public class ConnectivityTest {

    @Test
    public void testConnectivity() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        MyReceiver receiver = new MyReceiver();
        Intent i = new Intent("android.net.conn.CONNECTIVITY_CHANGE");

        receiver.onReceive(context, i);

        if (activeNetwork == null) {
            assertEquals(DataHolder.getInstance().getLastConnectionType(), "none");
        } else {
            assertEquals(DataHolder.getInstance().getLastConnectionType(), activeNetwork.getTypeName());
        }

        if (activeNetwork == null || !activeNetwork.isConnected()) {
            assertEquals(DataHolder.getInstance().getLastConnectionState(), false);
        } else {
            try {
                InetAddress ipAddr = InetAddress.getByName("google.com");
                if (ipAddr.equals("")) {
                    assertEquals(DataHolder.getInstance().getLastConnectionState(), false);
                } else {
                    assertEquals(DataHolder.getInstance().getLastConnectionState(), true);
                }

            } catch (Exception e) {
                assertEquals(DataHolder.getInstance().getLastConnectionState(), false);
            }
        }
    }
}
