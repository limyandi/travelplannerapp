package com.mad.madproject.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by limyandivicotrico on 5/30/18.
 */

public class Location {
    @SerializedName("lat")
    private double mLat;
    @SerializedName("lng")
    private double mLng;

    public double getLat() {
        return mLat;
    }

    public void setLat(double lat) {
        this.mLat = lat;
    }

    public double getLng() {
        return mLng;
    }

    public void setLng(double lng) {
        this.mLng = lng;
    }

    @Override
    public String toString() {
        return "Geometry{" +
                "lat=" + mLat +
                ", lng=" + mLng +
                '}';
    }
}

