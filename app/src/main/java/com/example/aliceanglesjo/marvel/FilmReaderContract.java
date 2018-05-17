package com.example.aliceanglesjo.marvel;

import android.provider.BaseColumns;

/**
 * Created by marcus on 2018-04-25.
 */

public class FilmReaderContract {
    // This class should contain your database schema.
    // See: https://developer.android.com/training/data-storage/sqlite.html#DefineContract

    private FilmReaderContract() {}

    // Inner class that defines the Film table contents
    public static class MountainEntry implements BaseColumns {
        // TODO:
        public static final String TABLE_NAME = "Movies";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_ID = "ID";
        public static final String COLUMN_NAME_YEAR = "year";
        public static final String COLUMN_NAME_RUNTIME = "runtime";
        public static final String COLUMN_NAME_DIRECTOR = "director";
        public static final String COLUMN_NAME_STORY = "storyline";
        public static final String COLUMN_NAME_IMG_URL = "img_url";
        public static final String COLUMN_NAME_IMDB = "imdb";

    }

    public static final String SQL_CREATE  =
            "CREATE TABLE " + MountainEntry.TABLE_NAME + " (" +
                    MountainEntry._ID + "INTEGER PRIMARY KEY, " +
                    MountainEntry.COLUMN_NAME_NAME + " TEXT NOT NULL UNIQUE," +
                    MountainEntry.COLUMN_NAME_DIRECTOR + " TEXT," +
                    MountainEntry.COLUMN_NAME_YEAR  + " TEXT," +
                    MountainEntry.COLUMN_NAME_ID + " TEXT," +
                    MountainEntry.COLUMN_NAME_IMDB + " TEXT," +
                    MountainEntry.COLUMN_NAME_STORY + " TEXT," +
                    MountainEntry.COLUMN_NAME_RUNTIME + " TEXT," +
                    MountainEntry.COLUMN_NAME_IMG_URL + " TEXT," +
                    MountainEntry.COLUMN_NAME_STATUS + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MountainEntry.TABLE_NAME;
}



