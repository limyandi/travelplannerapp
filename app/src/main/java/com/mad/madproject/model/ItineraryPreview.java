package com.mad.madproject.model;

/**
 * Created by limyandivicotrico on 5/17/18.
 */

public class ItineraryPreview {
    private String mCity;
    private String mImageURL;
    private String mInDays;

    public ItineraryPreview() {

    }

    public ItineraryPreview(String imageURL, String city, String inDays) {
        mCity = city;
        mImageURL = imageURL;
        mInDays = inDays;
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

    public String getInDays() {
        return mInDays;
    }

    public void setInDays(String inDays) {
        mInDays = inDays;
    }

    public String toString() {
        return "City: " + mCity + " In days: " + mInDays;
    }
}
