
package com.example.realestatemanageralx.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.realestatemanageralx.database.AgentDao;
import com.example.realestatemanageralx.database.AppDatabase;
import com.example.realestatemanageralx.database.PropertyDAO;
import com.example.realestatemanageralx.database.RateDAO;
import com.example.realestatemanageralx.model.Agent;
import com.example.realestatemanageralx.model.Property;
import com.example.realestatemanageralx.model.Rate;

import java.util.List;

/**
 * ViewModel for agents, using the DAO and returning LiveData, observed by the view
 */

public class RateViewModel  extends AndroidViewModel {
    private RateDAO rateDAO;
    private LiveData<List<Rate>> rateLiveData;

    public RateViewModel (@NonNull Application application) {
        super(application);
        rateDAO = AppDatabase.getDatabase(application).rateDAO();
    }

    public LiveData<List<Rate>> getRates() {
        rateLiveData= rateDAO.getAllRates();
        return rateLiveData;
    }
}




