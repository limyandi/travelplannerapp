package com.mad.madproject.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by limyandivicotrico on 5/15/18.
 */

/**
 * Itinerary class contains the trip lists for each day.
 */
public class Itinerary implements Serializable {
    private ArrayList<Place> mPlaces;

    /**
     * The default constructor
     */
    public Itinerary() {

    }

    /**
     * Alternate constructor that instantiate an object of itinerary with a content of the trips.
     * @param trips the trips list in the whole day of the itinerary
     */
    public Itinerary(ArrayList<Place> trips) {
        this.mPlaces = trips;
    }

    public ArrayList<Place> getPlaces() {
        return mPlaces;
    }

    public void setPlaces(ArrayList<Place> places) {
        this.mPlaces = places;
    }

    public void addPlace(Place place) {
        mPlaces.add(place);
    }
}
