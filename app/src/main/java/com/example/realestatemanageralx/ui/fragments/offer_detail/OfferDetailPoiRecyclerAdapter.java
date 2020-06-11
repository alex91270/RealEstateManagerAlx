package com.example.realestatemanageralx.ui.fragments.offer_detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.realestatemanageralx.R;
import java.util.List;

public class OfferDetailPoiRecyclerAdapter extends RecyclerView.Adapter<OfferDetailPoiRecyclerAdapter.ViewHolder> {

    private final List<String> poiList;
    private Context context;

    public OfferDetailPoiRecyclerAdapter(List<String> pois) {
        poiList = pois;
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
        holder.textViewPoiName.setText(poiList.get(position));
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
