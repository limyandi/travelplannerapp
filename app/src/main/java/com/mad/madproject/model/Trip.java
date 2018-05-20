package com.mad.madproject.model;

/**
 * Created by limyandivicotrico on 5/19/18.
 */

public class Trip {
    private String mTripPlace;
    private String mItineraryImageURL;
    private String mTripTime;

    public Trip() {

    }

    public Trip(String tripPlace, String itineraryImageURL, String tripTime) {
        mTripPlace = tripPlace;
        mItineraryImageURL = itineraryImageURL;
        mTripTime = tripTime;
    }

    public String getTripPlace() {
        return mTripPlace;
    }

    public void setTripPlace(String tripPlace) {
        mTripPlace = tripPlace;
    }

    public String getItineraryImageURL() {
        return mItineraryImageURL;
    }

    public void setItineraryImageURL(String itineraryImageURL) {
        mItineraryImageURL = itineraryImageURL;
    }

    public String getTripTime() {
        return mTripTime;
    }

    public void setTripTime(String tripTime) {
        mTripTime = tripTime;
    }

    public String toString() {
        return "Trip Place: " + mTripPlace + ", Trip Time:" + mTripTime;
    }
}
