package com.example.aliceanglesjo.marvel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by marcus on 2018-04-25.
 */

public abstract class MountainReaderDbHelper extends SQLiteOpenHelper {
    // TODO: You need to add member variables and methods to this helper class
    // See: https://developer.android.com/training/data-storage/sqlite.html#DbHelper
    MountainReaderDbHelper(Context c){
        super (c,"mountainsdb", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MountainReaderContract.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MountainReaderContract.SQL_DELETE_ENTRIES);
        onCreate(db);

    }
}
