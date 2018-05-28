package com.mad.madproject.model;

import com.mad.madproject.utils.Util;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by limyandivicotrico on 5/15/18.
 */

/**
 * Itinerary class contains the trip lists for each day.
 */
public class Itinerary implements Serializable {
    private ArrayList<Trip> mTrips;

    /**
     * The default constructor
     */
    public Itinerary() {

    }

    /**
     * Alternate constructor that instantiate an object of itinerary with a content of the trips.
     * @param trips the trips list in the whole day of the itinerary
     */
    public Itinerary(ArrayList<Trip> trips) {
        this.mTrips = trips;
    }

    public ArrayList<Trip> getTrips() {
        return mTrips;
    }

    public void setTrips(ArrayList<Trip> trips) {
        this.mTrips = trips;
    }
}
