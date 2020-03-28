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
    private boolean bathtubs;

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

}
