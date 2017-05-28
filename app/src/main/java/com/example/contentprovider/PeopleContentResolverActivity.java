package com.example.contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class PeopleContentResolverActivity extends AppCompatActivity {
    public static final String AUTHORITY = "com.example.contentprovider.provider";
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);
    public static final Uri CONTENT_URI =
            Uri.withAppendedPath(AUTHORITY_URI, "people");

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_PHONE = "PHONE";
    public static final String COLUMN_ADDRESS = "ADDRESS";

    private ListView mPeopleListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resolver);

        mPeopleListView = (ListView) findViewById(R.id.lv_people);

        Button btnAdd = (Button) findViewById(R.id.btn_add_people);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPeopleToContentProvider();
            }
        });

        Button btnShow = (Button) findViewById(R.id.btn_show_people);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPeopleFromContentProvider();
            }
        });

        Button btnUpdate = (Button) findViewById(R.id.btn_update_people);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePeopleFromContentProvider();
            }
        });

        Button btnDelete = (Button) findViewById(R.id.btn_delete_people);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAllPeopleFromContentProvider();
            }
        });
    }

    public void addPeopleToContentProvider(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME , "Jake Peralta");
        contentValues.put(COLUMN_PHONE , "99-123-3553");
        contentValues.put(COLUMN_ADDRESS , "Blooklyn 99");
        getContentResolver().insert(CONTENT_URI, contentValues);

        contentValues.put(COLUMN_NAME , "Amy Santiago");
        contentValues.put(COLUMN_PHONE , "99-123-1122");
        contentValues.put(COLUMN_ADDRESS , "Blooklyn 99");
        getContentResolver().insert(CONTENT_URI, contentValues);
    }

    public void updatePeopleFromContentProvider() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME , "Ray Holt");
        contentValues.put(COLUMN_PHONE , "99-555-5555");
        contentValues.put(COLUMN_ADDRESS , "Blooklyn 99");

        String where = null;
        String[] selectionArgs = null;
        getContentResolver().update(CONTENT_URI, contentValues, null, null);
    }

    public void deleteAllPeopleFromContentProvider() {
        String where = null;
        String[] selectionArgs = null;

        getContentResolver().delete(CONTENT_URI, where, selectionArgs);
    }

    public void showPeopleFromContentProvider(){
        Cursor cursor = getPeopleFromProvider();
        String[] cursorColumns =
                {
                        COLUMN_ID,
                        COLUMN_NAME,
                        COLUMN_PHONE,
                        COLUMN_ADDRESS
                };

        int[] viewIds = {R.id.id, R.id.name, R.id.phone, R.id.address};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(
                getApplicationContext(),
                R.layout.people_row,
                cursor,
                cursorColumns,
                viewIds,
                0);

        mPeopleListView.setAdapter(simpleCursorAdapter);
    }

    private Cursor getPeopleFromProvider(){
        String[] projection =
                {
                        COLUMN_ID,
                        COLUMN_NAME,
                        COLUMN_PHONE,
                        COLUMN_ADDRESS
                };

        String selection = null;
        String[] selectionArgs = null;
        String orderBy =  COLUMN_ID+" ASC";

        return getContentResolver().query(CONTENT_URI, projection,
                    selection, selectionArgs, orderBy );
    }

}
