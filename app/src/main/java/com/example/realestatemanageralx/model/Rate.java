package com.example.realestatemanageralx.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Table storing the exchange rate USD/EUR,
 * the loans interest rates from 1 to 30 years,
 * and the dates when those where last updated, as timestamps.
 */
@Entity(tableName = "Rate")
public class Rate {

    /**
     * The unique identifier of the rate
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rId")
    private int rId;

    /**
     * The name of the value stored
     */
    @ColumnInfo(name="dataType")
    @NonNull
    private String dataType;

    /**
     * The value stored
     */
    @ColumnInfo(name="value")
    @NonNull
    private double value;
}
