package com.example.realestatemanageralx.fragments.create_offer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realestatemanageralx.MasterActivity;
import com.example.realestatemanageralx.R;
import com.example.realestatemanageralx.database.AppDatabase;
import com.example.realestatemanageralx.helpers.MediaTypesAndCopy;
import com.example.realestatemanageralx.login.LoginHolder;
import com.example.realestatemanageralx.model.OfferMedia;
import com.example.realestatemanageralx.model.Property;
import com.example.realestatemanageralx.viewmodels.OfferMediaViewModel;
import com.example.realestatemanageralx.viewmodels.PropertyViewModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

public class CreateFragment extends Fragment implements EasyPermissions.PermissionCallbacks {

    private static int RESULT_LOAD_IMAGE = 1;

    private EditText editCity;
    private EditText editDistrict;
    private EditText editSize;
    private EditText editPrice;
    private EditText editBeds;
    private EditText editToilets;
    private EditText editShowers;
    private EditText editBathtubs;
    private EditText editDecription;
    private CheckBox checkBox;
    private RecyclerView recyclerView;
    private Button buttonPick;
    private Button buttonPublish;
    private MediasRecyclerViewAdapter myAdapter;
    private ArrayList<String> paths = new ArrayList();
    private MediaTypesAndCopy mtc = new MediaTypesAndCopy();
    private Context context;
    private final int RC_STORAGE_PERM = 321;
    private String location;
    private AppDatabase db;
    private PropertyViewModel propertyViewModel;
    private OfferMediaViewModel offerMediaViewModel;

    public static CreateFragment newInstance() {
        return (new CreateFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create, container, false);
        context = this.getActivity();
        editCity = root.findViewById(R.id.create_city_edit);
        editDistrict = root.findViewById(R.id.create_district_edit);
        editDecription = root.findViewById(R.id.create_description_edit);
        editSize= root.findViewById(R.id.create_size_edit);
        editPrice = root.findViewById(R.id.create_price_edit);
        editBeds = root.findViewById(R.id.create_beds_edit);
        editToilets = root.findViewById(R.id.create_toilets_edit);
        editShowers = root.findViewById(R.id.create_showers_edit);
        editBathtubs = root.findViewById(R.id.create_bathtubs_edit);
        checkBox = root.findViewById(R.id.checkBox);
        recyclerView = root.findViewById(R.id.create_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        db = AppDatabase.getDatabase(context);
        propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel.class);
        offerMediaViewModel = ViewModelProviders.of(this).get(OfferMediaViewModel.class);

        buttonPick = root.findViewById(R.id.button_pick);
        buttonPublish = root.findViewById(R.id.create_button_publish);

        buttonPick.setOnClickListener(v -> {
            if (EasyPermissions.hasPermissions(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Log.i("alex", "we have permissions");
                getToGallery();
            } else {
                Log.i("alex", "we don't have permissions");
                EasyPermissions.requestPermissions(getActivity(), "Please grant access to your photo gallery", RC_STORAGE_PERM, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        });

        buttonPublish.setOnClickListener(v -> {
           if (editSize.getText().toString().equals("") || editCity.getText().toString().equals("")
           || editPrice.getText().toString().equals("")) {
               Toast.makeText(context, "Please provide at least a city, price and size", Toast.LENGTH_LONG).show();
           } else { publish();}
        });

        myAdapter = new MediasRecyclerViewAdapter(paths);
        recyclerView.setAdapter(myAdapter);

        location = getArguments().getString("location");

        return root;
    }

    private void getToGallery() {
        Log.i("alex", "permission granted");
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    private void updateRecycler() {
        //Log.i("alex", "updateRecycler; list size: " + paths.size());
        myAdapter = new MediasRecyclerViewAdapter(paths);
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            if (mtc.isImage(picturePath)||mtc.isVideo(picturePath)) {
                Log.i("alex", "picture path: " + picturePath);
                paths.add(picturePath);
                updateRecycler();
            } else {
                Toast.makeText(context, "File type not supported !", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.i("alex", "permission granted");
        getToGallery();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.i("alex", "permission denied");
    }

    private void publish() {

        int beds = -1;
        int showers = -1;
        int bathtubs = -1;
        int toilets = -1;

        if (!editBeds.getText().toString().equals(""))beds = Integer.valueOf(editBeds.getText().toString());
        if (!editShowers.getText().toString().equals(""))showers = Integer.valueOf(editShowers.getText().toString());
        if (!editBathtubs.getText().toString().equals(""))bathtubs = Integer.valueOf(editBathtubs.getText().toString());
        if (!editToilets.getText().toString().equals(""))toilets = Integer.valueOf(editToilets.getText().toString());

        long ts = (new Timestamp(new Date().getTime())).getTime();

        Property property = new Property(
                editDecription.getText().toString(),
                editCity.getText().toString(),
                editDistrict.getText().toString(),
                Integer.valueOf(editSize.getText().toString()),
                beds,
                toilets,
                showers,
                bathtubs,
                checkBox.isChecked(),
                ts,
                location,
                Integer.valueOf(editPrice.getText().toString()),
                LoginHolder.getInstance().getAgentId(),
                false);

        propertyViewModel.insert(property);

        initObserverProperty();
    }

    private void copyMedias(long propertyId) {
        String filename = "";
        int mainMediaIndex = myAdapter.getMainPicture();
        boolean isMain = false;

        if (paths != null) for (String path : paths) {
            Log.i("alex", "path of file to copy: " + path );
            InputStream in = null;
            OutputStream out = null;
            try {
                File inFile = new File (path);
                filename = inFile.getName();
                in = new FileInputStream(inFile);
                File outFile = new File(context.getFilesDir().getPath() + "/medias/" + filename );
                out = new FileOutputStream(outFile);

                mtc.copyFile(in, out);
            } catch(IOException e) {
                Log.e("alex", "Failed to copy file: " + filename, e);
            }
            finally {
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

            isMain = false;
            if (paths.indexOf(path) == mainMediaIndex){
                Log.i("alex", "this is main media !!");
            } else {
                isMain = true;
            }
            offerMediaViewModel.insert(new OfferMedia(propertyId, filename, isMain));
        }
        }

    private void initObserverProperty() {
        propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel.class);
        propertyViewModel.getLastPropertyId().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long id) {
                Log.i("alex", "last inserted id: " + id );
                copyMedias(id);
            }
        });
    }
    }
