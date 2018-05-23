package com.mad.madproject.model;

import com.mad.madproject.utils.Util;

/**
 * Created by limyandivicotrico on 5/17/18.
 */

public class ItineraryPreview {
    private String mTripName;
    private String mCity;
    private String mOwnerId;
    private String mItineraryPreviewId;
    private String mStartDate;
    private String mEndDate;
    private int mDayInterval;

    public ItineraryPreview() {

    }

    public ItineraryPreview(String tripName, String city, String ownerId, String itineraryId, String startDate, String endDate) {
        mTripName = tripName;
        mCity = city;
        mOwnerId = ownerId;
        mItineraryPreviewId = itineraryId;
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
