package com.example.realestatemanageralx.fragments.create_offer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realestatemanageralx.R;
import com.example.realestatemanageralx.helpers.MediaTypesAndCopy;

import java.util.ArrayList;

public class MediasRecyclerViewAdapter extends RecyclerView.Adapter<MediasRecyclerViewAdapter.ViewHolder>{
    private ArrayList<String> paths;
    private MediaTypesAndCopy mtc = new MediaTypesAndCopy();
    private int lastSelectedPosition = 0;


    public MediasRecyclerViewAdapter(ArrayList<String> myPaths) {
        paths = myPaths;
        //Log.i("alex", "create adapter; list size: " + paths.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_create_media, parent, false);
       // Log.i("alex", "oncreateviewholder");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //Log.i("alex", "onbindviewholder");
        String path = paths.get(position);

        holder.radioButton.setChecked(lastSelectedPosition == position);

        if (path.length() > 20) {
            holder.filenameTextView.setText("..." + path.substring(path.length()-20));
        } else {
            holder.filenameTextView.setText(path);
        }

        if (mtc.isImage(path)) {
            //Log.i("alex", "it's an image");
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            holder.picture.setImageBitmap(bitmap);
        }else if (mtc.isVideo(path)) {
            holder.picture.setImageResource(R.drawable.video);
        }
    }



    @Override
    public int getItemCount() {
        //Log.i("alex", "itemCount: " + paths.size());
        return paths.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView picture;
        private TextView filenameTextView;
        public RadioButton radioButton;

        public ViewHolder(View view) {
            super(view);
            //Log.i("alex", "public Viewholder");
            picture = view.findViewById(R.id.create_item_picture);
            filenameTextView = view.findViewById(R.id.create_item_filename);
            radioButton = view.findViewById(R.id.radioButton);

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }
    }

    public int getMainPicture() {
        return lastSelectedPosition;
    }
}
