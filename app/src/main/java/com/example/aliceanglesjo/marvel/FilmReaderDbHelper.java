package com.example.aliceanglesjo.marvel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by marcus on 2018-04-25.
 */

public abstract class FilmReaderDbHelper extends SQLiteOpenHelper {
    // TODO: You need to add member variables and methods to this helper class
    // See: https://developer.android.com/training/data-storage/sqlite.html#DbHelper
    FilmReaderDbHelper(Context c){
        super (c,"mountainsdb", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FilmReaderContract.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(FilmReaderContract.SQL_DELETE_ENTRIES);
        onCreate(db);

    }
}
