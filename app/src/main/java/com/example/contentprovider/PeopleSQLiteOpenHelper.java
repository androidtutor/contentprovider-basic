package com.example.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class PeopleSQLiteOpenHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "people";
    static final String TABLE_NAME = "people";
    static final int DATABASE_VERSION = 1;

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_PHONE = "PHONE";
    public static final String COLUMN_ADDRESS = "ADDRESS";

    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_PHONE + " TEXT, " +
                    COLUMN_ADDRESS + " TEXT)";

    PeopleSQLiteOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}



