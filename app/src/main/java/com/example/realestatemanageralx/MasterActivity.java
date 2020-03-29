package com.example.realestatemanageralx;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;


import com.example.realestatemanageralx.currency.GetCurrencyRateAsync;
import com.example.realestatemanageralx.fragments.FirstFragment;
import com.example.realestatemanageralx.fragments.LoanFragment;
import com.example.realestatemanageralx.fragments.MapViewFragment;
import com.example.realestatemanageralx.interest.GetInterestRatesAsync;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MasterActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    //Declare fragment handled by Navigation Drawer
    private Fragment firstFragment;
    private Fragment mapFragment;
    private Fragment loanFragment;

    //Identify each fragment with a number
    private static final int FRAGMENT_FIRST = 0;
    private static final int FRAGMENT_MAP = 1;
    private static final int FRAGMENT_LOAN = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        String PATH = "/remote/dir/server/";
        String directoryName = "RealEM";
        File directory = new File(directoryName);
        if (! directory.exists()) {
            Log.i("alex", "le repertoire n'existe pas");
            directory.mkdir();
        } else {
            Log.i("alex", "le repertoire existe");
        }

        //new GetInterestRatesAsync().execute(getString(R.string.loans_API_key));
        //new GetCurrencyRateAsync().execute(getString(R.string.currency_API_key));

        configureToolBar();
        configureDrawerLayout();
        configureNavigationView();
        showStartFragment();

        Log.i("alex", "master thread: " + String.valueOf(Thread.currentThread().getId()));

       }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id){
            case R.id.nav_drawer_see_offers :
                showFirstFragment();
                break;
            case R.id.nav_drawer_see_on_map :
                showMapFragment();
                break;
            case R.id.nav_drawer_loan :
                showLoanFragment();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void configureToolBar(){
        toolbar = (Toolbar) findViewById(R.id.activity_master_toolbar);
        toolbar.setTitle(R.string.toolbar_title);
        setSupportActionBar(toolbar);
    }

    private void configureDrawerLayout(){
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_master_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavigationView(){
        navigationView = (NavigationView) findViewById(R.id.activity_master_nav_view);
        View headerView = navigationView.getHeaderView(0);

        navigationView.setNavigationItemSelectedListener(this);
    }

    //Show first fragment when activity is created
    private void showStartFragment(){
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.activity_master_frame_layout);
        if (visibleFragment == null){
            showFragment(FRAGMENT_FIRST);
            navigationView.getMenu().getItem(0).setChecked(true);
        }
    }

    //Show fragment according an Identifier
    private void showFragment(int fragmentIdentifier){
        switch (fragmentIdentifier){
            case FRAGMENT_FIRST :
                showFirstFragment();
                break;
            default:
                break;
        }
    }

    private void showFirstFragment(){
        if (firstFragment == null) firstFragment = FirstFragment.newInstance();
        startTransactionFragment(firstFragment);
    }

    private void showMapFragment(){
        if (mapFragment == null) mapFragment = MapViewFragment.newInstance();
        startTransactionFragment(mapFragment);
    }

    private void showLoanFragment(){
        if (loanFragment == null) loanFragment = LoanFragment.newInstance();
        startTransactionFragment(loanFragment);
    }

    private void startTransactionFragment(Fragment fragment){

        if (!fragment.isVisible()){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_master_frame_layout, fragment).commit();
        }
    }




    private class FetchTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            InputStream inputStream = null;
            HttpURLConnection conn = null;

            String stringUrl = strings[0];
            try {
                URL url = new URL(stringUrl);
                conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                int response = conn.getResponseCode();
                if (response != 200) {
                    return null;
                }

                inputStream = conn.getInputStream();
                if (inputStream == null) {
                    return null;
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuffer buffer = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                    buffer.append("\n");
                }

                return new String(buffer);
            } catch (IOException e) {
                return null;
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null) {
                Log.i("alex", "erreur ");
            } else {
                Log.i("alex", "resultat: " + s);

            }

        }
    }
}




