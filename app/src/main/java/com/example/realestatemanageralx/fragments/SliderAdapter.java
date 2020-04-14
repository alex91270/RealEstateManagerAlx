package com.example.realestatemanageralx.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.realestatemanageralx.R;
import com.example.realestatemanageralx.model.OfferMedia;

import java.util.List;

public class SliderAdapter extends PagerAdapter{

    Context context;
    List<OfferMedia> img;


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

        //Log.i("alex","Image Position -> " + position);
        String extension = img.get(position).getFileName().substring(img.get(position).getFileName().length()-3);
        //Log.i("alex", "extension: " + extension);

        if (extension.equals("jpg")) {
            Log.i("alex", "position: " + position +": JPG");
            Glide.with(context)
                    .load(context.getFilesDir().getPath() + "/medias/" + img.get(position).getFileName())
                    .into(imageView);
        } else {
            Log.i("alex", "position: " + position +": MP4");
           //imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.apercu));
           //imageView.setImageResource(R.drawable.apercu);

            Glide.with(context)
                    .load(context.getFilesDir().getPath() + "/medias/" + img.get(position-1).getFileName())
                    .into(imageView);
        }



        Glide.with(context)
                .load(context.getFilesDir().getPath() + "/medias/" + img.get(position).getFileName())
                .into(imageView);




        container.addView(itemView);

        return itemView;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}


/**public class SliderAdapter extends PagerAdapter {

    private Context context;
    private List<OfferMedia> medias;

    public SliderAdapter(Context context, List<OfferMedia> medias) {
        Log.i("alex", "in slider adapter " );
        this.context = context;
        this.medias = medias;
        Log.i("alex", "media list size: "  + this.medias.size());
    }

    @Override
    public int getCount() {
        return medias.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.i("alex", "instantiate item: ");

        ImageView imageView;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.slider_item,container,false);

        //String extension = medias.get(position).getFileName().substring(medias.get(position).getFileName().length()-3);
        //Log.i("alex", "extension: " + extension);
    */

        /**itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
         */
/**
        imageView = itemView.findViewById(R.id.slider_image_view);

        Glide.with(context)
                .load(context.getFilesDir().getPath() + "/medias/" + medias.get(position).getFileName())
                .into(imageView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
*/