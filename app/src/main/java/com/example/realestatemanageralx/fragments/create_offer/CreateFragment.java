package com.example.realestatemanageralx.fragments.create_offer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.realestatemanageralx.R;
import com.example.realestatemanageralx.database.AppDatabase;
import com.example.realestatemanageralx.datas.DataHolder;
import com.example.realestatemanageralx.events.DeleteMediaEvent;
import com.example.realestatemanageralx.fragments.FirstFragment;
import com.example.realestatemanageralx.helpers.DataProcessing;
import com.example.realestatemanageralx.helpers.MediaTypesAndCopy;
import com.example.realestatemanageralx.model.OfferMedia;
import com.example.realestatemanageralx.model.Property;
import com.example.realestatemanageralx.viewmodels.OfferMediaViewModel;
import com.example.realestatemanageralx.viewmodels.OnPropertyInserted;
import com.example.realestatemanageralx.viewmodels.PropertyViewModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import pub.devrel.easypermissions.EasyPermissions;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import static android.app.Activity.RESULT_OK;

public class CreateFragment extends Fragment implements EasyPermissions.PermissionCallbacks {

    private static int RESULT_LOAD_IMAGE = 1;
    private static String apiKey;
    private Spinner spinnerType;
    private EditText editSize;
    private EditText editPrice;
    private EditText editRooms;
    private EditText editBeds;
    private EditText editToilets;
    private EditText editShowers;
    private EditText editBathtubs;
    private EditText editDescription;
    private CheckBox checkBox;
    private RecyclerView recyclerView;
    private Button buttonLocation;
    private Button buttonPick;
    private Button buttonPublish;
    private MediasRecyclerViewAdapter myAdapter;
    private ArrayList<String> paths = new ArrayList();
    private Context context;
    private final int RC_STORAGE_PERM = 321;
    private AppDatabase db;
    private PropertyViewModel propertyViewModel;
    private OfferMediaViewModel offerMediaViewModel;
    private boolean modif;
    private Property tempProp;
    private ImageView liteMap;
    private LinearLayout medias_columns_titles;
    List<String> spinnerArray;
    private boolean isNewOffer = false;

    public static CreateFragment newInstance() {
        return (new CreateFragment());
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onDeleteMedia(DeleteMediaEvent event) {
        Log.i("alex", "delete media triggered");
        paths.remove(event.path);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create, container, false);
        context = this.getActivity();
        apiKey = context.getString(R.string.google_maps_key);
        liteMap = root.findViewById(R.id.create_map_lite);
        spinnerType = root.findViewById(R.id.create_spinner_type);
        editDescription = root.findViewById(R.id.create_description_edit);
        editSize= root.findViewById(R.id.create_size_edit);
        editRooms = root.findViewById(R.id.create_rooms_edit);
        editPrice = root.findViewById(R.id.create_price_edit);
        editBeds = root.findViewById(R.id.create_beds_edit);
        editToilets = root.findViewById(R.id.create_toilets_edit);
        editShowers = root.findViewById(R.id.create_showers_edit);
        editBathtubs = root.findViewById(R.id.create_bathtubs_edit);
        checkBox = root.findViewById(R.id.checkBox);
        medias_columns_titles = root.findViewById(R.id.create_medias_columns);
        recyclerView = root.findViewById(R.id.create_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        db = AppDatabase.getDatabase(context);
        propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel.class);
        offerMediaViewModel = ViewModelProviders.of(this).get(OfferMediaViewModel.class);
        buttonPick = root.findViewById(R.id.button_pick);
        buttonPublish = root.findViewById(R.id.create_button_publish);
        buttonLocation = root.findViewById(R.id.create_button_location);

        EasyPermissions.requestPermissions(getActivity(), "Please grant access to your photo gallery", RC_STORAGE_PERM, Manifest.permission.READ_EXTERNAL_STORAGE);

        buttonPick.setOnClickListener(v -> {

            if (EasyPermissions.hasPermissions(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                getToGallery();
            } else {
                EasyPermissions.requestPermissions(getActivity(), "Please grant access to your photo gallery", RC_STORAGE_PERM, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        });

        buttonPublish.setOnClickListener(v -> {
           if (editSize.getText().toString().equals("") || editPrice.getText().toString().equals("") || tempProp.getCity().equals("")) {
               Toast.makeText(context, "Please provide at least a location, price and size", Toast.LENGTH_LONG).show();
           } else {
               saveTemporaryProp();
               publish();}
        });

        buttonLocation.setOnClickListener(v -> {
            saveTemporaryProp();
            LocationPickerFragment pickerFragment = LocationPickerFragment.newInstance();
            Bundle bundle=new Bundle();
            bundle.putSerializable("prop", tempProp);
            pickerFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_master_frame_layout, pickerFragment, "fragment locationPicker")
                    .addToBackStack(null)
                    .commit();
        });

        myAdapter = new MediasRecyclerViewAdapter(paths);
        recyclerView.setAdapter(myAdapter);
        spinnerArray =  new ArrayList<>();
        spinnerArray.add("Apartment");
        spinnerArray.add("House");
        spinnerArray.add("Duplex");
        spinnerArray.add("Building");
        spinnerArray.add("Land");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.my_spinner, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);
        spinnerType.setSelection(0);

        modif = false;
        if (getArguments().getString("action").equals("modification")) {
            Log.i("alex", "bundle is modification ");
            buttonLocation.setText("CHANGE LOCATION");
            modif = true;
            tempProp = (Property) getArguments().getSerializable("prop");
            fillFieldsFromTemp();
            initMediasObserver();
        }else {
            Log.i("alex", "bundle is creation ");
            tempProp = DataProcessing.buildEmptyProperty();
        }

        if (tempProp.getId() == 0) isNewOffer = true;
        return root;
    }

    private void fillFieldsFromTemp() {
        editDescription.setText(tempProp.getDescription());
        if (tempProp.getSurface()!= -1) editSize.setText(String.valueOf(tempProp.getSurface()));
        if (tempProp.getPrice()!= -1) editPrice.setText(String.valueOf(tempProp.getPrice()));
        if (tempProp.getRooms()!= -1) editRooms.setText(String.valueOf(tempProp.getRooms()));
        if (tempProp.getBedrooms()!= -1) editBeds.setText(String.valueOf(tempProp.getBedrooms()));
        if (tempProp.getToilets()!= -1) editToilets.setText(String.valueOf(tempProp.getToilets()));
        if (tempProp.getShowers()!= -1) editShowers.setText(String.valueOf(tempProp.getShowers()));
        if (tempProp.getBathtubs()!= -1) editBathtubs.setText(String.valueOf(tempProp.getBathtubs()));
        if(tempProp.isAircon()) {checkBox.setChecked(true);}
        spinnerType.setSelection(spinnerArray.indexOf(tempProp.getBuildType()));

        String lat = (tempProp.getLocation().split(",", -1))[0];
        String lon = (tempProp.getLocation().split(",", -1))[1];
        String liteMapUrl = "https://maps.google.com/maps/api/staticmap?center=" + lat + "," + lon + "&zoom=12&size=400x200&markers=color:red%7C" + lat + "," + lon + "&sensor=false&key=" + apiKey;
        Glide.with(context)
                .load(liteMapUrl)
                .into(liteMap);
        }

        private void initMediasObserver() {
            offerMediaViewModel = ViewModelProviders.of(this).get(OfferMediaViewModel.class);
            offerMediaViewModel.getMediasByPropertyId(tempProp.getId()).observe(this, new Observer<List<OfferMedia>>() {
                public void onChanged(@Nullable List<OfferMedia> medias) {

                    Log.i("alex", "medias list observer changed. size: " + medias.size() );
                    if (medias.size() > 0) {
                        paths.clear();
                        for (OfferMedia media : medias) {
                            paths.add(context.getFilesDir().getPath() + "/medias/" + media.getFileName());
                        }
                        int index = DataProcessing.getMainPictureIndex(tempProp.getId(), medias);
                        Log.i("alex", "index: " + index);
                        Collections.swap(paths, index, 0);
                        updateRecycler();
                    }
                }
            });
        }

    private void getToGallery() {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    private void updateRecycler() {
        if (paths.size()>0){
            medias_columns_titles.setVisibility(View.VISIBLE);
        } else { medias_columns_titles.setVisibility(View.INVISIBLE);}
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

            if (MediaTypesAndCopy.isImage(picturePath)||MediaTypesAndCopy.isVideo(picturePath)) {
                Log.i("alex", "picture path: " + picturePath);
                paths.add(picturePath);
                updateRecycler();
            } else {
                Toast.makeText(context, "File type not supported !", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        getToGallery();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
    }

    private void saveTemporaryProp() {
        if (!editRooms.getText().toString().equals(""))tempProp.setRooms(Integer.valueOf(editRooms.getText().toString()));
        if (!editBeds.getText().toString().equals(""))tempProp.setBedrooms(Integer.valueOf(editBeds.getText().toString()));
        if (!editShowers.getText().toString().equals(""))tempProp.setShowers(Integer.valueOf(editShowers.getText().toString()));
        if (!editBathtubs.getText().toString().equals(""))tempProp.setBathtubs(Integer.valueOf(editBathtubs.getText().toString()));
        if (!editToilets.getText().toString().equals(""))tempProp.setToilets(Integer.valueOf(editToilets.getText().toString()));
        if (!editSize.getText().toString().equals(""))tempProp.setSurface(Integer.valueOf(editSize.getText().toString()));
        if (!editPrice.getText().toString().equals(""))tempProp.setPrice(Integer.valueOf(editPrice.getText().toString()));
        tempProp.setDateOffer((new Timestamp(new Date().getTime())).getTime());
        tempProp.setDescription(editDescription.getText().toString());
        tempProp.setAircon(checkBox.isChecked());
        tempProp.setBuildType(spinnerType.getSelectedItem().toString());
    }

    private void publish() {

        if(!isNewOffer) {
            offerMediaViewModel.deleteAllMediasOfThisProperty(tempProp.getId());
        }
            propertyViewModel.insert(tempProp, id -> copyMedias(id));
    }

    private void copyMedias(long propertyId) {

        Log.i("alex", "copyMedias. paths size: " + paths.size() + "PID: " + propertyId  );
        String filename;
        int mainMediaIndex = myAdapter.getMainPicture();
        boolean isMain;

        if (paths != null) for (String path : paths) {
            Log.i("alex", "path of file to copy: " + path );

            isMain = false;
            if (paths.indexOf(path) == mainMediaIndex){
                Log.i("alex", "this is main media !!");
                isMain = true;
            }
            File inFile = new File (path);
            filename = inFile.getName();
            File outFile = new File(context.getFilesDir().getPath() + "/medias/" + filename );

            Log.i("alex", "insert media, ID: " + propertyId + " filename: " + filename + " " + isMain);
            offerMediaViewModel.insert(new OfferMedia(propertyId, filename, isMain));

            InputStream in = null;
            OutputStream out = null;

            if (!inFile.getPath().equals(outFile.getPath())) {
                try {
                    in = new FileInputStream(inFile);
                    out = new FileOutputStream(outFile);

                    Log.i("alex", "infile: " + inFile.getPath());
                    Log.i("alex", "outfile: " + outFile.getPath());

                    MediaTypesAndCopy.copyFile(in, out);
                } catch (IOException e) {
                    Log.e("alex", "Failed to copy file: " + filename, e);
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
        }

        Handler mainHandler = new Handler(context.getMainLooper());
        Runnable myRunnable = () -> {
            Toast.makeText(context, "Offer published!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_master_frame_layout, new FirstFragment(), "fragment first")
                    .addToBackStack(null)
                    .commit();
        };

        mainHandler.post(myRunnable);
        }
}
