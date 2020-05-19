package com.example.realestatemanageralx.datas;

import com.example.realestatemanageralx.model.Property;
import java.util.ArrayList;

public class DataHolder {

    private static final DataHolder holder = new DataHolder();
    private boolean isAgentLogged = false;
    private long agentId = 0;
    private ArrayList<Property> searchedPropertiesList;
    private String orientation;
    private String lastNetworkState;

    public static DataHolder getInstance() {
        return holder;
    }

    public void setIsLogged(boolean logged){
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

    public void setSearchedPropertiesList (ArrayList<Property> propList) {
        searchedPropertiesList = propList;
    }

    public ArrayList<Property> getSearchedPropertiesList() {
        return searchedPropertiesList;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getLastNetworkState() {
        return lastNetworkState;
    }

    public void setLastNetworkState(String lastNetworkState) {
        this.lastNetworkState = lastNetworkState;
    }
}
