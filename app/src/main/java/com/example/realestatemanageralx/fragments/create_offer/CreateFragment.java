package com.example.realestatemanageralx.fragments.create_offer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.realestatemanageralx.R;
import com.example.realestatemanageralx.helpers.MediaTypesAndCopy;

import java.util.ArrayList;
import static android.app.Activity.RESULT_OK;

public class CreateFragment extends Fragment {

    private static int RESULT_LOAD_IMAGE = 1;

    private EditText editCity;
    private EditText editDistrict;
    private EditText editSize;
    private EditText editPrice;
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



    public static CreateFragment newInstance() {
        return (new CreateFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create, container, false);


        editCity = root.findViewById(R.id.create_city_edit);
        editDistrict = root.findViewById(R.id.create_district_edit);
        editSize= root.findViewById(R.id.create_size_edit);
        editPrice = root.findViewById(R.id.create_price_edit);
        editToilets = root.findViewById(R.id.create_toilets_edit);
        editShowers = root.findViewById(R.id.create_showers_edit);
        editBathtubs = root.findViewById(R.id.create_bathtubs_edit);
        checkBox = root.findViewById(R.id.checkBox);
        recyclerView = root.findViewById(R.id.create_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        buttonPick = root.findViewById(R.id.button_pick);

        buttonPick.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        myAdapter = new MediasRecyclerViewAdapter(paths);
        recyclerView.setAdapter(myAdapter);
        return root;
    }

    private void updateRecycler() {
        Log.i("alex", "updateRecycler; list size: " + paths.size());
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
                Toast.makeText(getContext(), "File type not supported !", Toast.LENGTH_LONG).show();
            }


        }
    }
}
