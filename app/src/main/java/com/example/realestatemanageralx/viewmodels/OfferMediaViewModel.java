package com.example.realestatemanageralx.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.realestatemanageralx.database.AppDatabase;
import com.example.realestatemanageralx.database.OfferMediaDAO;
import com.example.realestatemanageralx.database.PropertyDAO;
import com.example.realestatemanageralx.model.OfferMedia;
import com.example.realestatemanageralx.model.Property;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * ViewModel for medias, using the DAO and returning LiveData, observed by the view
 */

public class OfferMediaViewModel  extends AndroidViewModel {
    private OfferMediaDAO offerMediaDAO;
    private LiveData<List<OfferMedia>> allMediasLiveData;
    private LiveData<List<OfferMedia>> mediasList;
    private Executor taskExecutor;


    public OfferMediaViewModel (@NonNull Application application) {
        super(application);
        offerMediaDAO = AppDatabase.getDatabase(application).offerMediaDAO();
        taskExecutor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<OfferMedia>> getAllMedias() {
        allMediasLiveData = offerMediaDAO.getAllMedias();
        return allMediasLiveData;
    }

    public LiveData<List<OfferMedia>> getMediasByPropertyId(long id) {
        mediasList = offerMediaDAO.getMediasByPropertyId(id);
        return mediasList;
    }

    public void insert(final OfferMedia media) {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                offerMediaDAO.insertOfferMedia(media);
            }
        });
    }

    public void deleteAllMediasOfThisProperty(long id) {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                offerMediaDAO.deleteAllMediasByPropertyId(id);
            }
        });
    }


}
