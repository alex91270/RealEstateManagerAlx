package com.example.realestatemanageralx.fragments.offer_detail;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
//import android.util.Log;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.realestatemanageralx.R;
import com.example.realestatemanageralx.datas.DataHolder;
import com.example.realestatemanageralx.fragments.MapViewFragment;
import com.example.realestatemanageralx.fragments.SliderAdapter;
import com.example.realestatemanageralx.fragments.create_offer.CreateFragment;
import com.example.realestatemanageralx.fragments.offers_list.OffersListFragment;
import com.example.realestatemanageralx.helpers.DataProcessing;
import com.example.realestatemanageralx.helpers.TypesConversions;
import com.example.realestatemanageralx.helpers.Utils;
import com.example.realestatemanageralx.model.Agent;
import com.example.realestatemanageralx.model.OfferMedia;
import com.example.realestatemanageralx.model.Property;
import com.example.realestatemanageralx.model.Rate;
import com.example.realestatemanageralx.viewmodels.AgentViewModel;
import com.example.realestatemanageralx.viewmodels.OfferMediaViewModel;
import com.example.realestatemanageralx.viewmodels.PropertyViewModel;
import com.example.realestatemanageralx.viewmodels.RateViewModel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class OfferDetailFragment extends Fragment {
    private PropertyViewModel propertyViewModel;
    private OfferMediaViewModel mediaViewModel;
    private List<OfferMedia> mediasList = new ArrayList<>();
    private AgentViewModel agentViewModel;
    private Agent mAgent;
    private RateViewModel rateViewModel;
    private Property mProperty;
    private Long propertyId;
    private Long agentId;
    private static String apiKey;

    private ImageView imageSold;
    private TextView textViewBuildType;
    private TextView textViewLocation;
    private ImageView liteMap;
    private TextView textViewSurface;
   private TextView textViewRooms;
    private TextView textViewPrice;
    private TextView textViewSoldOn;
    private TextView textViewDescription;
    private TextView textViewBeds;
    private TextView textViewToilets;
    private TextView textViewShowers;
    private TextView textViewBathtubs;
    private TextView textViewAircon;
    private TextView textViewDateoffer;
    private TextView textViewAgentName;
    private TextView textViewAgentPhone;
    private TextView textViewAgentMail;
    private Button buttonSold;
    private Button buttonModify;
    private Button buttonMail;
    private Button buttonPhone;
    private TextView textViewConvertUnit;
    private TextView textViewConvertCurrency;
    private ImageView imageCurrency;
    private ViewPager gallery;
    private RecyclerView poiRecycler;
    private OfferDetailPoiRecyclerAdapter myAdapter;
    private LayoutInflater galleryInflater;

    private String surfaceUnit = "m²";
    private String currency = "dollar";

    private double exchangeRate;
    private Context context;
    private AlertDialog dialog = null;
    private final int PERMISSION_REQUEST_CALL = 123;
    //private TypesConversions tc = new TypesConversions();

    public static OfferDetailFragment newInstance() {
        return (new OfferDetailFragment());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_offer_detail, container, false);
        context = root.getContext();
        apiKey = context.getString(R.string.google_maps_key);
        propertyId = getArguments().getLong("propertyId");
        agentId = getArguments().getLong("agentId");

        imageSold = root.findViewById(R.id.detail_image_sold);

        textViewLocation = root.findViewById(R.id.detail_text_location);
        liteMap = root.findViewById(R.id.detail_map_lite);
        textViewBuildType = root.findViewById(R.id.detail_text_type);
      textViewSurface = root.findViewById(R.id.detail_text_surface);
        textViewRooms = root.findViewById(R.id.detail_text_rooms);
        textViewPrice = root.findViewById(R.id.detail_text_price);
        textViewSoldOn = root.findViewById(R.id.detail_text_sold_on);
         textViewDescription = root.findViewById(R.id.detail_text_description);
        textViewBeds = root.findViewById(R.id.detail_text_beds);
         textViewToilets = root.findViewById(R.id.detail_text_toilets);
        textViewShowers = root.findViewById(R.id.detail_text_showers);
         textViewBathtubs = root.findViewById(R.id.detail_text_bathtubs);
         textViewAircon = root.findViewById(R.id.detail_text_aircon);
        textViewDateoffer = root.findViewById(R.id.detail_text_date_offer);
        textViewAgentName = root.findViewById(R.id.detail_text_agent_name);
        textViewAgentPhone = root.findViewById(R.id.detail_text_agent_phone);
        textViewAgentMail = root.findViewById(R.id.detail_text_agent_email);
        textViewConvertUnit = root.findViewById(R.id.detail_text_convert_unit);
        textViewConvertCurrency = root.findViewById(R.id.detail_text_currency);
        buttonSold = root.findViewById(R.id.detail_button_sold);
        buttonModify = root.findViewById(R.id.detail_button_modify);
        buttonMail = root.findViewById(R.id.detail_button_mail);
        buttonPhone = root.findViewById(R.id.detail_button_call);
        imageCurrency = root.findViewById(R.id.imageViewCurrency);
        gallery = root.findViewById(R.id.view_pager_slider);
        poiRecycler = root.findViewById(R.id.detail_recycler_poi);
        galleryInflater = LayoutInflater.from(root.getContext());

        poiRecycler.setLayoutManager(new LinearLayoutManager(root.getContext()));

        if(DataHolder.getInstance().getIsAgentLogged()) {
            buttonSold.setVisibility(View.VISIBLE);
            buttonSold.setEnabled(true);
            buttonModify.setVisibility(View.VISIBLE);
            buttonModify.setEnabled(true);
        }

        buttonMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = getMessageDialog();
                dialog.show();
            }
        });

        buttonPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAgent();
            }
        });

        liteMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapViewFragment mapViewFrag= new MapViewFragment();
                Bundle bundle=new Bundle();
                bundle.putLong("propertyId",propertyId);
                mapViewFrag.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_master_frame_layout, mapViewFrag, "fragment map view")
                        .addToBackStack(null)
                        .commit();
            }
        });

        textViewConvertUnit.setOnClickListener(v -> {
            if (surfaceUnit.equals("m²")) {
                surfaceUnit = "Sq .ft";
                textViewSurface.setText("Size: " + (Math.round(mProperty.getSurface()*10.7639)) + " " + surfaceUnit);
                textViewConvertUnit.setText("convert to square meters");
            } else {
                surfaceUnit = "m²";
                textViewSurface.setText("Size: " + mProperty.getSurface() + " " + surfaceUnit);
                textViewConvertUnit.setText("convert to square foot");
            }
        });

        textViewConvertCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currency.equals("dollar")) {
                    currency = "euro";
                    //textViewPrice.setText(tc.formatPriceNicely((int)(Math.round((mProperty.getPrice())/exchangeRate))));
                    textViewPrice.setText(TypesConversions.formatPriceNicely(Utils.convertDollarToEuro(mProperty.getPrice(),exchangeRate)));
                    textViewConvertCurrency.setText("convert to dollar");
                    imageCurrency.setImageResource(R.drawable.ic_euro);

                } else {
                    currency = "dollar";
                    textViewPrice.setText(TypesConversions.formatPriceNicely(Utils.convertEuroToDollar(mProperty.getPrice(),exchangeRate)));
                    textViewConvertCurrency.setText("convert to euro");
                    imageCurrency.setImageResource(R.drawable.ic_dollar);
                }
            }
        });

        Log.i("alex", "end on create ");

        initObservers();

        return root;
    }



    private void initObservers() {
        propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel.class);
        propertyViewModel.getPropertyById(propertyId).observe(this, new Observer<Property>() {
            public void onChanged(@Nullable Property property) {
                mProperty = property;
                fillDatas();
            }
        });

        agentViewModel = ViewModelProviders.of(this).get(AgentViewModel.class);
        agentViewModel.getAgentById(agentId).observe(this, new Observer<Agent>() {
            public void onChanged(@Nullable Agent agent) {
                mAgent = agent;
                fillDatas();
            }
        });

        rateViewModel = ViewModelProviders.of(this).get(RateViewModel.class);
        rateViewModel.getRates().observe(this, new Observer<List<Rate>>() {
            public void onChanged(@Nullable List<Rate> rates) {
                exchangeRate = rates.get(0).getValue();
            }
        });

        mediaViewModel = ViewModelProviders.of(this).get(OfferMediaViewModel.class);
        mediaViewModel.getMediasByPropertyId(propertyId).observe(this, new Observer<List<OfferMedia>>() {
            public void onChanged(@Nullable List<OfferMedia> medias) {
                mediasList = medias;
                int index = DataProcessing.getMainPictureIndex(propertyId, medias);
                Log.i("alex", "main pic index: " + index);
                Collections.swap(medias, index, 0);

                gallery.setAdapter(new SliderAdapter(context, mediasList));
            }
        });
    }

    private void fillDatas() {

        if (mProperty != null && mAgent != null) {

            Log.i("alex", "detail: offer ID: " + mProperty.getId());

            textViewBuildType.setText(mProperty.getBuildType());

            textViewLocation.setText(mProperty.getCity() + " | " + mProperty.getDistrict());

            String lat = (mProperty.getLocation().split(",", -1))[0];
            String lon = (mProperty.getLocation().split(",", -1))[1];

            String liteMapUrl = "https://maps.google.com/maps/api/staticmap?center=" + lat + "," + lon + "&zoom=12&size=400x200&markers=color:red%7C" + lat + "," + lon + "&sensor=false&key=" + apiKey;
            Glide.with(context)
                    .load(liteMapUrl)
                    .into(liteMap);


            textViewSurface.setText("Size: " + mProperty.getSurface() + " " + surfaceUnit);

            if (mProperty.getRooms() == -1) {
                textViewRooms.setText("N.C");
            } else {
                textViewRooms.setText("Rooms: " + mProperty.getRooms());
            }

            textViewPrice.setText(TypesConversions.formatPriceNicely(mProperty.getPrice()));
            textViewDescription.setText(mProperty.getDescription());

            if (mProperty.getBedrooms() == -1) {
                textViewBeds.setText("N.C");
            } else {
                textViewBeds.setText("Bedrooms: " + mProperty.getBedrooms());
            }

            if (mProperty.getToilets() == -1) {
                textViewToilets.setText("Toilets: N.C");
            } else {
                textViewToilets.setText("Toilets: " + mProperty.getToilets());
            }

            if (mProperty.getShowers() == -1) {
                textViewShowers.setText("Showers: N.C");
            } else {
                textViewShowers.setText("Showers: " + mProperty.getShowers());
            }

            if (mProperty.getBathtubs()== -1) {
                textViewBathtubs.setText("Bathtubs: N.C");
            } else {
                textViewBathtubs.setText("Bathtubs: " + mProperty.getBathtubs());
            }

            if (mProperty.isAircon()) {
                textViewAircon.setText("Air conditionner: yes");
            } else {
                textViewAircon.setText("Air conditionner: no");
            }

            textViewDateoffer.setText("Published on: " + new TypesConversions().getStringFromTimestamp(mProperty.getDateOffer()));
            textViewAgentName.setText("Name: " + mAgent.getFirstName() + " " + mAgent.getLastName());
            textViewAgentPhone.setText("Phone: " + mAgent.getPhone());
            textViewAgentMail.setText("Email: " + mAgent.getEmail());

            if (mProperty.isSold()) {
                imageSold.setVisibility(View.VISIBLE);
                buttonSold.setText("REMOVE THE SOLD STATUS");
                textViewSoldOn.setVisibility(View.VISIBLE);
                textViewSoldOn.setText("Sold on:  " + new TypesConversions().getStringFromTimestamp(mProperty.getDateSale()) );
            }

            Log.i("alex", "poilist as string: " + mProperty.getPois());

            List<String> poiList = Arrays.asList(mProperty.getPois().split(","));
            List<String> recyclerPoiList = new ArrayList<>();
            //HashMap<String, Integer>hashPOI;

            for (int i=0; i<poiList.size(); i++) {
                if (!poiList.get(i).equals("0")) {
                    String poiNumber;
                    if (poiList.get(i).equals("+")){
                        poiNumber = "10+";
                    } else {
                        poiNumber = poiList.get(i);
                    }
                    if (i==0) recyclerPoiList.add("Schools: " + poiNumber );
                    if (i==1) recyclerPoiList.add("Stores: " + poiNumber );
                    if (i==2) recyclerPoiList.add("Parks: " + poiNumber );
                    if (i==3) recyclerPoiList.add("Restaurants: " + poiNumber );
                    if (i==4) recyclerPoiList.add("Subway stations: " + poiNumber );
                }
            }
            Log.i("alex", "recycler list size: " + recyclerPoiList.size());
            for (String p : recyclerPoiList) {
                Log.i("alex", p);
            }

            myAdapter = new OfferDetailPoiRecyclerAdapter(recyclerPoiList);
            poiRecycler.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();

            buttonSold.setOnClickListener(v -> {
                Log.i("alex", "buttonsold clic");
                if (mProperty.isSold()) {
                    propertyViewModel.setAsNotSold(propertyId);
                    propertyViewModel.setSaleDate(propertyId, 0);
                } else {
                    propertyViewModel.setAsSold(propertyId);
                    long ts = (new Timestamp(new Date().getTime())).getTime();
                    propertyViewModel.setSaleDate(propertyId, ts);
                }

                Toast.makeText(context, "change offer sold status", Toast.LENGTH_LONG).show();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_master_frame_layout, new OffersListFragment(), "fragment offers list")
                        .addToBackStack(null)
                        .commit();
            });

            buttonModify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CreateFragment createFragment = CreateFragment.newInstance();
                    Bundle bundle=new Bundle();
                    bundle.putString("action", "modification");
                    bundle.putSerializable("prop", mProperty);
                    createFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.activity_master_frame_layout, createFragment, "fragment create")
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    }

    private AlertDialog getMessageDialog() {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.alert_dialog_message, null);
        alertBuilder.setView(v);
        dialog = alertBuilder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = dialog.findViewById(R.id.dialog_button_send);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "Message sent", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
            }
        });

        return dialog;
    }

    private void callAgent(){
        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE},
                    PERMISSION_REQUEST_CALL);
        } else {
            dialPhoneNumber();
        }
    }

    private void dialPhoneNumber() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + mAgent.getPhone()));
        startActivity(callIntent);
    }

}
