package com.example.realestatemanageralx.fragments;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.realestatemanageralx.R;
import com.example.realestatemanageralx.helpers.MediaTypesAndCopy;
import com.example.realestatemanageralx.model.OfferMedia;
import com.example.realestatemanageralx.video.VideoActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SliderAdapter extends PagerAdapter{

    Context context;

    List<OfferMedia> img;
    /**
    ArrayList<String> imageExtensions = new ArrayList<String>(
            Arrays.asList("jpg", "bmp", "gif", "png"));

    ArrayList<String> videoExtensions = new ArrayList<String>(
            Arrays.asList("3gp", "mp4", "mkv"));
     */
    MediaTypesAndCopy md = new MediaTypesAndCopy();



    //int[] img = {R.drawable.apercu,R.drawable.apercu,R.drawable.apercu,
            //R.drawable.apercu,R.drawable.apercu,R.drawable.apercu};

    public SliderAdapter(Context context, List<OfferMedia> medias) {
        this.context = context;
        this.img = medias;
    }

    @Override
    public int getCount() {
        return img.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView imageView;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.slider_item,container,false);

        imageView = itemView.findViewById(R.id.slider_image_view);

        //String extension = img.get(position).getFileName().substring(img.get(position).getFileName().length()-3);

        if (md.isImage(img.get(position).getFileName())) {
         Glide.with(context)
         .load(context.getFilesDir().getPath() + "/medias/" + img.get(position).getFileName())
         .into(imageView);


        } else if (md.isVideo(img.get(position).getFileName())){
         imageView.setImageResource(R.drawable.video);
         imageView.setOnClickListener(v -> {
             Intent intent = new Intent(context, VideoActivity.class);
             intent.putExtra("path", context.getFilesDir().getPath() + "/medias/" + img.get(position).getFileName());
             context.startActivity(intent);
         });

        }

        container.addView(itemView);

        return itemView;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}