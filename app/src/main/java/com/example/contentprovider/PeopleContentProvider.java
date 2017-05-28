package com.example.contentprovider;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

public class PeopleContentProvider extends ContentProvider {

    private PeopleSQLiteOpenHelper sqLiteOpenHelper;
    public static final String TABLE_NAME = "people";
    public static final String AUTHORITY = "com.example.contentprovider.provider";
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);
    public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, TABLE_NAME);

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_PHONE = "PHONE";
    public static final String COLUMN_ADDRESS = "ADDRESS";

    public static final int PEOPLE = 1;

    private SQLiteDatabase mDb;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY, TABLE_NAME, PEOPLE);
    }
    @Override
    public boolean onCreate() {
        sqLiteOpenHelper = new PeopleSQLiteOpenHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        String tableNme = "";
        switch(uriMatcher.match(uri)){
            case PEOPLE:
                tableNme = TABLE_NAME;
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }

        mDb = sqLiteOpenHelper.getWritableDatabase();
        Cursor cursor = (SQLiteCursor) mDb.query(tableNme, projection, selection, selectionArgs,
                null, null, sortOrder);

        if (sortOrder == null || sortOrder == "") {
            sortOrder = COLUMN_NAME;
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long rowId = 0;
        mDb = sqLiteOpenHelper.getWritableDatabase();
        switch(uriMatcher.match(uri)){
            case PEOPLE:
                rowId = mDb.insert(TABLE_NAME, null, contentValues);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }


        if (rowId > 0) {
            Uri rUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(rUri, null);
            return rUri;
        }

        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(Uri uri, String where, String[] selectionArgs) {
        int count = 0;
        mDb = sqLiteOpenHelper.getWritableDatabase();
        switch(uriMatcher.match(uri)){
            case PEOPLE:
                count = mDb.delete(TABLE_NAME, where, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String where, String[] selectionArgs) {
        int count = 0;
        mDb = sqLiteOpenHelper.getWritableDatabase();
        switch(uriMatcher.match(uri)){
            case PEOPLE:
                count = mDb.update(TABLE_NAME, contentValues, where, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

}