package com.example.realestatemanageralx.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "property",
        foreignKeys = {@ForeignKey(entity = Agent.class,
        parentColumns = "aid",
        childColumns = "agentId",
        onDelete = ForeignKey.CASCADE)},
        indices = {
                @Index(name = "agentId_index", value = {"agentId"})
        })
public class Property {

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

    public String getCity() {
        return city;
    }

    public int getRooms() { return rooms;}

    public int getBathtubs() {
        return bathtubs;
    }

    public long getDateOffer() {
        return dateOffer;
    }

    public long getDateSale() { return dateSale; }

    public int getBedrooms() {
        return bedrooms;
    }

    public int getShowers() {
        return showers;
    }

    public int getSurface() {
        return surface;
    }

    public int getToilets() {
        return toilets;
    }

    public String getDescription() {
        return description;
    }

    public String getPois() { return pois;}

    public String getDistrict() {
        return district;
    }

    public int getPrice() {
        return price;
    }

    public long getAgentId() {
        return agentId;
    }

    public String getLocation() {
        return location;
    }

    public boolean isAircon() {
        return aircon;
    }

    public boolean isSold() {
        return sold;
    }

}
