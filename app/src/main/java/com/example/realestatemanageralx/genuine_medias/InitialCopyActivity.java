package com.example.realestatemanageralx.genuine_medias;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.realestatemanageralx.MasterActivity;
import com.example.realestatemanageralx.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

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

        long timeStart = new Date().getTime();
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("medias");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        if (files != null) for (String filename : files) {
            Log.i("alex", "filename in files: " + filename );
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open("medias/" + filename);


                File outFile = new File(getFilesDir().getPath() + "/medias/" + filename );


                out = new FileOutputStream(outFile);
                copyFile(in, out);
            } catch(IOException e) {
                Log.e("alex", "Failed to copy asset file: " + filename, e);
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        // NOOP
                        Log.e("alex", "error closing  file IN");
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        Log.e("alex", "error closing  file IN");
                        // NOOP
                    }
                }
            }
        }
        long timeEnd = new Date().getTime();
        Log.i("alex", "time elapsed: " + (timeEnd - timeStart) + " milliseconds");
        startActivity(new Intent(this, MasterActivity.class));

    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {

        Log.i("alex", "we are in copyfile ");

        long timeStart = new Date().getTime();
        byte[] buffer = new byte[1024];
        int read;

        int bits = 0;

        while((read = in.read(buffer)) != -1){
            bits +=1;
            out.write(buffer, 0, read);
        }

        Log.i("alex", "1 file copieds, number bits: " + bits);
        long timeEnd = new Date().getTime();
    }
}
