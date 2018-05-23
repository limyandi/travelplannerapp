package com.mad.madproject.model;

import com.mad.madproject.utils.Util;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by limyandivicotrico on 5/15/18.
 */

public class Itinerary implements Serializable {
    private ArrayList<Trip> mTrips;

    public Itinerary() {

    }

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
