package com.example.realestatemanageralx;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.example.realestatemanageralx.database.AppDatabase;
import com.example.realestatemanageralx.fragments.FirstFragment;
import com.example.realestatemanageralx.fragments.LoanFragment;
import com.example.realestatemanageralx.fragments.create_offer.LocationPickerFragment;
import com.example.realestatemanageralx.fragments.MapViewFragment;
import com.example.realestatemanageralx.genuine_medias.InitialCopyActivity;
import com.example.realestatemanageralx.login.LoginHolder;
import com.example.realestatemanageralx.model.Agent;
import com.example.realestatemanageralx.model.Property;
import com.example.realestatemanageralx.viewmodels.AgentViewModel;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;

public class MasterActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private Menu nav_Menu;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    //Declare fragment handled by Navigation Drawer
    private Fragment firstFragment;
    private Fragment mapFragment;
    private Fragment loanFragment;
    private Fragment createFragment;

    //Identify each fragment with a number
    private static final int FRAGMENT_FIRST = 0;
    private static final int FRAGMENT_MAP = 1;
    private static final int FRAGMENT_LOAN = 2;

    public Property temporaryProp;

    private AppDatabase myDatabase;
    //private PropertyViewModel propertyViewModel;
    //private List<Property> propertiesList = new ArrayList<>();
    private AgentViewModel agentViewModel;
    private AlertDialog dialog = null;
    private Context context;
    private EditText dialogTextUsername = null;
    private EditText dialogTextPassword = null;

    //private OfferMediaDAO offerMediaDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        context = this;

        File folderMedias = new File(this.getFilesDir(), "medias");
        if (! folderMedias.exists()) {
            startActivity(new Intent(this, InitialCopyActivity.class));
        }

        myDatabase = AppDatabase.getDatabase(getApplicationContext());

        //new GetInterestRatesAsync().execute(getString(R.string.loans_API_key));
        //new GetCurrencyRateAsync().execute(getString(R.string.currency_API_key));

        configureToolBar();
        configureDrawerLayout();
        configureNavigationView();
        showStartFragment();



        //initObservers();
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
            case R.id.nav_drawer_pro :
                final AlertDialog dialog = getMessageDialog();
                dialog.show();
                break;
            case R.id.nav_drawer_create :
                showCreateFragment();
                break;
            case R.id.nav_drawer_logout :
                nav_Menu.setGroupEnabled(R.id.pro_group, false);
                nav_Menu.setGroupVisible(R.id.pro_group, false);
                LoginHolder.getInstance().setIsLogged(false);
                Toast.makeText(context, "You are logged out", Toast.LENGTH_LONG).show();
                showFirstFragment();
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
        nav_Menu = navigationView.getMenu();
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

    private void showCreateFragment() {
        if (createFragment == null) createFragment = LocationPickerFragment.newInstance();
        startTransactionFragment(createFragment);
    }


    private void startTransactionFragment(Fragment fragment){

        if (!fragment.isVisible()){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_master_frame_layout, fragment).commit();
        }
    }

    private void initObserverAgent(String name, String pass) {
        agentViewModel = ViewModelProviders.of(this).get(AgentViewModel.class);
        agentViewModel.getAgentByUsername(name).observe(this, new Observer<Agent>() {
            @Override
            public void onChanged(Agent agent) {
                if (agent!=null) {
                    if (agent.getPassword().equals(pass)) {
                        Toast.makeText(context, "You are now loggued in", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        nav_Menu.setGroupEnabled(R.id.pro_group, true);
                        nav_Menu.setGroupVisible(R.id.pro_group, true);
                        LoginHolder.getInstance().setIsLogged(true);
                        LoginHolder.getInstance().setAgentId(agent.getId());
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
        LayoutInflater inflater = (LayoutInflater)this.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
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
}




