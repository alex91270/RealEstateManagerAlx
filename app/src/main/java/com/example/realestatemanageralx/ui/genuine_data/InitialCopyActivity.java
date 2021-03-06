package com.example.realestatemanageralx.ui.genuine_data;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.realestatemanageralx.R;
import com.example.realestatemanageralx.helpers.MediaTypesAndCopy;
import com.example.realestatemanageralx.ui.MasterActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Activity solicited at app's first launch to create and populate the BDD
 */

public class InitialCopyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_copy);

        ImageView image = findViewById(R.id.imageView8);
        Glide.with(this)
                .load(R.drawable.wait)
                .into(image);

        copyMediaFiles();
    }

    private void copyMediaFiles() {
        File folderToCreate = new File(this.getFilesDir(), "medias");
        folderToCreate.mkdir();

        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("medias");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        if (files != null) for (String filename : files) {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open("medias/" + filename);
                File outFile = new File(getFilesDir().getPath() + "/medias/" + filename);
                out = new FileOutputStream(outFile);
                MediaTypesAndCopy.copyFile(in, out);
            } catch (IOException e) {
                Log.e("alex", "Failed to copy asset file: " + filename, e);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        Log.e("alex", "error closing  file IN");
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        Log.e("alex", "error closing  file IN");
                    }
                }
            }
        }
        startActivity(new Intent(this, MasterActivity.class));
    }
}
