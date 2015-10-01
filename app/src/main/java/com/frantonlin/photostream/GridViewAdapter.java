package com.frantonlin.photostream;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import static android.widget.ImageView.ScaleType.CENTER_CROP;

/**
 * GridViewAdapter to insert images in the GridView
 * Created by Franton on 10/1/15
 */
final class GridViewAdapter extends BaseAdapter {
    // The context of the adapter
    private final Context context;
    // The ArrayList of the urls of the images
    private final ArrayList<String> urls;

    /**
     * Constructor
     * @param context the context of the adapter
     */
    public GridViewAdapter(Context context) {
        this.context = context;
        this.urls = new ArrayList<>();
    }

    /**
     * Gets the view for the specified position
     * @param position the position of the item within the adapter's data set of the item whose view we want
     * @param convertView the old view to reuse, if possible
     * @param parent the parent that this view will eventually be attached to
     * @return the view for the specified position
     */
    @Override public View getView(int position, View convertView, ViewGroup parent) {
        SquaredImageView view = (SquaredImageView) convertView;
        if (view == null) {
            view = new SquaredImageView(context);
            view.setScaleType(CENTER_CROP);
        }

        String url = getItem(position);

        Picasso.with(context)
                .load(url)
                .error(R.drawable.error)
                .fit()
                .centerCrop()
                .tag(context)
                .into(view);

        return view;
    }


    /**
     * Getter for the number of image URLs in the adapter
     * @return the number of image URLs in the adapter
     */
    @Override public int getCount() {
        return urls.size();
    }

    /**
     * Getter for a specific URL in the adapter
     * @param position the position of the URL to get
     * @return the URL at the specified position
     */
    @Override public String getItem(int position) {
        return urls.get(position);
    }

    /**
     * Getter for the item ID at the specified position
     * @param position the position of the item ID to get
     * @return the item ID at the specified position
     */
    @Override public long getItemId(int position) {
        return position;
    }

    /**
     * Getter for the URLs in the adapter
     * @return the URLs in the adapter
     */
    public ArrayList<String> getUrls(){
        return this.urls;
    }

    /**
     * Add multiple image URLs to the adapter
     * @param urls the URLs to add
     */
    public void addImages(ArrayList<String> urls) {
        this.urls.addAll(urls);
        notifyDataSetChanged();
    }

    /**
     * Remove an image URL from the adapter
     * @param position the position of the URL to remove
     */
    public void removeImage(int position) {
        this.urls.remove(position);
        notifyDataSetChanged();
    }

    /**
     * Clear all images out of the adapter
     */
    public void clearImages() {
        this.urls.clear();
        notifyDataSetChanged();
    }
}
