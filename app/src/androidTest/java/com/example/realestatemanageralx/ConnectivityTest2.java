package com.example.realestatemanageralx;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.example.realestatemanageralx.helpers.MyReceiver;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import java.lang.reflect.Method;
import java.util.Objects;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

public class ConnectivityTest2 {

    private ConnectivityManager connectivityManager;
    //private ShadowConnectivityManager shadowConnectivityManager;
    private Context context;

    @Rule
    public ActivityTestRule<MasterActivity> mActivityTestRule = new ActivityTestRule<>(MasterActivity.class);


    @Before
    public void setup() {
        context = mActivityTestRule.getActivity();
        //context = RuntimeEnvironment.application.getApplicationContext();

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
       // shadowConnectivityManager = Shadows.shadowOf(connectivityManager);
        //NetworkInfo networkInfoShadow = ShadowNetworkInfo.newInstance(NetworkInfo.DetailedState.CONNECTED, ConnectivityManager.TYPE_WIFI, 0, true, true);
        //shadowConnectivityManager.setNetworkInfo(ConnectivityManager.TYPE_WIFI, networkInfoShadow);
    }

    @Test
    public void wifiOnTest() {

        //NetworkInfo networkInfoShadow = ShadowNetworkInfo.newInstance(NetworkInfo.DetailedState.CONNECTED, ConnectivityManager.TYPE_WIFI, 0, true, true);
        //shadowConnectivityManager.setNetworkInfo(ConnectivityManager.TYPE_WIFI, networkInfoShadow);

        //Trigger BroadcastReceiver
        //RuntimeEnvironment.application.sendBroadcast(new Intent("android.net.wifi.STATE_CHANGE"));


        ViewInteraction toastWifiOn = onView(withText("Wifi enabled")).
                inRoot(withDecorView(not(mActivityTestRule.getActivity()
                        .getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void wifiOffTest() {

        //NetworkInfo networkInfoShadow = ShadowNetworkInfo.newInstance(NetworkInfo.DetailedState.DISCONNECTED, ConnectivityManager.TYPE_WIFI, 0, true, true);
        //shadowConnectivityManager.setNetworkInfo(ConnectivityManager.TYPE_WIFI, networkInfoShadow);

        //Trigger BroadcastReceiver
        //RuntimeEnvironment.application.sendBroadcast(new Intent("android.net.wifi.STATE_CHANGE"));


        ViewInteraction toastWifiOff = onView(withText("Mobile data enabled")).
                inRoot(withDecorView(not(mActivityTestRule.getActivity()
                        .getWindow().getDecorView()))).check(matches(isDisplayed()));

    }
}
