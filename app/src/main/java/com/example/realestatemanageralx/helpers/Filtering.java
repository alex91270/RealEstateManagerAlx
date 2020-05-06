package com.example.realestatemanageralx.helpers;

import com.example.realestatemanageralx.model.Filter;
import com.example.realestatemanageralx.model.Property;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Filtering {

    public ArrayList<Property> filterPropertiesList(ArrayList<Property> givenList, Filter filter) {
        ArrayList<Property> filteredPropertiesList = new ArrayList<>();

        for (Property prop : givenList) {
            filteredPropertiesList.add(prop);
        }

        for (Property prop : givenList) {
            //filter type
            if (filter.isFilterByType()) {
                if (!prop.getBuildType().equals(filter.getType())) {
                    filteredPropertiesList.remove(prop);
                }
            }

            //filter location
            if (filter.isFilterByLocation()) {
                if (!prop.getCity().equals(filter.getLocation())
                        && !prop.getDistrict().equals(filter.getLocation())) {
                    filteredPropertiesList.remove(prop);
                }
            }

            //filter price
            if (prop.getPrice() < filter.getMinPrice() || prop.getPrice() > filter.getMaxPrice()) {
                filteredPropertiesList.remove(prop);
            }

            //filter surface
            if (prop.getSurface() < filter.getMinSurface() || prop.getSurface() > filter.getMaxSurface()) {
                filteredPropertiesList.remove(prop);
            }

            //filter rooms
            if (filter.isFilterByRooms()) {
                if (prop.getRooms() < filter.getMinRooms()) filteredPropertiesList.remove(prop);
            }

            //filter status sold
            if (filter.isOnSaleChecked()) {
                if (prop.isSold()) filteredPropertiesList.remove(prop);
            }
            if (filter.isSoldChecked()) {
                if (!prop.isSold()) filteredPropertiesList.remove(prop);
            }

            //filter date
            if (filter.isFilterByDate()) {
                long currentTs = (new Timestamp(new Date().getTime())).getTime();
                switch (filter.getDateCase()) {
                    case 1:
                        if (currentTs - prop.getDateOffer() > 604800000)
                            filteredPropertiesList.remove(prop);
                    case 2:
                        if (currentTs - prop.getDateOffer() > 2678400000L)
                            filteredPropertiesList.remove(prop);
                    case 3:
                        if (currentTs - prop.getDateOffer() > 31622400000L)
                            filteredPropertiesList.remove(prop);

                }
            }

            //filter conveniences
            if (filter.isFilterByConveniences()) {
                List<String> pois = Arrays.asList(prop.getPois().split(","));
                if (filter.isFilterBySchool()) {
                    if (pois.get(0) == "0") filteredPropertiesList.remove(prop);
                }
                if (filter.isFilterByStores()) {
                    if (pois.get(1) == "0") filteredPropertiesList.remove(prop);
                }
                if (filter.isFilterByParks()) {
                    if (pois.get(2) == "0") filteredPropertiesList.remove(prop);
                }
                if (filter.isFilterByRestaurants()) {
                    if (pois.get(3) == "0") filteredPropertiesList.remove(prop);
                }
                if (filter.isFilterBySubways()) {
                    if (pois.get(4) == "0") filteredPropertiesList.remove(prop);
                }
            }


        }
        return filteredPropertiesList;
    }
}