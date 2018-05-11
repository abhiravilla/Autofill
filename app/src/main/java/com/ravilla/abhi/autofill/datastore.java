package com.ravilla.abhi.autofill;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class datastore extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "datastore" ;
    private static final String COLUMN_SITE = "Site";
    private static final String COLUMN_USERNAME = "Username";
    private static final String COLUMN_PASSWORD = "Password";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME ="autofill";
    public datastore(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_SITE + " TEXT " +
                COLUMN_USERNAME + " TEXT," +
                COLUMN_PASSWORD + " TEXT,"+
                "PRIMARY KEY ( "+COLUMN_SITE+","+COLUMN_USERNAME+"));";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Creating tables again
        onCreate(db);
    }
    public void addflist(fulllist flist) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SITE, flist.getsite()); // Site Name
        values.put(COLUMN_USERNAME, flist.getuname()); // Username
        values.put(COLUMN_PASSWORD, flist.getpassword()); // Password
        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }
    public List<fulllist> getAll() {
        List<fulllist> fulist = new ArrayList<fulllist>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                fulllist flist = new fulllist();
                flist.setsite(cursor.getString(0));
                flist.setUname(cursor.getString(1));
                flist.setPassword(cursor.getString(2));
                // Adding contact to list
                fulist.add(flist);
            } while (cursor.moveToNext());
        }

        // return contact list
        return fulist;
    }
    public int updatelist(fulllist flist) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        String C_USERNAME = flist.getuname();
        String C_SITE = flist.getsite();
        values.put(COLUMN_PASSWORD, flist.getpassword());
        // updating row
        return db.update(TABLE_NAME, values, COLUMN_SITE + " = ?" + COLUMN_USERNAME + " =?",
                new String[]{C_SITE,C_USERNAME});
    }
    public void deletentry(fulllist flist) {
        SQLiteDatabase db = this.getWritableDatabase();
        String C_USERNAME = flist.getuname();
        String C_SITE = flist.getsite();

        db.delete(TABLE_NAME, COLUMN_SITE + " = ?" + COLUMN_USERNAME + " =?",
                new String[]{C_SITE,C_USERNAME});
        db.close();
    }
}
