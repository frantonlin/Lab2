package com.frantonlin.photostream;

import android.app.Activity;
import android.app.Fragment;
import android.app.Service;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchFragment extends Fragment {

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final EditText searchBar = (EditText) view.findViewById(R.id.search_bar);
        ImageButton searchButton = (ImageButton) view.findViewById(R.id.search_button);

        if(searchBar.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchBar = (EditText) getActivity().findViewById(R.id.search_bar);
                setText(searchBar.getText().toString());
            }
        });
        searchBar.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event){
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_ENTER:
                            if(((EditText) getActivity().findViewById(view.getId())) == ((EditText) getActivity().findViewById(R.id.search_bar))){
                                setText(searchBar.getText().toString());
                            }
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
    }

    public void setText(String text) {
        String url = "http://www.google.com";

        final TextView tester = (TextView) getActivity().findViewById(R.id.tester);
//        tester.setText(text);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 50 characters of the response string.
                        tester.setText("Response is: "+ response.substring(0,50));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tester.setText("That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        ((MainActivity) getActivity()).getQueue().add(stringRequest);
    }

    public void search(String query) {
    // request key: AIzaSyCU_NgWKrIokTxsV2MM_8o2x7Iko9e3ARI
    // search id: 014156318284437473397:qkwz6_9kcmk
    // Add the URL parameter searchType=image
    }
}
