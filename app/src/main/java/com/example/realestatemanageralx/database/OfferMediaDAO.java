package com.example.realestatemanageralx.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.realestatemanageralx.model.OfferMedia;
import com.example.realestatemanageralx.model.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * * DAO (data access object) providing SQL queries to interact with the OfferMedia table
 */

@Dao
public interface OfferMediaDAO {

    @Insert
    long insertOfferMedia(OfferMedia offerMedia);

    @Query("SELECT * FROM offerMedia")
        LiveData<List<OfferMedia>> getAllMedias();

    @Query("SELECT * FROM offerMedia WHERE propertyId = :propertyId")
    //LiveData<Property> getMediaByPropertyId(long propertyId);
    List<OfferMedia> getMediasByPropertyId(long propertyId);
}
