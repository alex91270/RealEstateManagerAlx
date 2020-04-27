package com.example.realestatemanageralx.fragments.offer_detail;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realestatemanageralx.R;
import com.example.realestatemanageralx.fragments.offers_list.OffersListRecyclerViewAdapter;

import java.util.List;

public class OfferDetailPoiRecyclerAdapter extends RecyclerView.Adapter<OfferDetailPoiRecyclerAdapter.ViewHolder>{

    private final List<String> poiList;
    private int positionRecycler;
    private Context context;

    public OfferDetailPoiRecyclerAdapter(List<String> pois) {

        poiList = pois;
        Log.i("alex", "adapter poilist size: " + poiList.size());


    }

    @Override
    public OfferDetailPoiRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_poi_detail, parent, false);

        context = view.getContext();

        return new OfferDetailPoiRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i("alex", "position: " + position);
        holder.textViewPoiName.setText(poiList.get(position));
        Log.i("alex", "poi at this position: " + poiList.get(position));
    }

    @Override
    public int getItemCount() {
        return poiList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewPoiName;


        public ViewHolder(View view) {
            super(view);
            textViewPoiName = view.findViewById(R.id.item_poi_detail_name);
        }
    }
}
