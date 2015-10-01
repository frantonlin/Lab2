package com.frantonlin.photostream;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import java.util.ArrayList;

/**
 * Fragment for searching, displaying, and adding images to the photostream
 * Created by Franton Lin
 */
public class SearchFragment extends Fragment {

    // the user's query
    private String query;
    // the search page number
    private int page;

    public SearchFragment() {}

    /**
     * Initial creation of the fragment, set that fragment has options menu
     * @param savedInstance if the fragment is being re-created from a previous saved state, this is the state
     */
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setHasOptionsMenu(true);
    }

    /**
     * Initialize the options menu
     * @param menu the options menu in which you place your items
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menuitem, menu);
    }

    /**
     * Instantiates the user interface view
     * @param inflater the LayoutInflater object that can be used to inflate any views in the fragment
     * @param container if non-null, this is the parent view that the fragment's UI should be attached to
     * @param savedInstanceState if non-null, this fragment is being re-constructed from a previous saved state as given here
     * @return the View for the fragment's UI
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    /**
     * Set up interactive UI elements
     * @param view the View returned by onCreateView(LayoutInflater, ViewGroup, Bundle)
     * @param savedInstanceState if non-null, this fragment is being re-constructed from a previous saved state as given here
     */
    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        final EditText searchBar = (EditText) view.findViewById(R.id.search_bar);
        ImageButton searchButton = (ImageButton) view.findViewById(R.id.search_button);
        final GridView gridView = (GridView) view.findViewById(R.id.search_grid_view);

        if(searchBar.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!searchBar.getText().toString().isEmpty()) {
                    query = searchBar.getText().toString().replaceAll(" ", "+");
                    page = 1;
                    makeRequestWithCallback(query);
                    GridViewAdapter gva = (GridViewAdapter) ((GridView) getActivity().findViewById(R.id.search_grid_view)).getAdapter();
                    gva.clearImages();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
        searchBar.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            if (getActivity().findViewById(view.getId()) == getActivity().findViewById(R.id.search_bar)) {
                                if(!searchBar.getText().toString().isEmpty()) {
                                    query = searchBar.getText().toString().replaceAll(" ", "+");
                                    page = 1;
                                    makeRequestWithCallback(query);
                                    GridViewAdapter gva = (GridViewAdapter) ((GridView) getActivity().findViewById(R.id.search_grid_view)).getAdapter();
                                    gva.clearImages();
                                }
                            }
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        gridView.setAdapter(new GridViewAdapter(getActivity()));
        gridView.setOnScrollListener(new WebScrollListener(getActivity()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> urls = ((GridViewAdapter) gridView.getAdapter()).getUrls();
                ((MainActivity) getActivity()).saveImage(urls.get(position));
                ((SquaredImageView) gridView.getChildAt(position)).save();
            }
        });
    }

    /**
     * Make a request with a callback when the request completes
     * @param query the query to pass to the request
     */
    public void makeRequestWithCallback(String query) {
        ((MainActivity) getActivity()).getHttpHandler().searchWithCallback(new SuccessCallback() {

            @Override
            public void callback(boolean success, ArrayList<String> urls) {
                if (success) {
                    Log.d("Success", Boolean.toString(success));
                    GridView gv = (GridView) getActivity().findViewById(R.id.search_grid_view);
                    GridViewAdapter gva = (GridViewAdapter) gv.getAdapter();
                    gva.addImages(urls);
                } else {
                    Log.d("Failure", Boolean.toString(success));
                }
            }
        }, query, this.page);
    }

    /**
     * Make a request with a callback when the request completes
     * Uses the last entered query and the next page of results
     */
    public void requestNextPageWithCallback() {
        this.page++;
        ((MainActivity) getActivity()).getHttpHandler().searchWithCallback(new SuccessCallback() {
            @Override
            public void callback(boolean success, ArrayList<String> urls) {
                if (success) {
                    Log.d("Success", Boolean.toString(success));
                    GridView gv = (GridView) getActivity().findViewById(R.id.search_grid_view);
                    GridViewAdapter gva = (GridViewAdapter) gv.getAdapter();
                    gva.addImages(urls);
                } else {
                    Log.d("Failure", Boolean.toString(success));
                }
            }
        }, this.query, this.page);
    }
}
