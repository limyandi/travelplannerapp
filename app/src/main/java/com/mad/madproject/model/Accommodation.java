package com.mad.madproject.model;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by limyandivicotrico on 5/7/18.
 */

public class Accommodation implements Serializable {
    private String name;
    private String address;
    private String phoneNumber;
    private Uri websiteUri;
    private LatLng mLatLng;
    private float rating;

    public Accommodation(String name, String address, String phoneNumber, Uri websiteUri, LatLng latLng, float rating) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.websiteUri = websiteUri;
        mLatLng = latLng;
        this.rating = rating;
    }

    public Accommodation() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Uri getWebsiteUri() {
        return websiteUri;
    }

    public void setWebsiteUri(Uri websiteUri) {
        this.websiteUri = websiteUri;
    }

    public LatLng getLatLng() {
        return mLatLng;
    }

    public void setLatLng(LatLng latLng) {
        mLatLng = latLng;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String toString() {
        return "Name: " + name + "\n" +
                "Address: " + address + "\n";
    }
}
