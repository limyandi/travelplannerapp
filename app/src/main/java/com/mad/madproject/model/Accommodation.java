package com.mad.madproject.model;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by limyandivicotrico on 5/7/18.
 */

/**
 * Not really part of the model for the database. Acts as Helper class to clean the code more, so the apps do not need to store 3 different fields like name, address, and mLatLng.
 */
public class Accommodation implements Serializable {
    private String name;
    private String address;
    private String phoneNumber;
    private Uri websiteUri;
    private LatLng mLatLng;

    public Accommodation(String name, String address, String phoneNumber, Uri websiteUri, LatLng latLng) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.websiteUri = websiteUri;
        mLatLng = latLng;
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

    public String toString() {
        return "Name: " + name + "\n" +
                "Address: " + address + "\n";
    }
}
