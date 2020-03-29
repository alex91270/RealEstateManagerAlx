package com.example.realestatemanageralx.database;


import androidx.room.Dao;
import androidx.room.Insert;

import com.example.realestatemanageralx.model.Agent;

/**
 * * DAO (data access object) providing SQL queries to interact with the Agent table
 */

@Dao
public interface AgentDao {

    @Insert
    long insertAgent(Agent agent);

}
