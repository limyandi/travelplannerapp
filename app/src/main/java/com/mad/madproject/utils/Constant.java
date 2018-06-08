package com.mad.madproject.utils;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by limyandivicotrico on 5/2/18.
 */

public class Constant {
    public static final String LOG_TAG = "Rico Travel";
    public static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(-40, -168), new LatLng(71,136));
    //this is 4 days in milliseconds.
    public static final long DAY_LIMIT = 345600000;
    public static final int PROXIMITY_RADIUS = 50000;
}
