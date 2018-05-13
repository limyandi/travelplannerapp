package com.mad.madproject.model;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by limyandivicotrico on 5/2/18.
 */

public class City implements Serializable {
    private String mImage;
    private String mCity;
    private String mCountry;
    private LatLng mLatLng;

    public City() {

    }

    public City(String image, String city, String country, LatLng latLng) {
        mImage = image;
        mCity = city;
        mCountry = country;
        mLatLng = latLng;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public LatLng getLatLng() {
        return mLatLng;
    }

    public void setLatLng(LatLng latLng) {
        mLatLng = latLng;
    }
}
