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

    /**
     * The Uri of the media file
     */
    @ColumnInfo(name="fileUri")
    @NonNull
    private Uri fileUri;

    /**
     * whether or not this media is the main one
     */
    @ColumnInfo(name="isMain")
    @NonNull
    private boolean isMain;

    public OfferMedia(long propertyId, Uri fileUri, boolean isMain) {
        this.propertyId = propertyId;
        this.fileUri = fileUri;
        this.isMain = isMain;
    }

    public long getmId() {
        return mId;
    }

    public long getPropertyId() {
        return propertyId;
    }

    @NonNull
    public Uri getFileUri() {
        return fileUri;
    }

    public boolean getIsMain() {
        return isMain;
    }
}

