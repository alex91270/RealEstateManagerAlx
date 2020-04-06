package com.example.realestatemanageralx;


import android.content.Context;
import android.content.Intent;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.example.realestatemanageralx.currency.GetCurrencyRateAsync;
import com.example.realestatemanageralx.data.DataHolder;
import com.example.realestatemanageralx.database.AppDatabase;
import com.example.realestatemanageralx.database.OfferMediaDAO;
import com.example.realestatemanageralx.database.PropertyDAO;
import com.example.realestatemanageralx.fragments.FirstFragment;
import com.example.realestatemanageralx.fragments.LoanFragment;
import com.example.realestatemanageralx.fragments.MapViewFragment;
import com.example.realestatemanageralx.genuine_medias.InitialCopyActivity;
import com.example.realestatemanageralx.interest.GetInterestRatesAsync;
import com.example.realestatemanageralx.model.OfferMedia;
import com.example.realestatemanageralx.model.Property;
import com.example.realestatemanageralx.viewmodels.PropertyViewModel;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private AppDatabase myDatabase;
    private PropertyViewModel propertyViewModel;
    private List<Property> propertiesList = new ArrayList<>();

    private OfferMediaDAO offerMediaDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        File folderMedias = new File(this.getFilesDir(), "medias");
        if (! folderMedias.exists()) {
            startActivity(new Intent(this, InitialCopyActivity.class));
        }

        Log.i("alex", "calling the db from master");

        myDatabase = AppDatabase.getDatabase(getApplicationContext());

        //new GetInterestRatesAsync().execute(getString(R.string.loans_API_key));
        //new GetCurrencyRateAsync().execute(getString(R.string.currency_API_key));

        configureToolBar();
        configureDrawerLayout();
        configureNavigationView();
        showStartFragment();

        //List<Property> propList = myDatabase.PropertyDAO().getAllProjects();
        //Log.i("alex", "property list size : " + propList.size());

        List<OfferMedia> mediaList = offerMediaDAO.getMediasByPropertyId(2);
        Log.i("alex", "list of medias for property 1: " + mediaList.size());

        initObservers();
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

    private void initObservers() {

        //the ViewModel allows to separate the access to the data of the view(fragments and activities). It also survives
        //the configurations changes. Can be useful if same data is accessed from multiple views.
        propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel.class);
        propertyViewModel.getProjectsList().observe(this, new Observer<List<Property>>() {
            public void onChanged(@Nullable List<Property> properties) {
                propertiesList = properties;
                DataHolder.getInstance().setPropertiesList((ArrayList) properties);

                /**
                Log.i("alex", "property list size : " + propertiesList.size());
                for (Property prop : propertiesList) {
                    Log.i("alex", "property id : " + prop.getId() + "  surface: " + prop.getSurface());
                }
                 */
            }
        });
    }
}




