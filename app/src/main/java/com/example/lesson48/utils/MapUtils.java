package com.example.lesson48.utils;

import android.Manifest;

public class MapUtils {

    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    public static final String [] MAP_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    public static final float DEFAULT_ZOOM = 15;
    public static final String MY_LOCATION_TITLE = "My Location";
    public static final long LOCATION_REQUEST_INTERVAL = 6000;

    public static final int MAP_INTENT_RESULT_REQUEST_CODE = 1002;
    public static final int PLACE_INTENT_RESULT_REQUEST_CODE = 1003;
    public static final String MAP_INTENT_RESULT_KEY_LATITUDE = "map_intent_key_latitude";
    public static final String MAP_INTENT_RESULT_KEY_LONGITUDE = "map_intent_key_longitude";

}
