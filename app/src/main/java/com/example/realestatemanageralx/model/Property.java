package com.example.realestatemanageralx.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "property",
        foreignKeys = {@ForeignKey(entity = Agent.class,
        parentColumns = "aid",
        childColumns = "agentId",
        onDelete = ForeignKey.CASCADE)},
        indices = {
                @Index(name = "agentId_index", value = {"agentId"})
        })
public class Property implements Serializable {

    /**
     * The unique identifier of the project
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pid")
    private long id;

    @ColumnInfo(name="buildType")
    private String buildType;

    @ColumnInfo(name="description")
    private String description;

    @ColumnInfo(name="pois")
    private String pois;

    @ColumnInfo(name="city")
    private String city;

    @ColumnInfo(name="district")
    private String district;

    @ColumnInfo(name="surface")
    private int surface;

    @ColumnInfo(name="rooms")
    private int rooms;

    @ColumnInfo(name="bedrooms")
    private int bedrooms;

    @ColumnInfo(name="toilets")
    private int toilets;

    @ColumnInfo(name="showers")
    private int showers;

    @ColumnInfo(name="bathtubs")
    private int bathtubs;

    @ColumnInfo(name="aircon")
    private boolean aircon;

    @ColumnInfo(name="dateOffer")
    private long dateOffer;

    @ColumnInfo(name="dateSale")
    private long dateSale;

    @ColumnInfo(name="sold")
    private boolean sold;

    @ColumnInfo(name="location")
    private String location;

    @ColumnInfo(name="price")
    private int price;

    @ColumnInfo(name="agentId")
    private long agentId;

    public Property(String description, String city, String district, int surface, int bedrooms,
                    int toilets, int showers, int bathtubs, boolean aircon, long dateOffer,
                    String location, int price, long agentId, boolean sold, String pois, int rooms, String buildType, long dateSale) {
        this.description = description;
        this.city = city;
        this.district = district;
        this.surface = surface;
        this.bedrooms = bedrooms;
        this.showers = showers;
        this.toilets = toilets;
        this.bathtubs = bathtubs;
        this.aircon = aircon;
        this.dateOffer = dateOffer;
        this.sold = false;
        this.location = location;
        this.price = price;
        this.agentId = agentId;
        this.sold = sold;
        this.pois = pois;
        this.rooms = rooms;
        this.buildType = buildType;
        this.dateSale = dateSale;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getBuildType() {return buildType;}

    public void setBuildType(String type) {buildType = type;}

    public String getCity() {
        return city;
    }

    public void setCity(String city){this.city = city;}

    public int getRooms() { return rooms;}

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public int getBathtubs() {
        return bathtubs;
    }

    public void setBathtubs(int bathtubs) {
        this.bathtubs = bathtubs;
    }

    public long getDateOffer() {
        return dateOffer;
    }

    public void setDateOffer(long dateOffer) {
        this.dateOffer = dateOffer;
    }

    public long getDateSale() { return dateSale; }

    public int getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public int getShowers() {
        return showers;
    }

    public void setShowers(int showers) {
        this.showers = showers;
    }

    public int getSurface() {
        return surface;
    }

    public void setSurface(int surface) {
        this.surface = surface;
    }

    public int getToilets() {
        return toilets;
    }

    public void setToilets(int toilets) {
        this.toilets = toilets;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPois() { return pois;}

    public void setPois(String pois) {
        this.pois = pois;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public long getAgentId() {
        return agentId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isAircon() {
        return aircon;
    }

    public void setAircon(boolean aircon) {
        this.aircon = aircon;
    }

    public boolean isSold() {
        return sold;
    }

}
