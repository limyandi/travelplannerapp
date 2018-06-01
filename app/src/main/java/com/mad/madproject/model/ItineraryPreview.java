package com.mad.madproject.model;

import com.mad.madproject.utils.Util;

import java.io.Serializable;

/**
 * Created by limyandivicotrico on 5/17/18.
 */

/**
 * The ItineraryPreview model class contains the attribute defined by each Itinerary.
 */
public class ItineraryPreview implements Serializable {
    private String mTripName;
    private String mCity;
    private String mOwnerId;
    private String mItineraryPreviewId;
    private String mStartDate;
    private String mEndDate;
    private int mDayInterval;

    public ItineraryPreview() {

    }

    /**
     * Constructor to create an object of ItineraryPreview.
     * @param tripName define the trip name
     * @param city define the city name
     * @param ownerId define the owner id of the itienrary preview.
     * @param itineraryPreviewId define the itinerary id of the itinerary preview
     * @param startDate define the start date
     * @param endDate define the end date
     */
    public ItineraryPreview(String tripName, String city, String ownerId, String itineraryPreviewId, String startDate, String endDate) {
        mTripName = tripName;
        mCity = city;
        mOwnerId = ownerId;
        mItineraryPreviewId = itineraryPreviewId;
        mStartDate = startDate;
        mEndDate = endDate;
        mDayInterval = Util.convertDateToDayInterval(mEndDate, mStartDate);
    }

    public String getTripName() {
        return mTripName;
    }

    public void setTripName(String tripName) {
        mTripName = tripName;
    }


    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getOwnerId() {
        return mOwnerId;
    }

    public void setOwnerId(String ownerId) {
        mOwnerId = ownerId;
    }

    public String getItineraryPreviewId() {
        return mItineraryPreviewId;
    }

    public void setItineraryPreviewId(String itineraryPreviewId) {
        mItineraryPreviewId = itineraryPreviewId;
    }

    public String getStartDate() {
        return mStartDate;
    }

    public void setStartDate(String startDate) {
        mStartDate = startDate;
    }

    public String getEndDate() {
        return mEndDate;
    }

    public void setEndDate(String endDate) {
        mEndDate = endDate;
    }

    public int getDayInterval() {
        return mDayInterval;
    }

    public void setDayInterval(int dayInterval) {
        mDayInterval = dayInterval;
    }

    public String toString() {
        return "City: " + mCity + " Date: " + mStartDate + " - " + mEndDate;
    }


}
