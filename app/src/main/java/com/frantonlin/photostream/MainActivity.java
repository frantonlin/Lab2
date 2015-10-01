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

/**
 * Main activity that houses the two fragments
 * Created by Franton Lin
 */
public class MainActivity extends AppCompatActivity {

    // Fragments
    private SearchFragment searchFragment;
    private ViewFragment viewFragment;

    // HTTP and database handlers
    private HTTPHandler httpHandler;
    private FeedReaderDbHelper dbHelper;

    // URLs of saved images
    private ArrayList<String> savedUrls;

    /**
     * Initialize container with ViewFragment and load saved urls from database
     * @param savedInstanceState if the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle)
     */
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

    /**
     * Initializes the options menu
     * @param menu the options menu in which you place your items
     * @return if menu is inflated
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    /**
     * Handles the selecting the menu items
     * @param item the item that was selected
     * @return true if selected item is handled correctly
     */
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

    /**
     * Transitions to the specified fragment
     * @param fragment the fragment to transition to
     */
    public void transitionToFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    /**
     * Getter for the HTTPHandler
     * @return the HTTPHandler
     */
    public HTTPHandler getHttpHandler() {
        return httpHandler;
    }

    /**
     * Save an image URL and add to the database
     * @param url the URL to save
     */
    public void saveImage(String url) {
        savedUrls.add(url);
        addToDatabase(url);
    }

    /**
     * Remove an image URL from the saved URLs and delete from the database
     * @param position the position of the URL to delete
     */
    public void deleteImage(int position) {
        deleteFromDatabase(savedUrls.get(position));
        savedUrls.remove(position);
    }

    /**
     * Getter for the saved URLs
     * @return the saved URLs
     */
    public ArrayList<String> getSavedUrls() {
        return savedUrls;
    }

    /**
     * Adds the specified string to the URL database
     * @param url the URL to add
     * @return the ID of the added row
     */
    public long addToDatabase(String url) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_URL, url);

        long newRowId;
        newRowId = db.insert(
                FeedEntry.TABLE_NAME,
                null,
                values);

        return newRowId;
    }

    /**
     * Reads all of the URLs from the URL database
     * @return an ArrayList of the URLs in the database
     */
    public ArrayList<String> readFromDatabase() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<String> urls = new ArrayList<>();

        String[] projection = {
                FeedEntry._ID,
                FeedEntry.COLUMN_NAME_URL,
        };

        String sortOrder =
                FeedEntry.COLUMN_NAME_URL + " DESC";

        Cursor c = db.query(FeedEntry.TABLE_NAME, projection, null, null, null, null, sortOrder);

        if(c.moveToFirst()) {
            urls.add(c.getString(1));
            while(c.moveToNext())
            {
                urls.add(c.getString(1));
            }
        }

        return urls;
    }

    /**
     * Deletes the specified URL from the database
     * @param url the URL to delete from the database
     */
    public void deleteFromDatabase(String url) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = FeedEntry.COLUMN_NAME_URL + " LIKE ?";
        String[] selectionArgs = {url};
        db.delete(FeedEntry.TABLE_NAME, selection, selectionArgs);
    }
}

