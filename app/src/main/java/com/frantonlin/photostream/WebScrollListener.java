package com.frantonlin.photostream;

import android.app.FragmentManager;
import android.content.Context;
import android.widget.AbsListView;

/**
 * Created by franton on 10/1/15.
 */
public class WebScrollListener extends ScrollListener {
    private int numberQueried;

    public WebScrollListener(Context context) {
        super(context);
        this.numberQueried = 0;
    }

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
