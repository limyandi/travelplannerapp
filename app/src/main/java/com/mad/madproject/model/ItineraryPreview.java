package com.mad.madproject.model;

/**
 * Created by limyandivicotrico on 5/17/18.
 */

public class ItineraryPreview {
    private String mTripName;
    private String mCity;
    private String mImageURL;
    private String mLengthOfTrip;

    public ItineraryPreview() {

    }

    public ItineraryPreview(String tripName, String imageURL, String city, String lengthOfTrip) {
        mTripName = tripName;

        mCity = city;
        mImageURL = imageURL;
        mLengthOfTrip = lengthOfTrip;
    }

    public String getTripName() {
        return mTripName;
    }

    public void setTripName(String tripName) {
        mTripName = tripName;
    }

    public String getImageURL() {
        return mImageURL;
    }

    public void setImageURL(String imageURL) {
        mImageURL = imageURL;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getLengthOfTrip() {
        return mLengthOfTrip;
    }

    public void setLengthOfTrip(String lengthOfTrip) {
        mLengthOfTrip = lengthOfTrip;
    }

    public String toString() {
        return "City: " + mCity + " In days: " + mLengthOfTrip;
    }
}
