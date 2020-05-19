package com.example.realestatemanageralx.database;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.realestatemanageralx.model.Agent;
import com.example.realestatemanageralx.model.OfferMedia;
import com.example.realestatemanageralx.model.Property;
import com.example.realestatemanageralx.model.Rate;

/**
 * Class representing the database, with its tables
 * and providing the DAO's used to query the DB
 */

@Database(entities = {Agent.class, OfferMedia.class, Property.class, Rate.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    //As the data can be accessed by many threads asynchronously, volatile guaranties the value accessed
    //is always gonna be the most up to date one.
    private static volatile AppDatabase INSTANCE;

    public abstract AgentDao agentDAO();
    public abstract PropertyDAO propertyDAO();
    public abstract OfferMediaDAO offerMediaDAO();
    public abstract RateDAO rateDAO();

    private static final String DB_NAME = "real.db";

    public static AppDatabase getDatabase(Context context) {

        if (INSTANCE == null) {

            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    DatabaseInitialize.populateAsync(INSTANCE);
                                }
                            })
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

}

