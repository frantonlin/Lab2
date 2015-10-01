package com.frantonlin.photostream;

import java.util.ArrayList;

/**
 * Interface for callbacks
 * Created by Franton on 10/1/15
 */
public interface SuccessCallback {
    void callback(boolean success, ArrayList<String> urls);
}
