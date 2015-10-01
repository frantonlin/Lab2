package com.frantonlin.photostream;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

//    private static final String TAG = MainActivity.class.getSimpleName();
    private SearchFragment searchFragment;
    private ViewFragment viewFragment;

    // Instantiate the HTTPHandler
    private HTTPHandler httpHandler;

    private ArrayList<String> savedUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchFragment = new SearchFragment();
        viewFragment = new ViewFragment();
        getFragmentManager().beginTransaction().add(R.id.container, searchFragment).commit();

        httpHandler = new HTTPHandler(this);

        savedUrls = new ArrayList<>();
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
            case R.id.back_to_stream:
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
        Log.d("SAVE URLS", savedUrls.toString());
    }

    public ArrayList<String> getSavedUrls() {
        return savedUrls;
    }
}

