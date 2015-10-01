package com.frantonlin.photostream;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private SearchFragment searchFragment;
    private ViewFragment viewFragment;

    // Instantiate the HTTPHandler
    private HTTPHandler httpHandler;
    private FeedReaderDbHelper dbHelper;

    private ArrayList<String> savedUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchFragment = new SearchFragment();
        viewFragment = new ViewFragment();
        getFragmentManager().beginTransaction().add(R.id.container, viewFragment).commit();

        httpHandler = new HTTPHandler(this);
        dbHelper = new FeedReaderDbHelper(this);

        savedUrls = readFromDatabase();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Handle presses on the action bar items
        switch (id) {
            case R.id.to_stream:
                transitionToFragment(viewFragment);
                return true;
            case R.id.find_images:
                transitionToFragment(searchFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void transitionToFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    public HTTPHandler getHttpHandler() {
        return httpHandler;
    }

    public void saveImage(String url) {
        savedUrls.add(url);
        addToDatabase(url);
    }

    public void deleteImage(int position) {
        deleteFromDatabase(savedUrls.get(position));
        savedUrls.remove(position);
    }

    public ArrayList<String> getSavedUrls() {
        return savedUrls;
    }

    public long addToDatabase(String url) {
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_URL, url);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                FeedEntry.TABLE_NAME,
                null,
                values);

        return newRowId;
    }

    public ArrayList<String> readFromDatabase() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<String> urls = new ArrayList<>();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FeedEntry._ID,
                FeedEntry.COLUMN_NAME_URL,
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                FeedEntry.COLUMN_NAME_URL + " DESC";

        Cursor c = db.query(
                FeedEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if(c.moveToFirst()) {
            urls.add(c.getString(1));
            while(c.moveToNext())
            {
                urls.add(c.getString(1));
            }
        }

        return urls;
    }

    public void deleteFromDatabase(String url) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Define 'where' part of query.
        String selection = FeedEntry.COLUMN_NAME_URL + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {url};
        // Issue SQL statement.
        db.delete(FeedEntry.TABLE_NAME, selection, selectionArgs);
    }
}

