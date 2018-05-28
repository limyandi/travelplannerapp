package com.mad.madproject.model;

import com.mad.madproject.utils.Util;

import java.util.ArrayList;

/**
 * Created by limyandivicotrico on 5/19/18.
 */

/**
 * This class acts as the parent class for the itinerary details, it holds the itinerarylists for all day, the tripname, startdate, enddate,
 * and previewid(foreign key in database) so that it can be sent to database.
 */
public class Itineraries {
    private ArrayList<Itinerary> mItineraryLists;
    private String mTripName;
    private String mStartDate;
    private String mEndDate;
    private String mItineraryPreviewId;
    private int mIntervalDay;

    /**
     * Constructor that takes the parameters of the itinerarylists, the trip name, start date, end date and itinerary previewId.
     * @param itineraryLists Contains the itinerary lists for each day.
     * @param tripName the trip name
     * @param startDate the start date
     * @param endDate the end date
     * @param itineraryPreviewId the foreign key ItineraryPreviewID
     */
    public Itineraries(ArrayList<Itinerary> itineraryLists, String tripName, String startDate, String endDate, String itineraryPreviewId) {
        mItineraryLists = itineraryLists;
        mTripName = tripName;
        mStartDate = startDate;
        mEndDate = endDate;
        mItineraryPreviewId = itineraryPreviewId;
        mIntervalDay = Util.convertDateToDayInterval(mEndDate, mStartDate);
    }

    /**
     * Default Constructor(Not used).
     */
    public Itineraries() {

    }

    public ArrayList<Itinerary> getItineraryLists() {
        return mItineraryLists;
    }

    public void setItineraryLists(ArrayList<Itinerary> itineraryLists) {
        mItineraryLists = itineraryLists;
    }

    public String getItineraryPreviewId() {
        return mItineraryPreviewId;
    }

    public void setItineraryPreviewId(String itineraryPreviewId) {
        mItineraryPreviewId = itineraryPreviewId;
    }

    public String getTripName() {
        return mTripName;
    }

    public void setTripName(String tripName) {
        mTripName = tripName;
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

    public int getIntervalDay() {
        return mIntervalDay;
    }

    public void setIntervalDay(int intervalDay) {
        mIntervalDay = intervalDay;
    }

    public String toString() {
        return mTripName + mIntervalDay + mEndDate;
    }
}
