package com.mad.madproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by limyandivicotrico on 5/30/18.
 */

/**
 * Geometry class handles the POJO to define the Geometrical Location of a place from the web service.
 */
public class Geometry implements Serializable {
    @SerializedName("location")
    private Location mLocation;

    /**
     * Getter methods
     * @return location
     */
    public Location getLocation() {
        return mLocation;
    }

    /**
     * Setter methods
     * @param location the new location
     */
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
