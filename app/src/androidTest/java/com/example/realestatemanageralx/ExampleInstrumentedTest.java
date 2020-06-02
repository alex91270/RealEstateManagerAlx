package com.example.realestatemanageralx;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.realestatemanageralx.datas.DataHolder;
import com.example.realestatemanageralx.helpers.MyReceiver;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.realestatemanageralx", appContext.getPackageName());
    }

    /**@Test
    public void testCon() {

        Log.i("alex","Current test thread: " + Thread.currentThread().getId());

        DataHolder.getInstance().setLastNetworkState("null");
        MyReceiver receiver = new MyReceiver();
        Intent i = new Intent("android.net.conn.CONNECTIVITY_CHANGE");

        receiver.onReceive(InstrumentationRegistry.getInstrumentation().getTargetContext(), i);
        assertTrue(!DataHolder.getInstance().getLastNetworkState().equals("null"));
    }*/

    @Test
    public void testCon2() {

        Log.i("alex","Current test thread: " + Thread.currentThread().getId());

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
