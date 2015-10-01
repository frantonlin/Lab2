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
 * A placeholder fragment containing a simple view.
 */
public class SearchFragment extends Fragment {

    private String query;
    private int page;

    public SearchFragment() {}

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menuitem, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

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
                    // handle failure
                }
            }
        }, query, this.page);
    }

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
                    // handle failure
                }
            }
        }, this.query, this.page);
    }
}
