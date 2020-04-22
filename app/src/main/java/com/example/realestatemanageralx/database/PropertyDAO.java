package com.example.realestatemanageralx.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.realestatemanageralx.model.Property;

import java.util.List;

/**
 * * DAO (data access object) providing SQL queries to interact with the Property table
 */

@Dao
public interface PropertyDAO {

    @Insert
    long insertProperty(Property property);

    @Query("SELECT * FROM property")
    LiveData<List<Property>> getAllProperties();

    @Query("SELECT * FROM property WHERE pid = :propertyId")
    LiveData<Property> getPropertyById(long propertyId);

    @Query("SELECT rowid from property order by dateOffer DESC limit 1")
    LiveData<Long> getLastInsertedId();

    @Query("UPDATE property SET sold = 1 WHERE pid = :propertyId")
    void setPropertyAsSold(long propertyId);
}
