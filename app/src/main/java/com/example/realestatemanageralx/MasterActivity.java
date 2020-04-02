package com.example.realestatemanageralx;


import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

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

        File folderToCreate = new File(this.getFilesDir(), "medias");
        if (! folderToCreate.exists()) {
            Log.i("alex","First launch, copying media files...");
            folderToCreate.mkdir();
        }

        /**AssetManager assetManager = getAssets();
        String[] files = null;

        try {
            files = assetManager.list("medias");
            Log.i("alex", "asset list size: " + files.length);

            for(int i=0;i<files.length;i++){
                Log.i("alex", "list element " + i + " :" + files[i]);
            }

        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }*/

        copyMediaFiles();


        //new GetInterestRatesAsync().execute(getString(R.string.loans_API_key));
        //new GetCurrencyRateAsync().execute(getString(R.string.currency_API_key));

        configureToolBar();
        configureDrawerLayout();
        configureNavigationView();
        showStartFragment();

        /**ImageView imageView = findViewById(R.id.imageViewTest);
        File imgFile = new  File(this.getFilesDir() +"/medias/photo11.jpg");
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        imageView.setImageBitmap(myBitmap);
         */

        Log.i("alex", "path: " + getFilesDir().getPath() + "/medias/photo11.jpg" );

        File filePhoto11 = new File(getFilesDir().getPath() + "/medias/photo11.jpg" );
        if (! filePhoto11.exists()) {
            Log.i("alex", "photo11 existe pas ");
        }else {
            Log.i("alex", "photo11 existe");
        }

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

    private void copyMediaFiles() {
        long timeStart = new Date().getTime();
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("medias");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        if (files != null) for (String filename : files) {
            Log.i("alex", "filename in files: " + filename );
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open("medias/" + filename);

                //File outFile = new File(this.getFilesDir(), "medias/" + filename);
                //File outFile = new File(getExternalFilesDir(null), filename);
                File outFile = new File(getFilesDir().getPath() + "/medias/" + filename );


                out = new FileOutputStream(outFile);
                copyFile(in, out);
            } catch(IOException e) {
                Log.e("alex", "Failed to copy asset file: " + filename, e);
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        // NOOP
                        Log.e("alex", "error closing  file IN");
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        Log.e("alex", "error closing  file IN");
                        // NOOP
                    }
                }
            }
        }
        long timeEnd = new Date().getTime();
        Log.i("alex", "time elapsed: " + (timeEnd - timeStart) + " milliseconds");

    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {

        Log.i("alex", "we are in copyfile ");

        long timeStart = new Date().getTime();
        byte[] buffer = new byte[1024];
        int read;

        int bits = 0;

        while((read = in.read(buffer)) != -1){
            bits +=1;
            out.write(buffer, 0, read);
        }

        Log.i("alex", "1 file copieds, number bits: " + bits);
        long timeEnd = new Date().getTime();
    }
}




