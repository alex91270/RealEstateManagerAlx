package com.example.realestatemanageralx.fragments.create_offer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.realestatemanageralx.R;
import java.util.List;

public class OfferCreatePoiRecyclerAdapter extends RecyclerView.Adapter<OfferCreatePoiRecyclerAdapter.ViewHolder> {
    private final List<String> poiList;
    private int positionRecycler;
    private Context context;

    public OfferCreatePoiRecyclerAdapter(List<String> pois) {
        poiList = pois;
    }

    @Override
    public OfferCreatePoiRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_poi_create, parent, false);

        context = view.getContext();

        return new OfferCreatePoiRecyclerAdapter.ViewHolder(view);
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
        RadioButton radioButton;


        public ViewHolder(View view) {
            super(view);
            textViewPoiName = view.findViewById(R.id.item_poi_create_name);
            radioButton = view.findViewById(R.id.item_poi_create_radioButton);
        }
    }
}
