package com.frantonlin.photostream;

/**
 * Created by franton on 10/1/15.
 */
import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

final class GridViewAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<String> urls;

    public GridViewAdapter(Context context) {
        this.context = context;
        this.urls = new ArrayList<>();
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        SquaredImageView view = (SquaredImageView) convertView;
        if (view == null) {
            view = new SquaredImageView(context);
            view.setScaleType(CENTER_CROP);
        }

        // Get the image URL for the current position.
        String url = getItem(position);

        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(context)
                .load(url)
//                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .fit()
                .centerCrop()
                .tag(context)
                .into(view);

        return view;
    }

    @Override public int getCount() {
        return urls.size();
    }

    @Override public String getItem(int position) {
        return urls.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }

    public void addImages(ArrayList<String> urls) {
        this.urls.addAll(urls);
        notifyDataSetChanged();
    }

    public void clearImages() {
        this.urls.clear();
        notifyDataSetChanged();
    }

    public ArrayList<String> getUrls(){
        return this.urls;
    }
}
