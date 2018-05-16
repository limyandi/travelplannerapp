package com.mad.madproject.model;

import java.net.URL;

/**
 * Created by limyandivicotrico on 5/15/18.
 */

public class Itinerary {
    private String mItineraryTime;
    private String mItineraryPlace;
    private String mItineraryImageURL;

    public Itinerary() {

    }

    public Itinerary(String itineraryTime, String itineraryPlace, String itineraryImageURL) {
        mItineraryTime = itineraryTime;
        mItineraryPlace = itineraryPlace;
        mItineraryImageURL = itineraryImageURL;
    }

    public String getItineraryTime() {
        return mItineraryTime;
    }

    public void setItineraryTime(String itineraryTime) {
        mItineraryTime = itineraryTime;
    }

    public String getItineraryPlace() {
        return mItineraryPlace;
    }

    public void setItineraryPlace(String itineraryPlace) {
        mItineraryPlace = itineraryPlace;
    }

    public String getItineraryImageURL() {
        return mItineraryImageURL;
    }

    public void setItineraryImageURL(String itineraryImageURL) {
        mItineraryImageURL = itineraryImageURL;
    }
}
