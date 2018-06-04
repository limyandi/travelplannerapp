package com.mad.madproject.utils;


import android.support.v4.app.Fragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.maps.android.SphericalUtil;

import java.util.Date;

/**
 * Created by limyandivicotrico on 5/13/18.
 */

public class Util {
    /**
     * Convert a latitude and longitude and radius to a new bounds (using android-maps-utils package)
     * @param center the focal point (in our case, the accommodation chosen)
     * @param radiusInMeters the radius in meters
     * @return LatLngBounds determining how far the bound should be
     */
    public static LatLngBounds toBounds(LatLng center, double radiusInMeters) {
        double distanceFromCenterToCorner = radiusInMeters * Math.sqrt(2.0);
        LatLng southwestCorner = SphericalUtil.computeOffset(center, distanceFromCenterToCorner, 225.0);
        LatLng northeastCorner = SphericalUtil.computeOffset(center, distanceFromCenterToCorner, 45.0);
        return new LatLngBounds(southwestCorner, northeastCorner);
    }

    /**
     * Method to return user uid from the Firebase instance
     * @return user uid from firebase.
     */
    public static String getUserUid() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        return auth.getUid();
    }

    /**
     * Return the database reference of any passed in string reference
     * @param reference the name of the database reference in the database table.
     * @return
     */
    public static DatabaseReference getDatabaseReference(String reference) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        return database.getReference(reference);
    }

    /**
     * Return the storage reference of any passed in string reference
     * @param reference the name of the storage reference in the storage table.
     * @return
     */
    public static StorageReference getStorageReference(String reference) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        return storage.getReference(reference);
    }

    //TODO: Redundant function, we have two similar function.
    public static int convertDateToDayInterval(Date endDate, Date startDate) {
        return (int) (((endDate.getTime() - startDate.getTime()) / 86400000) + 1);
    }

    /**
     * TODO: Needs to be fix with time stamp instead, because in this, it could caused problem like if user choose 31 June and 2 May, the interval day would be 29.
     * Convert the date to day interval
     * @param endDate refer to the date of the trip finish
     * @param startDate refer to the date of the trip starting
     * @return the day interval
     */
    public static int convertDateToDayInterval(String endDate, String startDate) {
        int intervalDay = Integer.valueOf(endDate.substring(0, 2)) - Integer.valueOf(startDate.substring(0, 2)) + 1;
        return intervalDay;
    }

    /**
     * Utility function to check if the itinerary is already expired (if the date today has passed the end date.)
     * @param endDate the date the trip ends
     * @param today today's date
     * @return boolean of true or false.
     */
    public static boolean isExpired(Date endDate, Date today) {
        return today.after(endDate);
    }

    /**
     * Utility Function to set the fragment toolbar title.
     * @param fragment determine which fragment to set
     * @param title determine the title.
     */
    public static void setFragmentToolbarTitle(Fragment fragment, String title) {
        if(fragment.getActivity() != null) {
            fragment.getActivity().setTitle(title);
        }
    }
}
