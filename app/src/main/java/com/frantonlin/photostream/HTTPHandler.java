package com.frantonlin.photostream;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.security.auth.callback.Callback;

/**
 * Created by franton on 10/1/15.
 */
public class HTTPHandler {

    public RequestQueue queue; // this is where you should usually put the queue

    public HTTPHandler(Context context) {
        queue = Volley.newRequestQueue(context); // queue must be initialized with context, so create initializer which does this
    }

    public void searchWithCallback(final SuccessCallback callback, String query, int page) {
        String url = "https://www.googleapis.com/customsearch/v1?";
        String key = "AIzaSyCU_NgWKrIokTxsV2MM_8o2x7Iko9e3ARI";
        String cx = "014156318284437473397:qkwz6_9kcmk";
        String searchType = "image";
        String num = "10";
        String start = Integer.toString((page-1)*10+1);

        String requestUrl = url +
                "key=" + key +
                "&cx=" + cx +
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