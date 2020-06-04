package com.example.realestatemanageralx.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.realestatemanageralx.database.AppDatabase;
import com.example.realestatemanageralx.model.Property;

/**
 * ContentProvider for the Property table
 */

public class PropertyContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.example.realestatemanageralx.providers.property";
    public static final String TABLE_NAME = Property.class.getSimpleName();
    public static final Uri PROPERTY_URI_ITEM = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

    @Override
    public boolean onCreate() {
        Log.i("alex", "uri property: " + PROPERTY_URI_ITEM);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        if (getContext() != null) {
            final Cursor cursor = AppDatabase.getDatabase(getContext()).propertyDAO().getPropertiesWithCursor();
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
        }

        throw new IllegalArgumentException("Failed to query row for uri " + uri);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "vnd.android.cursor.item/" + AUTHORITY + "." + TABLE_NAME;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
