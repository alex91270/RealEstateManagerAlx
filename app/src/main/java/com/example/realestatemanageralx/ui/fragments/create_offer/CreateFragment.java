package com.example.realestatemanageralx.ui.fragments.create_offer;

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
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.bumptech.glide.Glide;
import com.example.realestatemanageralx.R;
import com.example.realestatemanageralx.database.AppDatabase;
import com.example.realestatemanageralx.databinding.FragmentCreateBinding;
import com.example.realestatemanageralx.events.DeleteMediaEvent;
import com.example.realestatemanageralx.ui.fragments.FirstFragment;
import com.example.realestatemanageralx.helpers.DataProcessing;
import com.example.realestatemanageralx.helpers.MediaTypesAndCopy;
import com.example.realestatemanageralx.model.OfferMedia;
import com.example.realestatemanageralx.model.Property;
import com.example.realestatemanageralx.viewmodels.OfferMediaViewModel;
import com.example.realestatemanageralx.viewmodels.PropertyViewModel;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
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
import static android.app.Activity.RESULT_OK;

public class CreateFragment extends Fragment implements EasyPermissions.PermissionCallbacks {

    private FragmentCreateBinding binding;
    private static int RESULT_LOAD_IMAGE = 1;
    private static String apiKey;
    private MediasRecyclerViewAdapter myAdapter;
    private ArrayList<String> paths = new ArrayList();
    private Context context;
    private final int RC_STORAGE_PERM = 321;
    private AppDatabase db;
    private PropertyViewModel propertyViewModel;
    private OfferMediaViewModel offerMediaViewModel;
    private boolean modif;
    private Property tempProp;
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
        //When the eventBus warns a media has been deleted in the recyclerView
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
        binding = FragmentCreateBinding.inflate(getLayoutInflater());
        context = binding.getRoot().getContext();
        apiKey = context.getString(R.string.google_maps_key);
        binding.createRecycler.setLayoutManager(new LinearLayoutManager(context));
        binding.createRecycler.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        db = AppDatabase.getDatabase(context);
        propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel.class);
        offerMediaViewModel = ViewModelProviders.of(this).get(OfferMediaViewModel.class);

        EasyPermissions.requestPermissions(getActivity(), "Please grant access to your photo gallery", RC_STORAGE_PERM, Manifest.permission.READ_EXTERNAL_STORAGE);

        binding.buttonPick.setOnClickListener(v -> {

            if (EasyPermissions.hasPermissions(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                getToGallery();
            } else {
                EasyPermissions.requestPermissions(getActivity(), "Please grant access to your photo gallery", RC_STORAGE_PERM, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        });

        binding.createButtonPublish.setOnClickListener(v -> {
            if (binding.createSizeEdit.getText().toString().equals("") || binding.createPriceEdit.getText().toString().equals("") || tempProp.getCity().equals("")) {
                Toast.makeText(context, "Please provide at least a location, price and size", Toast.LENGTH_LONG).show();
            } else {
                saveTemporaryProp();
                publish();
            }
        });

        binding.createButtonLocation.setOnClickListener(v -> {
            //saves the temporary created offer before picking up a location
            saveTemporaryProp();
            LocationPickerFragment pickerFragment = LocationPickerFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putSerializable("prop", tempProp);
            pickerFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_master_frame_layout, pickerFragment, "fragment locationPicker")
                    .addToBackStack(null)
                    .commit();
        });

        myAdapter = new MediasRecyclerViewAdapter(paths);
        binding.createRecycler.setAdapter(myAdapter);
        spinnerArray = new ArrayList<>();
        spinnerArray.add("Apartment");
        spinnerArray.add("House");
        spinnerArray.add("Duplex");
        spinnerArray.add("Building");
        spinnerArray.add("Land");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.my_spinner, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.createSpinnerType.setAdapter(adapter);
        binding.createSpinnerType.setSelection(0);

        modif = false;
        if (getArguments().getString("action").equals("modification")) {
            //if this concerns an existing offers, gets it back to fill the fields
            binding.createButtonLocation.setText("CHANGE LOCATION");
            modif = true;
            tempProp = (Property) getArguments().getSerializable("prop");
            fillFieldsFromTemp();
            initMediasObserver();
        } else {
            //if this concerns a new offer, builds an empty one from scratch
            tempProp = DataProcessing.buildEmptyProperty();
        }

        if (tempProp.getId() == 0) isNewOffer = true;
        return binding.getRoot();
    }

    private void fillFieldsFromTemp() {
        binding.createDescriptionEdit.setText(tempProp.getDescription());
        if (tempProp.getSurface() != -1) binding.createSizeEdit.setText(String.valueOf(tempProp.getSurface()));
        if (tempProp.getPrice() != -1) binding.createPriceEdit.setText(String.valueOf(tempProp.getPrice()));
        if (tempProp.getRooms() != -1) binding.createRoomsEdit.setText(String.valueOf(tempProp.getRooms()));
        if (tempProp.getBedrooms() != -1) binding.createBedsEdit.setText(String.valueOf(tempProp.getBedrooms()));
        if (tempProp.getToilets() != -1) binding.createToiletsEdit.setText(String.valueOf(tempProp.getToilets()));
        if (tempProp.getShowers() != -1) binding.createShowersEdit.setText(String.valueOf(tempProp.getShowers()));
        if (tempProp.getBathtubs() != -1)
            binding.createBathtubsEdit.setText(String.valueOf(tempProp.getBathtubs()));
        if (tempProp.isAircon()) {
            binding.checkBox.setChecked(true);
        }
        binding.createSpinnerType.setSelection(spinnerArray.indexOf(tempProp.getBuildType()));

        String lat = (tempProp.getLocation().split(",", -1))[0];
        String lon = (tempProp.getLocation().split(",", -1))[1];
        String liteMapUrl = "https://maps.google.com/maps/api/staticmap?center=" + lat + "," + lon + "&zoom=12&size=400x200&markers=color:red%7C" + lat + "," + lon + "&sensor=false&key=" + apiKey;
        Glide.with(context)
                .load(liteMapUrl)
                .into(binding.createMapLite);
    }

    private void initMediasObserver() {
        offerMediaViewModel = ViewModelProviders.of(this).get(OfferMediaViewModel.class);
        offerMediaViewModel.getMediasByPropertyId(tempProp.getId()).observe(this, new Observer<List<OfferMedia>>() {
            public void onChanged(@Nullable List<OfferMedia> medias) {
                if (medias.size() > 0) {
                    paths.clear();
                    for (OfferMedia media : medias) {
                        paths.add(context.getFilesDir().getPath() + "/medias/" + media.getFileName());
                    }
                    int index = DataProcessing.getMainPictureIndex(tempProp.getId(), medias);
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
        if (paths.size() > 0) {
            binding.createMediasColumns.setVisibility(View.VISIBLE);
        } else {
            binding.createMediasColumns.setVisibility(View.INVISIBLE);
        }
        myAdapter = new MediasRecyclerViewAdapter(paths);
        binding.createRecycler.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            if (MediaTypesAndCopy.isImage(picturePath) || MediaTypesAndCopy.isVideo(picturePath)) {
                //checks the file format is of an image or video accepted one
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
        if (!binding.createRoomsEdit.getText().toString().equals(""))
            tempProp.setRooms(Integer.parseInt(binding.createRoomsEdit.getText().toString()));
        if (!binding.createBedsEdit.getText().toString().equals(""))
            tempProp.setBedrooms(Integer.parseInt(binding.createBedsEdit.getText().toString()));
        if (!binding.createShowersEdit.getText().toString().equals(""))
            tempProp.setShowers(Integer.parseInt(binding.createShowersEdit.getText().toString()));
        if (!binding.createBathtubsEdit.getText().toString().equals(""))
            tempProp.setBathtubs(Integer.parseInt(binding.createBathtubsEdit.getText().toString()));
        if (!binding.createToiletsEdit.getText().toString().equals(""))
            tempProp.setToilets(Integer.parseInt(binding.createToiletsEdit.getText().toString()));
        if (!binding.createSizeEdit.getText().toString().equals(""))
            tempProp.setSurface(Integer.parseInt(binding.createSizeEdit.getText().toString()));
        if (!binding.createPriceEdit.getText().toString().equals(""))
            tempProp.setPrice(Integer.parseInt(binding.createPriceEdit.getText().toString()));
        tempProp.setDateOffer((new Timestamp(new Date().getTime())).getTime());
        tempProp.setDescription(binding.createDescriptionEdit.getText().toString());
        tempProp.setAircon(binding.checkBox.isChecked());
        tempProp.setBuildType(binding.createSpinnerType.getSelectedItem().toString());
    }

    private void publish() {

        if (!isNewOffer) {
            offerMediaViewModel.deleteAllMediasOfThisProperty(tempProp.getId());
        }
        propertyViewModel.insert(tempProp, id -> copyMedias(id));
    }

    private void copyMedias(long propertyId) {

        String filename;
        int mainMediaIndex = myAdapter.getMainPicture();
        boolean isMain;

        if (paths != null) for (String path : paths) {

            isMain = false;
            if (paths.indexOf(path) == mainMediaIndex) {
                isMain = true;
            }
            File inFile = new File(path);
            filename = inFile.getName();
            File outFile = new File(context.getFilesDir().getPath() + "/medias/" + filename);
            offerMediaViewModel.insert(new OfferMedia(propertyId, filename, isMain));

            InputStream in = null;
            OutputStream out = null;

            if (!inFile.getPath().equals(outFile.getPath())) {
                try {
                    in = new FileInputStream(inFile);
                    out = new FileOutputStream(outFile);

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
