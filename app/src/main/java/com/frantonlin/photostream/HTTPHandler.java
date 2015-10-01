package com.frantonlin.photostream;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Handles the HTTP requests
 * Created by Franton on 10/1/15.
 */
public class HTTPHandler {

    // The request queue for Volley
    public RequestQueue queue;

    /**
     * Constructor
     * @param context the context of the handler
     */
    public HTTPHandler(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    /**
     * Search the specified query from the specified page with a callback
     * @param callback the callback
     * @param query the query to search
     * @param page the page to start the search from
     */
    public void searchWithCallback(final SuccessCallback callback, String query, int page) {
        String url = "https://www.googleapis.com/customsearch/v1?";
        String key = "AIzaSyCU_NgWKrIokTxsV2MM_8o2x7Iko9e3ARI";
        String key2 = "AIzaSyCUjoI4H76ZXerGg8xPgjc2E2wUOyh8jE0";
        String key3 = "AIzaSyBIIvvq1ObxS1BqY_tlIKcv0i9w34jL_s4";
        String cx = "014156318284437473397:qkwz6_9kcmk";
        String cx2 = "014156318284437473397:bxllsb702mi";
        String cx3 = "014156318284437473397:9xv_wx2tayq";
        String searchType = "image";
        String num = "10";
        String start = Integer.toString((page-1)*10+1);

        String requestUrl = url +
                "key=" + key3 +
                "&cx=" + cx3 +
                "&searchType=" + searchType +
                "&num=" + num +
                "&start=" + start +
                "&q=" + query;
        Log.d("URL", requestUrl);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                requestUrl,
                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // we got a response, success!
                        try {
                            JSONArray items = response.getJSONArray("items");
                            ArrayList<String> urls = new ArrayList<String>();
                            for(int i = 0; i < items.length(); i++) {
                                urls.add(items.getJSONObject(i).getString("link"));
                            }
                            callback.callback(true, urls);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.callback(false, null);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // we had an error, failure!
                        callback.callback(false, null);
                    }
                }
        );

        request.addMarker("search");
        queue.add(request);
    }
}