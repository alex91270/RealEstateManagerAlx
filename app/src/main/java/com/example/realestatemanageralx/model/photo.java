package com.example.realestatemanageralx.model;

import android.net.Uri;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


public class photo {
    private int id;
    private int propertyId;
    private Uri fileUri;
    private Boolean isMain;
}
