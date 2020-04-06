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

/**
 * ViewModel for medias, using the DAO and returning LiveData, observed by the view
 */

public class OfferMediaViewModel  extends AndroidViewModel {
    private OfferMediaDAO offerMediaDAO;
    private LiveData<List<OfferMedia>> allMediasLiveData;
    private List<OfferMedia> mediasList;


    public OfferMediaViewModel (@NonNull Application application) {
        super(application);
        offerMediaDAO = AppDatabase.getDatabase(application).offerMediaDAO();
    }

    public LiveData<List<OfferMedia>> getAllMedias() {
        allMediasLiveData = offerMediaDAO.getAllMedias();
        return allMediasLiveData;
    }

    public List<OfferMedia> getMediasByPropertyId(long id) {
        mediasList = offerMediaDAO.getMediasByPropertyId(id);
        return mediasList;}
}
