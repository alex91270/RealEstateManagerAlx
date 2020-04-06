package com.example.realestatemanageralx.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.realestatemanageralx.database.AppDatabase;
import com.example.realestatemanageralx.database.PropertyDAO;
import com.example.realestatemanageralx.model.Property;

import java.util.List;

/**
 * ViewModel for properties, using the DAO and returning LiveData, observed by the view
 */

public class PropertyViewModel  extends AndroidViewModel {
    private PropertyDAO propertyDao;
    private LiveData<List<Property>> propertiesLiveData;
    private LiveData<Property> propertyLiveData;

    public PropertyViewModel (@NonNull Application application) {
        super(application);
        propertyDao = AppDatabase.getDatabase(application).propertyDAO();
    }

    public LiveData<List<Property>> getProjectsList() {
        propertiesLiveData = propertyDao.getAllProperties();
        return propertiesLiveData;
    }

    public LiveData<Property> getProjectById(long id) {
        propertyLiveData = propertyDao.getPropertyById(id);
        return propertyLiveData;}
}


