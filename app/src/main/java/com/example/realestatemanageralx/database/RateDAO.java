package com.example.realestatemanageralx.database;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.realestatemanageralx.model.Property;
import com.example.realestatemanageralx.model.Rate;

import java.util.List;

/**
 * * DAO (data access object) providing SQL queries to interact with the Rate table
 */

@Dao
public interface RateDAO {

    @Insert
    long insertRate(Rate rate);

    @Query("SELECT * FROM rate")
    LiveData<List<Rate>> getAllRates();

    // for contentProvider
    @Query("SELECT * FROM rate")
    Cursor getRatesWithCursor();
}
