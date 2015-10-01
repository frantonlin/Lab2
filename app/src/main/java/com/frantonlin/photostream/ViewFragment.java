package com.frantonlin.photostream;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

/**
 * Fragment for displaying images added to photostream
 * Created by Franton on 10/1/15
 */
public class ViewFragment extends Fragment {

    public ViewFragment() {}

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
        inflater.inflate(R.menu.view_menuitem, menu);
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
        return inflater.inflate(R.layout.fragment_view, container, false);
    }

    /**
     * Set up interactive UI elements
     * @param view the View returned by onCreateView(LayoutInflater, ViewGroup, Bundle)
     * @param savedInstanceState if non-null, this fragment is being re-constructed from a previous saved state as given here
     */
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
