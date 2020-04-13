package com.example.realestatemanageralx.data;


import com.example.realestatemanageralx.model.Agent;
import com.example.realestatemanageralx.model.OfferMedia;
import com.example.realestatemanageralx.model.Property;
import com.example.realestatemanageralx.model.Rate;

import java.util.ArrayList;

public class DataHolder {

    private static final DataHolder holder = new DataHolder();

    private boolean isAgentLoggued;
    private long agentId;

    public static DataHolder getInstance() {
        return holder;
    }

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
