package com.example.realestatemanageralx.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

@Entity(tableName = "property",
        foreignKeys = {@ForeignKey(entity = Agent.class,
        parentColumns = "aId",
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
    @ColumnInfo(name = "pId")
    private long pId;

    @ColumnInfo(name="description")
    private String description;

    @ColumnInfo(name="city")
    private String city;

    @ColumnInfo(name="district")
    private String district;

    @ColumnInfo(name="surface")
    private int surface;

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
    private Date dateOffer;

    @ColumnInfo(name="sold")
    private boolean sold;

    @ColumnInfo(name="location")
    private LatLng location;

    @ColumnInfo(name="price")
    private int price;

    @ColumnInfo(name="agentId")
    private long agentId;

    public Property(String description, String city, String district, int surface, int bedrooms,
                    int toilets, int showers, int bathtubs, boolean aircon, Date dateOffer,
                    LatLng location, int price, long agentId) {
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
    }

    public long getpId() {
        return pId;
    }

    public String getCity() {
        return city;
    }

    public int getBathtubs() {
        return bathtubs;
    }

    public Date getDateOffer() {
        return dateOffer;
    }

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

    public String getDistrict() {
        return district;
    }

    public int getPrice() {
        return price;
    }

    public long getAgentId() {
        return agentId;
    }

    public LatLng getLocation() {
        return location;
    }
}
