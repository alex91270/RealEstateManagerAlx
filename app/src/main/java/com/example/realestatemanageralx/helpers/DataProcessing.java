package com.example.realestatemanageralx.helpers;

import com.example.realestatemanageralx.datas.DataHolder;
import com.example.realestatemanageralx.model.OfferMedia;
import com.example.realestatemanageralx.model.Property;
import java.util.ArrayList;
import java.util.List;

public class DataProcessing {

    /**
     * Takes a list of medias, a Property id and returns the filename of the main media
     * for this Property
     * @param propertyId
     * @param listMedias
     * @return
     */
    public static String getMainPictureName(long propertyId, List<OfferMedia> listMedias) {
        ArrayList<OfferMedia> listMediaThisProperty = new ArrayList<>();
        String fileNameMainMedia = "";

        for (OfferMedia media : listMedias) {
            if (media.getPropertyId() == propertyId) {
                listMediaThisProperty.add(media);
                if (media.getIsMain()) fileNameMainMedia = media.getFileName();
            }
        }

        if (fileNameMainMedia.equals("") & listMediaThisProperty.size() > 0)
            fileNameMainMedia = listMediaThisProperty.get(0).getFileName();

        return fileNameMainMedia;
    }

    /**
     * Takes a list of medias and returns the index of the main one
     *  for this Property
     * @param propertyId
     * @param listMedias
     * @return
     */

    public static int getMainPictureIndex(long propertyId, List<OfferMedia> listMedias) {
        int index = 0;

        for (int i = 0; i < listMedias.size(); i++) {
            OfferMedia media = listMedias.get(i);
            if (media.getPropertyId() == propertyId) {
                if (media.getIsMain()) index = i;
            }
        }
        return index;
    }

    /**
     * Takes the list of all properties and returns the last created one
     * @param offersList
     * @return
     */

    public static Property getLastOffer(List<Property> offersList) {

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

    /**
     * Creates an empty Property from scratch
     * @return
     */

    public static Property buildEmptyProperty() {
        Property tempProp = new Property(
                "",
                "",
                "",
                -1,
                -1,
                -1,
                -1,
                -1,
                false,
                0,
                "",
                -1,
                DataHolder.getInstance().getAgentId(),
                false, "",
                -1,
                "",
                0);

        return tempProp;
    }
}
