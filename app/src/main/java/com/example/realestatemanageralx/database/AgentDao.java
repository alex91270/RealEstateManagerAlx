package com.example.realestatemanageralx.database;


import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.realestatemanageralx.model.Agent;
import com.example.realestatemanageralx.model.Property;

import java.util.List;

/**
 * * DAO (data access object) providing SQL queries to interact with the Agent table
 */

@Dao
public interface AgentDao {

    @Insert
    long insertAgent(Agent agent);

    @Query("SELECT * FROM agent WHERE aid = :agentId")
    LiveData<Agent> getAgentById(long agentId);

    @Query("SELECT * FROM agent WHERE username = :agentUsername")
    LiveData<Agent> getAgentByUsername(String agentUsername);


    // for contentProvider
    @Query("SELECT * FROM agent WHERE aid = :agentId")
    Cursor getAgentByIdWithCursor(long agentId);

}
