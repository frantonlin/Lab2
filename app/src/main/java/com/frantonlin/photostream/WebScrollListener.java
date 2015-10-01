package com.frantonlin.photostream;

import android.app.FragmentManager;
import android.content.Context;
import android.widget.AbsListView;

/**
 * ScrollListener for GridView that continuously loads images
 * Created by Franton on 10/1/15
 */
public class WebScrollListener extends ScrollListener {
    // the number of results that have been queried
    private int numberQueried;

    /**
     * Constructor
     * @param context the context of the ScrollListener
     */
    public WebScrollListener(Context context) {
        super(context);
        this.numberQueried = 0;
    }

    /**
     * Requests next page of results if scrolled to the bottom
     * @param view the view whose scroll state is being reported
     * @param firstVisibleItem the index of the first visible cell
     * @param visibleItemCount the number of visible cells
     * @param totalItemCount the number of items in the list adaptor
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        if(totalItemCount > 0 && firstVisibleItem + visibleItemCount >= totalItemCount){
            if(totalItemCount > numberQueried) {
                FragmentManager fm = ((MainActivity) context).getFragmentManager();
                SearchFragment searchFragment = (SearchFragment) fm.findFragmentById(R.id.container);
                searchFragment.requestNextPageWithCallback();
                numberQueried = totalItemCount;
            }
        } else if(totalItemCount == 0) {
            numberQueried = 0;
        }
    }
}
