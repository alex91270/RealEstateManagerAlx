package com.example.realestatemanageralx.fragments.create_offer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.realestatemanageralx.R;
import com.example.realestatemanageralx.events.DeleteMediaEvent;
import com.example.realestatemanageralx.helpers.MediaTypesAndCopy;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class MediasRecyclerViewAdapter extends RecyclerView.Adapter<MediasRecyclerViewAdapter.ViewHolder> {
    private ArrayList<String> paths;
    private MediaTypesAndCopy mtc = new MediaTypesAndCopy();
    private int lastSelectedPosition = 0;


    public MediasRecyclerViewAdapter(ArrayList<String> myPaths) {
        paths = myPaths;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_create_media, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String path = paths.get(position);

        holder.radioButton.setChecked(lastSelectedPosition == position);

        if (path.length() > 20) {
            holder.filenameTextView.setText("..." + path.substring(path.length() - 20));
        } else {
            holder.filenameTextView.setText(path);
        }

        if (MediaTypesAndCopy.isImage(path)) {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            holder.picture.setImageBitmap(bitmap);
        } else if (MediaTypesAndCopy.isVideo(path)) {
            holder.picture.setImageResource(R.drawable.video);
        }
    }

    @Override
    public int getItemCount() {
        return paths.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView picture;
        private TextView filenameTextView;
        public RadioButton radioButton;
        private ImageButton deleteButton;

        public ViewHolder(View view) {
            super(view);
            picture = view.findViewById(R.id.create_item_picture);
            filenameTextView = view.findViewById(R.id.create_item_filename);
            radioButton = view.findViewById(R.id.research_radio_button_on_sale);
            deleteButton = view.findViewById(R.id.create_media_delete);

            radioButton.setOnClickListener(v -> {
                lastSelectedPosition = getAdapterPosition();
                notifyDataSetChanged();
            });

            deleteButton.setOnClickListener(v -> EventBus.getDefault().post(new DeleteMediaEvent(paths.get(getAdapterPosition()))));
        }
    }

    public int getMainPicture() {
        return lastSelectedPosition;
    }

}
