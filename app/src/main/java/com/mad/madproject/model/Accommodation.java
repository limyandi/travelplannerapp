package com.mad.madproject.model;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by limyandivicotrico on 5/7/18.
 */

/**
 * Not really part of the model for the database. Acts as Helper class to clean the code more, so the apps do not need to store 3 different fields like mName, mAddress, and mLatLng.
 */
public class Accommodation implements Serializable {
    private String mName;
    private String mAddress;
    private String mPhoneNumber;
    private Uri mWebsiteUri;
    private LatLng mLatLng;

    public Accommodation(String name, String address, String phoneNumber, Uri websiteUri, LatLng latLng) {
        this.mName = name;
        this.mAddress = address;
        this.mPhoneNumber = phoneNumber;
        this.mWebsiteUri = websiteUri;
        mLatLng = latLng;
    }

    public Accommodation() {
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        this.mAddress = address;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.mPhoneNumber = phoneNumber;
    }

    public Uri getWebsiteUri() {
        return mWebsiteUri;
    }

    public void setWebsiteUri(Uri websiteUri) {
        this.mWebsiteUri = websiteUri;
    }

    public LatLng getLatLng() {
        return mLatLng;
    }

    public void setLatLng(LatLng latLng) {
        mLatLng = latLng;
    }

    public String toString() {
        return "Name: " + mName + "\n" +
                "Address: " + mAddress + "\n";
    }
}
