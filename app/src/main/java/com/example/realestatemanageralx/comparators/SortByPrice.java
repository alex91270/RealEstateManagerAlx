package com.example.realestatemanageralx.comparators;

import com.example.realestatemanageralx.model.Property;

import java.util.Comparator;

public class SortByPrice  implements Comparator<Property> {

    /**
     *
     * @param p1 offer to compare
     * @param p2 offer to compare
     * @return number describing the order between the 2
     */
    @Override
    public int compare(Property p1, Property p2) {

        return (int) (p2.getPrice() - p1.getPrice());
    }
}
