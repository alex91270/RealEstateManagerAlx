package com.example.realestatemanageralx.database;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.realestatemanageralx.model.OfferMedia;

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
    LiveData<List<OfferMedia>> getMediasByPropertyId(long propertyId);

    @Query("DELETE FROM offerMedia WHERE propertyId = :propertyId")
    void deleteAllMediasByPropertyId(long propertyId);

    // for contentProvider
    @Query("SELECT * FROM offerMedia WHERE propertyId = :propertyId")
    Cursor getMediasWithCursor(long propertyId);
}
