package com.example.realestatemanageralx.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.realestatemanageralx.R;
import com.example.realestatemanageralx.helpers.TypesConversions;
import com.example.realestatemanageralx.model.Agent;
import com.example.realestatemanageralx.model.OfferMedia;
import com.example.realestatemanageralx.model.Property;
import com.example.realestatemanageralx.model.Rate;
import com.example.realestatemanageralx.viewmodels.AgentViewModel;
import com.example.realestatemanageralx.viewmodels.OfferMediaViewModel;
import com.example.realestatemanageralx.viewmodels.PropertyViewModel;
import com.example.realestatemanageralx.viewmodels.RateViewModel;
import java.util.ArrayList;
import java.util.List;

public class OfferDetailFragment extends Fragment {
    private PropertyViewModel propertyViewModel;
    private List<Property> propertiesList = new ArrayList<>();
    private OfferMediaViewModel mediaViewModel;
    private List<OfferMedia> mediasList = new ArrayList<>();
    private AgentViewModel agentViewModel;
    private Agent mAgent;
    private RateViewModel rateViewModel;
    //private List<Rate> rateList;
    private Property mProperty;
    private Long propertyId;
    private Long agentId;

    private TextView textViewLocation;
    private TextView textViewSurface;
    private TextView textViewBeds;
    private TextView textViewPrice;
    private TextView textViewDescription;
    private TextView textViewToilets;
    private TextView textViewShowers;
    private TextView textViewBathtubs;
    private TextView textViewAircon;
    private TextView textViewDateoffer;
    private TextView textViewAgentName;
    private TextView textViewAgentPhone;
    private TextView textViewAgentMail;
    private Button buttonMail;
    private Button buttonPhone;
    private TextView textViewSeeonmap;
    private TextView textViewConvertUnit;
    private TextView textViewConvertCurrency;
    private ImageView imageCurrency;
    private LinearLayout gallery;
    private LayoutInflater galleryInflater;

    private String surfaceUnit = "m²";
    private String currency = "dollar";

    private double exchangeRate;
    private double rateUpdateDate;


    private Context context;
    private AlertDialog dialog = null;
    private final int PERMISSION_REQUEST_CALL = 123;
    private TypesConversions tc = new TypesConversions();


    public static OfferDetailFragment newInstance() {
        return (new OfferDetailFragment());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_offer_detail, container, false);
        context = root.getContext();
        propertyId = getArguments().getLong("propertyId");
        agentId = getArguments().getLong("agentId");

        textViewLocation = root.findViewById(R.id.detail_text_location);
      textViewSurface = root.findViewById(R.id.detail_text_surface);
       textViewBeds = root.findViewById(R.id.detail_text_beds);
        textViewPrice = root.findViewById(R.id.detail_text_price);
         textViewDescription = root.findViewById(R.id.detail_text_description);
         textViewToilets = root.findViewById(R.id.detail_text_toilets);
        textViewShowers = root.findViewById(R.id.detail_text_showers);
         textViewBathtubs = root.findViewById(R.id.detail_text_bathtubs);
         textViewAircon = root.findViewById(R.id.detail_text_aircon);
        textViewDateoffer = root.findViewById(R.id.detail_text_date_offer);
        textViewAgentName = root.findViewById(R.id.detail_text_agent_name);
        textViewAgentPhone = root.findViewById(R.id.detail_text_agent_phone);
        textViewAgentMail = root.findViewById(R.id.detail_text_agent_email);
        textViewSeeonmap = root.findViewById(R.id.detail_text_seeonmap);
        textViewConvertUnit = root.findViewById(R.id.detail_text_convert_unit);
        textViewConvertCurrency = root.findViewById(R.id.detail_text_currency);
        buttonMail = root.findViewById(R.id.detail_button_mail);
        buttonPhone = root.findViewById(R.id.detail_button_call);
        imageCurrency = root.findViewById(R.id.imageViewCurrency);
        gallery = root.findViewById(R.id.gallery);
        galleryInflater = LayoutInflater.from(root.getContext());

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

        textViewSeeonmap.setOnClickListener(new View.OnClickListener() {
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
                surfaceUnit = "Sq m";
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
                    textViewPrice.setText(tc.formatPriceNicely((int)(Math.round((mProperty.getPrice())*exchangeRate))));
                    textViewConvertCurrency.setText("convert to dollar");
                    imageCurrency.setImageResource(R.drawable.ic_euro);

                } else {
                    currency = "dollar";
                    textViewPrice.setText(tc.formatPriceNicely(mProperty.getPrice()));
                    textViewConvertCurrency.setText("convert to euro");
                    imageCurrency.setImageResource(R.drawable.ic_dollar);
                }
            }
        });
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
                rateUpdateDate = rates.get(1).getValue();
                Log.i("alex", "exchangerate: " + exchangeRate);
                Log.i("alex", "rate date: " + rateUpdateDate);
            }
        });

        mediaViewModel = ViewModelProviders.of(this).get(OfferMediaViewModel.class);
        mediaViewModel.getMediasByPropertyId(propertyId).observe(this, new Observer<List<OfferMedia>>() {
            public void onChanged(@Nullable List<OfferMedia> medias) {
                mediasList = medias;
                fillPictures();
                //Log.i("alex", "medias list size: " + mediasList.size() );

            }
        });
    }

    private void fillDatas() {

        if (mProperty != null && mAgent != null) {

            textViewLocation.setText(mProperty.getCity() + " | " + mProperty.getDistrict());
            textViewSurface.setText("Size: " + mProperty.getSurface() + " " + surfaceUnit);
            textViewBeds.setText("Bedrooms: " + mProperty.getBedrooms());
            textViewPrice.setText(tc.formatPriceNicely(mProperty.getPrice()));
            textViewDescription.setText(mProperty.getDescription());
            textViewToilets.setText("Toilets: " + mProperty.getToilets());
            textViewShowers.setText("Showers: " + mProperty.getShowers());
            textViewBathtubs.setText("Bathtubs: " + mProperty.getBathtubs());

            if (mProperty.isAircon()) {
                textViewAircon.setText("Air conditionner: yes");
            } else {
                textViewAircon.setText("Air conditionner: no");
            }

            textViewDateoffer.setText("Published on: " + new TypesConversions().getStringFromTimestamp(mProperty.getDateOffer()));
            textViewAgentName.setText("Name: " + mAgent.getFirstName() + " " + mAgent.getLastName());
            textViewAgentPhone.setText("Phone: " + mAgent.getPhone());
            textViewAgentMail.setText("Email: " + mAgent.getEmail());

        }

    }

    private void fillPictures() {
        for (OfferMedia media : mediasList) {
            Log.i("alex", "another view");
            View mView = galleryInflater.inflate(R.layout.item_gallery, gallery, false);
            ImageView image = mView.findViewById(R.id.image_gallery_item);

            Glide.with(context)
                    .load(context.getFilesDir().getPath() + "/medias/" + media.getFileName())
                    //.apply(RequestOptions.circleCropTransform())
                    .into(image);

            gallery.addView(mView);
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
