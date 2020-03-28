package com.example.realestatemanageralx.database;

import android.os.AsyncTask;

/**
 * Called if the database did not exist yet, to initialise it with the 3 projects
 */



public class DatabaseInitialize {


    public static void populateAsync(final AppDatabase db) {

        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateProjectsIfNewDb(mDb);
            return null;
        }
    }

    private static void populateProjectsIfNewDb(AppDatabase db) {
        /**
        db.projectDao().insertProject(new Project("Projet Tartampion", 0xFFEADAD1));
        db.projectDao().insertProject(new Project( "Projet Lucidia", 0xFFB4CDBA));
        db.projectDao().insertProject(new Project( "Projet Circus", 0xFFA3CED2));
         */
    }

}



