package com.example.realestatemanageralx.database;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.realestatemanageralx.model.Rate;

/**
 * * DAO (data access object) providing SQL queries to interact with the Rate table
 */

@Dao
public interface RateDAO {

    @Insert
    long insertRate(Rate rate);
}
