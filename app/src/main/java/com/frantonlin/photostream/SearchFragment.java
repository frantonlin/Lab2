package com.frantonlin.photostream;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        final EditText searchBar = (EditText) view.findViewById(R.id.search_bar);
        ImageButton searchButton = (ImageButton) view.findViewById(R.id.search_button);

        if(searchBar.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query = searchBar.getText().toString();
                page = 1;
                makeRequestWithCallback(query);
                GridViewAdapter gva = (GridViewAdapter) ((GridView) getActivity().findViewById(R.id.grid_view)).getAdapter();
                gva.clearImages();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        searchBar.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            if (getActivity().findViewById(view.getId()) == getActivity().findViewById(R.id.search_bar)) {
                                query = searchBar.getText().toString();
                                page = 1;
                                makeRequestWithCallback(query);
                                GridViewAdapter gva = (GridViewAdapter) ((GridView) getActivity().findViewById(R.id.grid_view)).getAdapter();
                                gva.clearImages();
                            }
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        GridView gv = (GridView) getActivity().findViewById(R.id.grid_view);
        gv.setAdapter(new GridViewAdapter(getActivity()));
        gv.setOnScrollListener(new ScrollListener(getActivity()));
    }

    public void makeRequestWithCallback(String query) {
        ((MainActivity) getActivity()).getHttpHandler().searchWithCallback(new SuccessCallback() {

            @Override
            public void callback(boolean success, ArrayList<String> urls) {
                if (success) {
                    Log.d("Success", Boolean.toString(success));
                    GridView gv = (GridView) getActivity().findViewById(R.id.grid_view);
                    GridViewAdapter gva = (GridViewAdapter) gv.getAdapter();
//                    ((MainActivity) getActivity()).getHttpHandler().queue.cancelAll(null);
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
                    GridView gv = (GridView) getActivity().findViewById(R.id.grid_view);
                    GridViewAdapter gva = (GridViewAdapter) gv.getAdapter();
//                    ((MainActivity) getActivity()).getHttpHandler().queue.cancelAll(null);
                    gva.addImages(urls);
                } else {
                    Log.d("Failure", Boolean.toString(success));
                    // handle failure
                }
            }
        }, this.query, this.page);
    }
}
