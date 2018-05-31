package com.mad.madproject.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by limyandivicotrico on 5/30/18.
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
