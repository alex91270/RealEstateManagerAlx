package com.example.realestatemanageralx.ui.fragments.offers_list;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.realestatemanageralx.R;
import com.example.realestatemanageralx.datas.DataHolder;
import com.example.realestatemanageralx.ui.fragments.offer_detail.OfferDetailFragment;
import com.example.realestatemanageralx.helpers.DataProcessing;
import com.example.realestatemanageralx.helpers.TypesConversions;
import com.example.realestatemanageralx.model.OfferMedia;
import com.example.realestatemanageralx.model.Property;
import java.util.List;

public class OffersListRecyclerViewAdapter extends RecyclerView.Adapter<OffersListRecyclerViewAdapter.ViewHolder> {

    private final List<Property> listOffers;
    private final List<OfferMedia> listMedias;
    private int positionRecycler;
    private Context context;
    private FragmentManager fragmentManager;

    public OffersListRecyclerViewAdapter(List<Property> offers, List<OfferMedia> medias, FragmentManager fm) {
        listOffers = offers;
        listMedias = medias;
        this.fragmentManager = fm;
    }

    @Override
    public OffersListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_offer, parent, false);

        context = view.getContext();

        return new OffersListRecyclerViewAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final OffersListRecyclerViewAdapter.ViewHolder holder, final int position) {
        positionRecycler = position;
        Property property = listOffers.get(position);

        if (property.isSold()) {
            holder.imageSold.setVisibility(View.VISIBLE);
        }

        holder.textViewPrice.setText(TypesConversions.formatPriceNicely(property.getPrice()));

        holder.textViewLocation.setText(property.getCity() + " - " + property.getDistrict());

        if (property.getRooms() == -1) {
            holder.textViewSurface.setText(property.getSurface() + " m²");
        } else {
            holder.textViewSurface.setText(property.getSurface() + " m²" + " - " + property.getRooms() + " rooms");
        }


        String fileNameMainMedia = DataProcessing.getMainPictureName(property.getId(), listMedias);

        if (!fileNameMainMedia.equals("")) {
            Bitmap bitmap = BitmapFactory.decodeFile(context.getFilesDir().getPath() + "/medias/" + fileNameMainMedia);
            holder.picture.setImageBitmap(bitmap);
        } else {
            holder.picture.setImageResource(R.drawable.nopicture);
        }
        holder.cardView.setOnClickListener(v -> {
            int frameLayout;
            if (context.getResources().getString(R.string.twopanes).equals("false")) {
                frameLayout = R.id.activity_master_frame_layout;
            } else {
                frameLayout = R.id.fragment_list_frame_layout;
            }

            OfferDetailFragment offerDetailFrag = new OfferDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putLong("propertyId", listOffers.get(position).getId());
            bundle.putLong("agentId", listOffers.get(position).getAgentId());
            offerDetailFrag.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(frameLayout, offerDetailFrag, "fragment offer detail")
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return listOffers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewPrice;
        TextView textViewLocation;
        TextView textViewSurface;
        ImageView picture;
        CardView cardView;
        ImageView imageSold;

        public ViewHolder(View view) {
            super(view);
            textViewPrice = view.findViewById(R.id.item_textview_price);
            textViewLocation = view.findViewById(R.id.item_textview_location);
            textViewSurface = view.findViewById(R.id.item_textview_surface);
            picture = view.findViewById(R.id.item_picture);
            cardView = view.findViewById(R.id.item_cardview);
            imageSold = view.findViewById(R.id.item_image_sold);
        }
    }
}
