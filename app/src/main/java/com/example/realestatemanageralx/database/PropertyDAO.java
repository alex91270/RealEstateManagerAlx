package com.example.realestatemanageralx.database;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.realestatemanageralx.model.Property;

/**
 * * DAO (data access object) providing SQL queries to interact with the Property table
 */

@Dao
public interface PropertyDAO {

    @Insert
    long insertProperty(Property property);
}
