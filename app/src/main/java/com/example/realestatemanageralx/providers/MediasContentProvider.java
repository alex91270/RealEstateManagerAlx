package com.example.realestatemanageralx.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.realestatemanageralx.database.AppDatabase;
import com.example.realestatemanageralx.model.OfferMedia;

/**
 * ContentProvider for the OfferMedia table
 */

public class MediasContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.example.realestatemanageralx.providers.media";
    public static final String TABLE_NAME = OfferMedia.class.getSimpleName();
    public static final Uri MEDIA_URI_ITEM = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        if (getContext() != null) {
            long propertyId = ContentUris.parseId(uri);
            final Cursor cursor = AppDatabase.getDatabase(getContext()).offerMediaDAO().getMediasWithCursor(propertyId);
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
