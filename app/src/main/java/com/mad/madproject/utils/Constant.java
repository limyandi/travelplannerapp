package com.mad.madproject.utils;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by limyandivicotrico on 5/2/18.
 */

/**
 * Constant class to hold all static final type attributes to be reused.
 */
public class Constant {
    public static final String LOG_TAG = "Rico Travel";
    public static final String LOG_TAG_MVVM = "MVVM";
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final int ONE_DAY = 86400000;
    public static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(-40, -168), new LatLng(71,136));
    //this is 4 days in milliseconds.
    public static final long DAY_LIMIT = 345600000;
    public static final int PROXIMITY_RADIUS = 50000;
    public static final String CITY_KEY = "City";
    public static final String LATITUDE_KEY = "Latitude";
    public static final String LONGITUDE_KEY = "Longitude";
    public static final String DAYS_KEY = "Day";
    public static final String ITINERARY_PREVIEW_KEY = "ItineraryPreview";
    public static final String ITINERARY_PREVIEW_PUSH_KEY_KEY = "PreviewKey";
    public static final String ITINERARY_LISTS_KEY = "Itinerary List";
}
