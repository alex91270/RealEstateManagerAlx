package com.example.realestatemanageralx.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.RoomDatabase;


public abstract class AppDatabase extends RoomDatabase {

}





/**
 * Class representing the database, with its tables
 * and providing the DAO's used to query the DB
 */

/**

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    //As the data can be accessed by many threads asynchronously, volatile guaranties the value accessed
    //is always gonna be the most up to date one.
    private static volatile AppDatabase INSTANCE;

    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();
    private static final String DB_NAME = "tasks.db";

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
                            .build();
        }
        return INSTANCE;
    }

}
 */
