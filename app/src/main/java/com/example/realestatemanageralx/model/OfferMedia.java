package com.example.realestatemanageralx.model;

import android.net.Uri;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "offerMedia",
        foreignKeys = {@ForeignKey(entity = Property.class,
                parentColumns = "pId",
                childColumns = "propertyId",
                onDelete = ForeignKey.CASCADE)},
        indices = {
                @Index(name = "propertyId_index", value = {"propertyId"})
        })
public class OfferMedia {

    /**
     * The unique identifier of the media
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "mId")
    private long mId;

    /**
     * The id of the property concerned
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pid")
    private long propertyId;
    private Uri fileUri;
    private boolean isMain;
}
