package com.mad.madproject.model;

import java.io.Serializable;

/**
 * Created by limyandivicotrico on 5/19/18.
 */

/**
 * This class contains each trip details.
 */
public class Trip implements Serializable {
    private String mTripPlace;
    private String mItineraryImageURL;
    private String mTripTime;

    /**
     * Default constructor
     */
    public Trip() {

    }


    /**
     * TODO: Currently, itineraryImageURL is not needed. Maybe next version or if we still have time.
     * Alternate constructor that instantiate an object trip that defines the trip place, URL for the image, and the trip time
     * @param tripPlace define the name of the place to go
     * @param itineraryImageURL define the picture of this place
     * @param tripTime define the time to go.
     */
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
