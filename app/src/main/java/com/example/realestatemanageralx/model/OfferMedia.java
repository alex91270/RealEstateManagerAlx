package com.example.realestatemanageralx.model;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "offerMedia",
        foreignKeys = {@ForeignKey(entity = Property.class,
                parentColumns = "pid",
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
    @ColumnInfo(name = "mid")
    private long id;

    /**
     * The id of the property concerned
     */
    @ColumnInfo(name = "propertyId")
    private long propertyId;

    /**
     * The Uri of the media file
     */
    @ColumnInfo(name="fileUri")
    @NonNull
    private String fileUri;

    /**
     * whether or not this media is the main one
     */
    @ColumnInfo(name="isMain")
    @NonNull
    private boolean isMain;

    public OfferMedia(long propertyId, String fileUri, boolean isMain) {
        this.propertyId = propertyId;
        this.fileUri = fileUri;
        this.isMain = isMain;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getPropertyId() {
        return propertyId;
    }

    @NonNull
    public String getFileUri() {
        return fileUri;
    }

    public boolean getIsMain() {
        return isMain;
    }
}

