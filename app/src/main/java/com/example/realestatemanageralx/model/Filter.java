package com.example.realestatemanageralx.model;

import java.io.Serializable;

/**
 * Object containing all the requests of a research
 */

public class Filter implements Serializable {
    private boolean filterByType;
    private String type;
    private boolean filterByLocation;
    private String location;
    private int minPrice;
    private int maxPrice;
    private int minSurface;
    private int maxSurface;
    private boolean filterByRooms;
    private int minRooms;
    private boolean onSaleChecked;
    private boolean soldChecked;
    private boolean filterByDate;
    private int dateCase;
    private boolean filterByConveniences;
    private boolean filterBySchool;
    private boolean filterByStores;
    private boolean filterByParks;
    private boolean filterByRestaurants;
    private boolean filterBySubways;

    public Filter() {
    }

    public boolean isFilterByType() {
        return filterByType;
    }

    public void setFilterByType(boolean filterByType) {
        this.filterByType = filterByType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isFilterByLocation() {
        return filterByLocation;
    }

    public void setFilterByLocation(boolean filterByLocation) {
        this.filterByLocation = filterByLocation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getMinSurface() {
        return minSurface;
    }

    public void setMinSurface(int minSurface) {
        this.minSurface = minSurface;
    }

    public int getMaxSurface() {
        return maxSurface;
    }

    public void setMaxSurface(int maxSurface) {
        this.maxSurface = maxSurface;
    }

    public boolean isFilterByRooms() {
        return filterByRooms;
    }

    public void setFilterByRooms(boolean filterByRooms) {
        this.filterByRooms = filterByRooms;
    }

    public boolean isOnSaleChecked() {
        return onSaleChecked;
    }

    public void setOnSaleChecked(boolean onSaleChecked) {
        this.onSaleChecked = onSaleChecked;
    }

    public boolean isSoldChecked() {
        return soldChecked;
    }

    public void setSoldChecked(boolean soldChecked) {
        this.soldChecked = soldChecked;
    }

    public boolean isFilterByDate() {
        return filterByDate;
    }

    public void setFilterByDate(boolean filterByDate) {
        this.filterByDate = filterByDate;
    }

    public int getDateCase() {
        return dateCase;
    }

    public void setDateCase(int dateCase) {
        this.dateCase = dateCase;
    }

    public boolean isFilterByConveniences() {
        return filterByConveniences;
    }

    public void setFilterByConveniences(boolean filterByConveniences) {
        this.filterByConveniences = filterByConveniences;
    }

    public boolean isFilterBySchool() {
        return filterBySchool;
    }

    public void setFilterBySchool(boolean filterBySchool) {
        this.filterBySchool = filterBySchool;
    }

    public boolean isFilterByStores() {
        return filterByStores;
    }

    public void setFilterByStores(boolean filterByStores) {
        this.filterByStores = filterByStores;
    }

    public boolean isFilterByParks() {
        return filterByParks;
    }

    public void setFilterByParks(boolean filterByParks) {
        this.filterByParks = filterByParks;
    }

    public boolean isFilterByRestaurants() {
        return filterByRestaurants;
    }

    public void setFilterByRestaurants(boolean filterByRestaurants) {
        this.filterByRestaurants = filterByRestaurants;
    }

    public boolean isFilterBySubways() {
        return filterBySubways;
    }

    public void setFilterBySubways(boolean filterBySubways) {
        this.filterBySubways = filterBySubways;
    }

    public int getMinRooms() {
        return minRooms;
    }

    public void setMinRooms(int minRooms) {
        this.minRooms = minRooms;
    }
}
