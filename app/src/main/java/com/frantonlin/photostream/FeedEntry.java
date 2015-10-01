package com.frantonlin.photostream;

import android.provider.BaseColumns;

/**
 * Created by franton on 10/1/15.
 */
public class FeedEntry implements BaseColumns {
    public static final String TABLE_NAME = "savedUrls";
    public static final String COLUMN_NAME_URL = "url";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_URL + TEXT_TYPE +
            ")";
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    public FeedEntry() {}
}
