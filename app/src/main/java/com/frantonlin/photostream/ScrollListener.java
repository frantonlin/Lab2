package com.frantonlin.photostream;

import android.content.Context;
import android.widget.AbsListView;
import com.squareup.picasso.Picasso;

/**
 * ScrollListener for GridView
 * Created by Franton on 10/1/15
 */
public class ScrollListener implements AbsListView.OnScrollListener {
    protected final Context context;

    /**
     * Constructor
     * @param context the context of the ScrollListener
     */
    public ScrollListener(Context context) {
        this.context = context;
    }

    /**
     * Invoked while the view is being scrolled
     * @param view the view whose scroll state is being reported
     * @param scrollState the current scroll state
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        final Picasso picasso = Picasso.with(context);
        if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_TOUCH_SCROLL) {
            picasso.resumeTag(context);
        } else {
            picasso.pauseTag(context);
        }
    }

    /**
     * Invoked when the scroll is completed
     * @param view the view whose scroll state is being reported
     * @param firstVisibleItem the index of the first visible cell
     * @param visibleItemCount the number of visible cells
     * @param totalItemCount the number of items in the list adaptor
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
    }
}
