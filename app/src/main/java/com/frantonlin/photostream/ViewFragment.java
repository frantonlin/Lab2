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
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;

import java.util.ArrayList;

/**
 * Created by franton on 10/1/15.
 */
public class ViewFragment extends Fragment {

    public ViewFragment() {}

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.view_menuitem, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        final GridView gridView = (GridView) view.findViewById(R.id.view_grid_view);

        gridView.setAdapter(new GridViewAdapter(getActivity()));
        gridView.setOnScrollListener(new ScrollListener(getActivity()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity) getActivity()).deleteImage(position);
                ((GridViewAdapter) gridView.getAdapter()).removeImage(position);
            }
        });
        GridViewAdapter gva = (GridViewAdapter) gridView.getAdapter();
        gva.addImages(((MainActivity) getActivity()).getSavedUrls());
    }
}
