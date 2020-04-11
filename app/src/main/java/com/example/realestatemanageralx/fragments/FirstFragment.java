package com.example.realestatemanageralx.fragments;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.realestatemanageralx.R;
import com.example.realestatemanageralx.fragments.offers_list.OffersListFragment;
import com.example.realestatemanageralx.fragments.offers_list.OffersListRecyclerViewAdapter;
import com.example.realestatemanageralx.helpers.DataProcessing;
import com.example.realestatemanageralx.model.OfferMedia;
import com.example.realestatemanageralx.model.Property;
import com.example.realestatemanageralx.viewmodels.OfferMediaViewModel;
import com.example.realestatemanageralx.viewmodels.PropertyViewModel;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private Button seeOffersButton;
    private ImageView imageNewOffer;
    private PropertyViewModel propertyViewModel;
    private List<Property> propertiesList = new ArrayList<>();
    private OfferMediaViewModel mediaViewModel;
    private List<OfferMedia> mediasList = new ArrayList<>();
    private Context context;
    private Property property;

    public static FirstFragment newInstance() {
        return (new FirstFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_first, container, false);
        context = root.getContext();

        seeOffersButton = root.findViewById(R.id.see_offers_button);
        seeOffersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OffersListFragment offersListFrag= new OffersListFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_master_frame_layout, offersListFrag, "Fragment offersList")
                        .addToBackStack(null)
                        .commit();
            }
        });

        imageNewOffer = root.findViewById(R.id.image_last_offer);
        imageNewOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OfferDetailFragment offerDetailFrag= new OfferDetailFragment();
                Bundle bundle=new Bundle();
                bundle.putLong("propertyId",property.getId());
                bundle.putLong("agentId", property.getAgentId());
                offerDetailFrag.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_master_frame_layout, offerDetailFrag, "fragment offer detail")
                        .addToBackStack(null)
                        .commit();
            }
        });

        initObservers();
        return root;
    }

    private void initObservers() {
        propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel.class);
        propertyViewModel.getPropertiesList().observe(this, new Observer<List<Property>>() {
            public void onChanged(@Nullable List<Property> properties) {
                propertiesList = properties;
                if (propertiesList.size()>0 && mediasList.size()>0)fillLastOffer();
            }
        });

        mediaViewModel = ViewModelProviders.of(this).get(OfferMediaViewModel.class);
        mediaViewModel.getAllMedias().observe(this, new Observer<List<OfferMedia>>() {
            public void onChanged(@Nullable List<OfferMedia> medias) {
                mediasList = medias;
                if (propertiesList.size()>0 && mediasList.size()>0)fillLastOffer();
            }
        });
    }

    private void fillLastOffer() {
        ImageView image = getView().findViewById(R.id.image_last_offer);
        TextView text_district = getView().findViewById(R.id.textView_district);
        TextView text_surface = getView().findViewById(R.id.textView_surface);
        TextView text_beds = getView().findViewById(R.id.textView_bedrooms);


        property = new DataProcessing().getLastOffer(propertiesList);

            String fileNameMainMedia = new DataProcessing().getMainPictureName(property.getId(), mediasList);

            Log.i("alex", "last offer id: " + property.getId());
            Log.i("alex", "last offer surface: " + property.getSurface());

            text_district.setText(property.getDistrict());
            text_surface.setText(String.valueOf(property.getSurface()) + " m²");
            text_beds.setText(String.valueOf(property.getBedrooms()) + " bedrooms");

            Glide.with(context)
                    .load(context.getFilesDir().getPath() + "/medias/" + fileNameMainMedia)
                    .apply(RequestOptions.circleCropTransform())
                    .into(image);
        }
}
