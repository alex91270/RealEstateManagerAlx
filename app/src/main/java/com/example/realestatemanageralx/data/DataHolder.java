package com.example.realestatemanageralx.data;


import com.example.realestatemanageralx.model.Agent;
import com.example.realestatemanageralx.model.OfferMedia;
import com.example.realestatemanageralx.model.Property;
import com.example.realestatemanageralx.model.Rate;

import java.util.ArrayList;

public class DataHolder {

    private static final DataHolder holder = new DataHolder();
   //private ArrayList<Property> propertiesList;
    //private ArrayList<OfferMedia> offerMediaList;
    //private ArrayList<Agent> agentList;
    //private ArrayList<Rate> rateList;
    //private String data;

    private boolean isAgentLoggued;
    private long agentId;

    public static DataHolder getInstance() {
        return holder;
    }

    /**public ArrayList<Property> getPropertiesList() {
        return propertiesList;
    }
    public void setPropertiesList(ArrayList<Property> list) {
        this.propertiesList = list;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }*/

    public void setIsLoggued(boolean loggued){
        isAgentLoggued = loggued;
    }

    public boolean getIsAgentLoggued() {
        return isAgentLoggued;
    }

    public void setAgentId(long id) {
        agentId = id;
    }

    public long getAgentId() {
        return agentId;
    }

}
