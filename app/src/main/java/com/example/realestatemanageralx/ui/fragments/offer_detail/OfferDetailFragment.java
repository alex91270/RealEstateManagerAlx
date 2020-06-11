package com.example.realestatemanageralx.ui.fragments.offer_detail;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.bumptech.glide.Glide;
import com.example.realestatemanageralx.R;
import com.example.realestatemanageralx.databinding.FragmentOfferDetailBinding;
import com.example.realestatemanageralx.datas.DataHolder;
import com.example.realestatemanageralx.ui.fragments.MapViewFragment;
import com.example.realestatemanageralx.ui.fragments.SliderAdapter;
import com.example.realestatemanageralx.ui.fragments.create_offer.CreateFragment;
import com.example.realestatemanageralx.ui.fragments.offers_list.OffersListFragment;
import com.example.realestatemanageralx.helpers.DataProcessing;
import com.example.realestatemanageralx.helpers.TypesConversions;
import com.example.realestatemanageralx.helpers.Utils;
import com.example.realestatemanageralx.model.Agent;
import com.example.realestatemanageralx.model.OfferMedia;
import com.example.realestatemanageralx.model.Property;
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

    private FragmentOfferDetailBinding binding;
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
    private OfferDetailPoiRecyclerAdapter myAdapter;
    private LayoutInflater galleryInflater;
    private String surfaceUnit = "m²";
    private String currency = "dollar";
    private double exchangeRate;
    private Context context;
    private AlertDialog dialog = null;
    private final int PERMISSION_REQUEST_CALL = 123;

    public static OfferDetailFragment newInstance() {
        return (new OfferDetailFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentOfferDetailBinding.inflate(getLayoutInflater());
        context = binding.getRoot().getContext();
        apiKey = context.getString(R.string.google_maps_key);
        propertyId = getArguments().getLong("propertyId");
        agentId = getArguments().getLong("agentId");
        galleryInflater = LayoutInflater.from(context);
        binding.detailRecyclerPoi.setLayoutManager(new LinearLayoutManager(context));

        if (DataHolder.getInstance().getIsAgentLogged()) {
            binding.detailButtonSold.setVisibility(View.VISIBLE);
            binding.detailButtonSold.setEnabled(true);
            binding.detailButtonModify.setVisibility(View.VISIBLE);
            binding.detailButtonModify.setEnabled(true);
        }

        binding.detailButtonMail.setOnClickListener(v -> {
            final AlertDialog dialog = getMessageDialog();
            dialog.show();
        });

        binding.detailButtonCall.setOnClickListener(v -> callAgent());

        binding.detailMapLite.setOnClickListener(v -> {
            MapViewFragment mapViewFrag = new MapViewFragment();
            Bundle bundle = new Bundle();
            bundle.putLong("propertyId", propertyId);
            mapViewFrag.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_master_frame_layout, mapViewFrag, "fragment map view")
                    .addToBackStack(null)
                    .commit();
        });

        binding.detailTextConvertUnit.setOnClickListener(v -> {
            if (surfaceUnit.equals("m²")) {
                surfaceUnit = "Sq .ft";
                binding.detailTextSurface.setText("Size: " + (Math.round(mProperty.getSurface() * 10.7639)) + " " + surfaceUnit);
                binding.detailTextConvertUnit.setText("convert to square meters");
            } else {
                surfaceUnit = "m²";
                binding.detailTextSurface.setText("Size: " + mProperty.getSurface() + " " + surfaceUnit);
                binding.detailTextConvertUnit.setText("convert to square foot");
            }
        });

        binding.detailTextCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currency.equals("dollar")) {
                    currency = "euro";
                    binding.detailTextPrice.setText(TypesConversions.formatPriceNicely(Utils.convertDollarToEuro(mProperty.getPrice(), exchangeRate)));
                    binding.detailTextCurrency.setText("convert to dollar");
                    binding.imageViewCurrency.setImageResource(R.drawable.ic_euro);

                } else {
                    currency = "dollar";
                    binding.detailTextPrice.setText(TypesConversions.formatPriceNicely(Utils.convertEuroToDollar(mProperty.getPrice(), exchangeRate)));
                    binding.detailTextCurrency.setText("convert to euro");
                    binding.imageViewCurrency.setImageResource(R.drawable.ic_dollar);
                }
            }
        });
        initObservers();

        return binding.getRoot();
    }


    private void initObservers() {
        propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel.class);
        propertyViewModel.getPropertyById(propertyId).observe(this, property -> {
            mProperty = property;
            fillDatas();
        });

        agentViewModel = ViewModelProviders.of(this).get(AgentViewModel.class);
        agentViewModel.getAgentById(agentId).observe(this, agent -> {
            mAgent = agent;
            fillDatas();
        });

        rateViewModel = ViewModelProviders.of(this).get(RateViewModel.class);
        rateViewModel.getRates().observe(this, rates -> exchangeRate = rates.get(0).getValue());

        mediaViewModel = ViewModelProviders.of(this).get(OfferMediaViewModel.class);
        mediaViewModel.getMediasByPropertyId(propertyId).observe(this, medias -> {
            mediasList = medias;
            if (medias.size() > 0) {
                int index = DataProcessing.getMainPictureIndex(propertyId, medias);
                Collections.swap(medias, index, 0);
            }
            binding.viewPagerSlider.setAdapter(new SliderAdapter(context, mediasList));
        });
    }

    private void fillDatas() {

        if (mProperty != null && mAgent != null) {
            binding.detailTextType.setText(mProperty.getBuildType());
            binding.detailTextLocation.setText(mProperty.getCity() + " | " + mProperty.getDistrict());
            String lat = (mProperty.getLocation().split(",", -1))[0];
            String lon = (mProperty.getLocation().split(",", -1))[1];
            String liteMapUrl = "https://maps.google.com/maps/api/staticmap?center=" + lat + "," + lon + "&zoom=12&size=400x200&markers=color:red%7C" + lat + "," + lon + "&sensor=false&key=" + apiKey;
            Glide.with(context)
                    .load(liteMapUrl)
                    .into(binding.detailMapLite);

            binding.detailTextSurface.setText("Size: " + mProperty.getSurface() + " " + surfaceUnit);
            binding.detailTextRooms.setText(mProperty.getRooms() == -1 ? "N.C" : "Rooms: " + mProperty.getRooms());
            binding.detailTextPrice.setText(TypesConversions.formatPriceNicely(mProperty.getPrice()));
            binding.detailTextDescription.setText(mProperty.getDescription());
            binding.detailTextBeds.setText(mProperty.getBedrooms() == -1 ? "N.C" : "Bedrooms: " + mProperty.getBedrooms());
            binding.detailTextToilets.setText(mProperty.getToilets() == -1 ? "N.C" : "Toilets: " + mProperty.getToilets());
            binding.detailTextShowers.setText(mProperty.getShowers() == -1 ? "N.C" : "Showers: " + mProperty.getShowers());
            binding.detailTextBathtubs.setText(mProperty.getBathtubs() == -1 ? "N.C" : "Bathtubs: " + mProperty.getBathtubs());
            binding.detailTextAircon.setText(mProperty.isAircon() ? "Air conditionner: yes" : "Air conditionner: no");
            binding.detailTextDateOffer.setText("Published on: " + new TypesConversions().getStringFromTimestamp(mProperty.getDateOffer()));
            binding.detailTextAgentName.setText("Name: " + mAgent.getFirstName() + " " + mAgent.getLastName());
            binding.detailTextAgentPhone.setText("Phone: " + mAgent.getPhone());
            binding.detailTextAgentEmail.setText("Email: " + mAgent.getEmail());

            if (mProperty.isSold()) {
                binding.detailImageSold.setVisibility(View.VISIBLE);
                binding.detailButtonSold.setText("REMOVE THE SOLD STATUS");
                binding.detailTextSoldOn.setVisibility(View.VISIBLE);
                binding.detailTextSoldOn.setText("Sold on:  " + new TypesConversions().getStringFromTimestamp(mProperty.getDateSale()));
            }

            List<String> poiList = Arrays.asList(mProperty.getPois().split(","));
            List<String> recyclerPoiList = new ArrayList<>();

            for (int i = 0; i < poiList.size(); i++) {
                if (!poiList.get(i).equals("0")) {
                    String poiNumber;
                    if (poiList.get(i).equals("+")) {
                        poiNumber = "10+";
                    } else {
                        poiNumber = poiList.get(i);
                    }
                    if (i == 0) recyclerPoiList.add("Schools: " + poiNumber);
                    if (i == 1) recyclerPoiList.add("Stores: " + poiNumber);
                    if (i == 2) recyclerPoiList.add("Parks: " + poiNumber);
                    if (i == 3) recyclerPoiList.add("Restaurants: " + poiNumber);
                    if (i == 4) recyclerPoiList.add("Subway stations: " + poiNumber);
                }
            }

            myAdapter = new OfferDetailPoiRecyclerAdapter(recyclerPoiList);
            binding.detailRecyclerPoi.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();

            binding.detailButtonSold.setOnClickListener(v -> {
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

            binding.detailButtonModify.setOnClickListener(v -> {
                CreateFragment createFragment = CreateFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString("action", "modification");
                bundle.putSerializable("prop", mProperty);
                createFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_master_frame_layout, createFragment, "fragment create")
                        .addToBackStack(null)
                        .commit();
            });
        }
    }

    private AlertDialog getMessageDialog() {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.alert_dialog_message, null);
        alertBuilder.setView(v);
        dialog = alertBuilder.create();
        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.findViewById(R.id.dialog_button_send);
            button.setOnClickListener(view -> {
                Toast.makeText(context, "Message sent", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            });
        });

        return dialog;
    }

    private void callAgent() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
