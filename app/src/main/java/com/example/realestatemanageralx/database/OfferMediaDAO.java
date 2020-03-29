package com.example.realestatemanageralx.database;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.realestatemanageralx.model.OfferMedia;

/**
 * * DAO (data access object) providing SQL queries to interact with the OfferMedia table
 */

@Dao
public interface OfferMediaDAO {

    @Insert
    long insertOfferMedia(OfferMedia offerMedia);
}
