package com.example.realestatemanageralx.ui;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.realestatemanageralx.R;
import com.example.realestatemanageralx.apis.GetCurrencyRateAsync;
import com.example.realestatemanageralx.apis.GetInterestRatesAsync;
import com.example.realestatemanageralx.datas.DataHolder;
import com.example.realestatemanageralx.ui.fragments.FirstFragment;
import com.example.realestatemanageralx.ui.fragments.LoanFragment;
import com.example.realestatemanageralx.ui.fragments.MapViewFragment;
import com.example.realestatemanageralx.ui.fragments.ResearchFragment;
import com.example.realestatemanageralx.ui.fragments.create_offer.CreateFragment;
import com.example.realestatemanageralx.ui.genuine_data.InitialCopyActivity;
import com.example.realestatemanageralx.ui.fragments.offers_list.OffersListFragment;
import com.example.realestatemanageralx.helpers.MyReceiver;
import com.example.realestatemanageralx.model.Agent;
import com.example.realestatemanageralx.viewmodels.AgentViewModel;
import com.example.realestatemanageralx.viewmodels.RateViewModel;
import com.google.android.material.navigation.NavigationView;
import java.io.File;

public class MasterActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private Menu nav_Menu;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    //Declare fragment handled by Navigation Drawer
    private Fragment firstFragment;
    private Fragment offersListFragment;
    private Fragment researchFragment;
    private Fragment mapFragment;
    private Fragment loanFragment;
    private Fragment createFragment;
    private AgentViewModel agentViewModel;
    private AlertDialog dialog = null;
    private Context context;
    private EditText dialogTextUsername = null;
    private EditText dialogTextPassword = null;
    private BroadcastReceiver MyReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        context = this;
        MyReceiver = new MyReceiver();

        if (getResources().getString(R.string.twopanes).equals("true")) {
            Log.i("alex", "displaying on 2 panes");
        } else {
            Log.i("alex", "displaying on 1 pane");
        }


/**
                //Forces screen orientation accordingly to the screen density of the device
        if (getResources().getString(R.string.orientation).equals("portrait")) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            DataHolder.getInstance().setOrientation("portrait");
            Log.i("alex", "portrait");
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            DataHolder.getInstance().setOrientation("landscape");
            Log.i("alex", "landscape");
        }*/


        File folderMedias = new File(this.getFilesDir(), "medias");
        //if the folder medias does not exist, creates it and fills it
        if (!folderMedias.exists()) {
            startActivity(new Intent(this, InitialCopyActivity.class));
        }

        RateViewModel rvm = ViewModelProviders.of(this).get(RateViewModel.class);
//TODO

        //gets back async, exchange rate and interest rates
        //new GetInterestRatesAsync(rvm).execute(getString(R.string.loans_API_key));
        //new GetCurrencyRateAsync(rvm).execute(getString(R.string.currency_API_key));

        configureToolBar();
        configureDrawerLayout();
        configureNavigationView();

        //registers the broadcast receiver for connectivity check
        registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        showFirstFragment();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.nav_drawer_see_offers:
                showOffersListFragment();
                break;
            case R.id.nav_drawer_research:
                showResearchFragment();
                break;
            case R.id.nav_drawer_see_on_map:
                showMapFragment();
                break;
            case R.id.nav_drawer_loan:
                showLoanFragment();
                break;
            case R.id.nav_drawer_pro:
                final AlertDialog dialog = getMessageDialog();
                dialog.show();
                break;
            case R.id.nav_drawer_create:
                showCreateFragment();
                break;
            case R.id.nav_drawer_logout:
                nav_Menu.setGroupEnabled(R.id.pro_group, false);
                nav_Menu.setGroupVisible(R.id.pro_group, false);
                DataHolder.getInstance().setIsLogged(false);
                Toast.makeText(context, "You are logged out", Toast.LENGTH_LONG).show();
                showFirstFragment();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void configureToolBar() {
        toolbar = (Toolbar) findViewById(R.id.activity_master_toolbar);
        toolbar.setTitle(R.string.toolbar_title);
        setSupportActionBar(toolbar);
    }

    private void configureDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_master_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.activity_master_nav_view);
        nav_Menu = navigationView.getMenu();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void showFirstFragment() {
        if (firstFragment == null) firstFragment = FirstFragment.newInstance();
        startTransactionFragment(firstFragment, "firstFragment");
    }

    private void showOffersListFragment() {
        if (offersListFragment == null) offersListFragment = OffersListFragment.newInstance();
        startTransactionFragment(offersListFragment, "offersListFragment");
    }

    private void showResearchFragment() {
        if (researchFragment == null) researchFragment = ResearchFragment.newInstance();
        startTransactionFragment(researchFragment, "researchFragment");
    }

    private void showMapFragment() {
        if (mapFragment == null) mapFragment = MapViewFragment.newInstance();
        startTransactionFragment(mapFragment, "mapFragment");
    }

    private void showLoanFragment() {
        if (loanFragment == null) loanFragment = LoanFragment.newInstance();
        startTransactionFragment(loanFragment, "loanFragment");
    }

    private void showCreateFragment() {
        if (createFragment == null) createFragment = CreateFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putString("action", "creation");
        createFragment.setArguments(bundle);
        startTransactionFragment(createFragment, "createFragment");
    }


    private void startTransactionFragment(Fragment fragment, String tag) {
        if (!fragment.isVisible()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_master_frame_layout, fragment, tag).commit();
        }
    }

    private void initObserverAgent(String name, String pass) {
        agentViewModel = ViewModelProviders.of(this).get(AgentViewModel.class);
        agentViewModel.getAgentByUsername(name).observe(this, new Observer<Agent>() {
            @Override
            public void onChanged(Agent agent) {
                if (agent != null) {
                    if (agent.getPassword().equals(pass)) {
                        Toast.makeText(context, "You are now loggued in", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        nav_Menu.setGroupEnabled(R.id.pro_group, true);
                        nav_Menu.setGroupVisible(R.id.pro_group, true);
                        DataHolder.getInstance().setIsLogged(true);
                        DataHolder.getInstance().setAgentId(agent.getId());
                        showFirstFragment();
                    } else {
                        Toast.makeText(context, "Wrong username/password", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, "Unknown username", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private AlertDialog getMessageDialog() {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.alert_dialog_login, null);
        alertBuilder.setView(v);
        dialog = alertBuilder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = dialog.findViewById(R.id.dialog_button_login);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        dialogTextUsername = dialog.findViewById(R.id.loginEditTextName);
                        dialogTextPassword = dialog.findViewById(R.id.loginEditTextPassword);

                        initObserverAgent(dialogTextUsername.getText().toString(), dialogTextPassword.getText().toString());
                    }
                });
            }
        });

        return dialog;
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(MyReceiver);
    }

    @Override
    public void onBackPressed() {
        Fragment currentFrag = getSupportFragmentManager().findFragmentByTag("firstFragment");
        if (currentFrag == null) {
            showFirstFragment();
        } else {
            super.onBackPressed();
        }
    }
}




