package com.example.realestatemanageralx.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.example.realestatemanageralx.database.AppDatabase;
import com.example.realestatemanageralx.database.PropertyDAO;
import com.example.realestatemanageralx.helpers.SearchQueryBuilder;
import com.example.realestatemanageralx.model.Filter;
import com.example.realestatemanageralx.model.Property;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * ViewModel for properties, using the DAO and returning LiveData, observed by the view
 */

public class PropertyViewModel extends AndroidViewModel {
    private PropertyDAO propertyDao;
    private LiveData<List<Property>> propertiesLiveData;
    private LiveData<Property> propertyLiveData;
    private LiveData<Long> lastPropertyId;
    private Executor taskExecutor;

    public PropertyViewModel(@NonNull Application application) {
        super(application);
        propertyDao = AppDatabase.getDatabase(application).propertyDAO();
        taskExecutor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Property>> getPropertiesList() {
        propertiesLiveData = propertyDao.getAllProperties();
        return propertiesLiveData;
    }

    public LiveData<List<Property>> getFilteredPropertiesList(Filter filter) {
        SimpleSQLiteQuery SSQ = new SimpleSQLiteQuery(new SearchQueryBuilder().buildQuery(filter));
        propertiesLiveData = propertyDao.getFilteredProperties(SSQ);
        return propertiesLiveData;
    }

    public LiveData<Property> getPropertyById(long id) {
        propertyLiveData = propertyDao.getPropertyById(id);
        return propertyLiveData;
    }

    public void insert(final Property property, OnPropertyInserted onPropertyInserted) {
        taskExecutor.execute(() -> {
            long id = propertyDao.insertProperty(property);
            onPropertyInserted.doneWriting(id);
        });
    }

    public LiveData<Long> getLastPropertyId() {
        lastPropertyId = propertyDao.getLastInsertedId();
        return lastPropertyId;
    }

    public void setAsSold(final long pId) {
        taskExecutor.execute(() -> propertyDao.setPropertyAsSold(pId));
    }

    public void setAsNotSold(final long pId) {
        taskExecutor.execute(() -> propertyDao.setPropertyAsNotSold(pId));
    }

    public void setSaleDate(final long pId, final long mDate) {
        taskExecutor.execute(() -> propertyDao.setPropertySaleDate(pId, mDate));
    }

    public void deleteProperty(final long pId) {
        taskExecutor.execute(() -> propertyDao.deleteProperty(pId));
    }
}


