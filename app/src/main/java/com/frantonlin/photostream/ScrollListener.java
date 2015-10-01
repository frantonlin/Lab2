package com.frantonlin.photostream;

/**
 * Created by franton on 10/1/15.
 */
import android.app.FragmentManager;
import android.content.Context;
import android.widget.AbsListView;

import com.squareup.picasso.Picasso;

public class ScrollListener implements AbsListView.OnScrollListener {
    protected final Context context;

    public ScrollListener(Context context) {
        this.context = context;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        final Picasso picasso = Picasso.with(context);
        if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_TOUCH_SCROLL) {
            picasso.resumeTag(context);
        } else {
            picasso.pauseTag(context);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        // do nothing
    }
}
