package com.mad.madproject.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by limyandivicotrico on 5/30/18.
 */

/**
 * Geometry class handles the POJO to define the Geometrical Location of a place from the web service.
 */
public class Geometry {
    @SerializedName("location")
    private Location mLocation;

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }

    @Override
    public String toString() {
        return "Geometry{" +
                "location=" + mLocation +
                '}';
    }
}