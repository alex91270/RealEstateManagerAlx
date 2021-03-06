package com.example.realestatemanageralx.datas;

import com.example.realestatemanageralx.model.Property;
import java.util.ArrayList;

/**
 * - Holds values for if a real estate agent is logged or not
 * - Holds the list of properties returned by the last research
 * - Holds the last connection type and state
 */

public class DataHolder {

    private static final DataHolder holder = new DataHolder();
    private boolean isAgentLogged = false;
    private long agentId = 0;
    private ArrayList<Property> searchedPropertiesList;
    private Boolean isConnected;
    private String lastConnectionType;

    public static DataHolder getInstance() {
        return holder;
    }

    public void setIsLogged(boolean logged) {
        isAgentLogged = logged;
    }

    public boolean getIsAgentLogged() {
        return isAgentLogged;
    }

    public void setAgentId(long id) {
        agentId = id;
    }

    public long getAgentId() {
        return agentId;
    }

    public void setSearchedPropertiesList(ArrayList<Property> propList) {
        searchedPropertiesList = propList;
    }

    public ArrayList<Property> getSearchedPropertiesList() {
        return searchedPropertiesList;
    }

    public boolean getLastConnectionState() {
        return isConnected;
    }

    public void setLastConnectionState(boolean connected) {
        this.isConnected = connected;
    }

    public String getLastConnectionType() {
        return lastConnectionType;
    }

    public void setLastConnectionType(String lastConnectionType) {
        this.lastConnectionType = lastConnectionType;
    }
}
