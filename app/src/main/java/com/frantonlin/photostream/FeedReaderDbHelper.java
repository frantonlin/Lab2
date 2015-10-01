package com.frantonlin.photostream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Database helper
 * Created by Franton on 10/1/15.
 */
public class FeedReaderDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    /**
     * Constructor
     * @param context the context of the helper
     */
    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates a table
     * @param db the database to add the table to
     */
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FeedEntry.SQL_CREATE_ENTRIES);
    }

    /**
     * Upgrade the database
     * @param db the database to update
     * @param oldVersion the old version
     * @param newVersion the new version
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(FeedEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /**
     * Downgrade the database
     * @param db the database to downgrade
     * @param oldVersion the old version
     * @param newVersion the new version
     */
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
