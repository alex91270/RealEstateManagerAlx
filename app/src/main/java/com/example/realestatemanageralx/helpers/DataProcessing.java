package com.example.realestatemanageralx.helpers;

import com.example.realestatemanageralx.model.OfferMedia;
import com.example.realestatemanageralx.model.Property;

import java.util.ArrayList;
import java.util.List;

public class DataProcessing {

    public String getMainPictureName(long propertyId, List<OfferMedia> listMedias ) {
        ArrayList<OfferMedia> listMediaThisProperty = new ArrayList<>();
        String fileNameMainMedia = "";

        for (OfferMedia media : listMedias) {
            if(media.getPropertyId() == propertyId) {
                listMediaThisProperty.add(media);
                if (media.getIsMain()) fileNameMainMedia = media.getFileName();
            }
        }

        if (fileNameMainMedia.equals("") & listMediaThisProperty.size() > 0) fileNameMainMedia = listMediaThisProperty.get(0).getFileName();

        return fileNameMainMedia;
    }

    public Property getLastOffer(List<Property> offersList) {

        Property lastOffer = null;
        double timestamp = 0;

        for (Property prop : offersList) {
            if (prop.getDateOffer() > timestamp) {
                lastOffer = prop;
                timestamp = prop.getDateOffer();
            }
        }
        return lastOffer;
    }

    public Property getPropertyById(List<Property> offersList, long id) {
        Property myProperty = null;

        for (Property prop : offersList) {
            if (prop.getId() == id) {
                myProperty = prop;
            }
        }
        return myProperty;
    }
}
