package com.example.realestatemanageralx.helpers;

import com.example.realestatemanageralx.model.Filter;

import java.sql.Timestamp;
import java.util.Date;

public class SearchQueryBuilder {

    /**
     * Builds the String SQL query from an object Filter
     * @param filter
     * @return
     */

    public String buildQuery(Filter filter) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM property WHERE");

        //filter price
        sb.append(" price BETWEEN " + filter.getMinPrice() + " AND " + filter.getMaxPrice());

        //filter surface
        sb.append(" AND surface BETWEEN " + filter.getMinSurface() + " AND " + filter.getMaxSurface());

        //filter type
        if (filter.isFilterByType()) {
            sb.append(" AND buildType = '" + filter.getType() + "'");
        }

        //filter location
        if (filter.isFilterByLocation()) {
            sb.append(" AND  city = '" + filter.getLocation() + "' OR district = '" + filter.getLocation() + "'");
        }

        //filter rooms
        if (filter.isFilterByRooms()) {
            sb.append(" AND rooms >= " + filter.getMinRooms());
        }

        //filter status sold
        if (filter.isOnSaleChecked()) {
            sb.append(" AND sold = 0");
        }
        if (filter.isSoldChecked()) {
            sb.append(" AND sold = 1");
        }

        //filter date
        if (filter.isFilterByDate()) {
            long currentTs = (new Timestamp(new Date().getTime())).getTime();
            long difference = 0;
            switch (filter.getDateCase()) {
                case 1:
                    difference = currentTs - 604800000;
                    break;
                case 2:
                    difference = currentTs - 2678400000L;
                    break;
                case 3:
                    difference = currentTs - 31622400000L;
                    break;
            }
            sb.append(" AND dateOffer < " + String.valueOf(difference));
        }

        //filter conveniences
        if (filter.isFilterByConveniences()) {
            if (filter.isFilterBySchool()) {
                sb.append(" AND substr(pois, 1, 1) != '0'");
            }
            if (filter.isFilterByStores()) {
                sb.append(" AND substr(pois, 3, 1) != '0'");
            }
            if (filter.isFilterByParks()) {
                sb.append(" AND substr(pois, 5, 1) != '0'");
            }
            if (filter.isFilterByRestaurants()) {
                sb.append(" AND substr(pois, 7, 1) != '0'");
            }
            if (filter.isFilterBySubways()) {
                sb.append(" AND substr(pois, 9, 1) != '0'");
            }
        }

        return sb.toString();

    }
}
