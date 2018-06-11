package com.mad.madproject.utils;


import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.maps.android.SphericalUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by limyandivicotrico on 5/13/18.
 */

/**
 * Class to handle all functions that is being used several times and by several class
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

    /**
     * Get place type, using the algorithms class.
     * @param timeToGo the time to go.
     * @return the place type to go.
     */
    public static String getPlaceType(int timeToGo) {
        RandomCollection rc;
        switch (timeToGo) {
            case 9:
                rc = new RandomCollection().add(50, "park").add(50, "amusement_park");
                return rc.next();
            case 11:
                rc = new RandomCollection().add(50, "restaurant").add(30, "meal_takeaway")
                        .add(20, "cafe");
                return rc.next();
            case 13:
                rc = new RandomCollection().add(40, "aquarium").add(30, "city_hall")
                        .add(30, "shopping_mall");
                return rc.next();
            case 15:
                rc = new RandomCollection().add(40, "movie_theater").add(10, "home_goods_store")
                        .add(50, "zoo");
                return rc.next();
            case 17:
                rc = new RandomCollection().add(70, "restaurant").add(30, "meal_takeaway");
                return rc.next();
            case 19:
                rc = new RandomCollection().add(50, "casino").add(30, "night_club").add(20, "spa");
                return rc.next();
            default:
                //return random if there is an error in time.
                rc = new RandomCollection().add(10, "liquor_store").add(10, "park").add(10, "amusement_park")
                        .add(10, "restaurant").add(10, "mean_takeaway").add(10, "aquarium").add(10, "movie_theater")
                        .add(10, "home_goods_store").add(10, "spa").add(10, "cafe");
                return rc.next();
        }
    }

    /**
     * Create a random number from 0 to 4
     * @return random number
     */
    private static int randomizeNumber() {
        Random rand = new Random();
        //return number from 0 to 4
        return rand.nextInt(5);
    }

    /**
     * Handle error, if the nearby places web service return only very little places.
     * @param size the size of the index.
     * @return randomNumber
     */
    //sometimes the places search might not return any value, or just 1 value or 2 value
    public static int handlePlaceSearchIndexError(int size) {
        int randomNumber = Util.randomizeNumber();

        if (randomNumber > size) {
            randomNumber = size - 1;
        }

        return randomNumber;
    }

    /**
     * Convert the date to day interval
     * @param endDate refer to the date of the trip finish
     * @param startDate refer to the date of the trip starting
     * @return the day interval
     */
    public static int convertDateToDayInterval(Date endDate, Date startDate) {
        return (int) (((endDate.getTime() - startDate.getTime()) / 86400000) + 1);
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

    /**
     * Get a string and parse it into date
     * @param dateString the date in string.
     * @return date format.
     */
    public static Date parseDate(String dateString) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Date date = Calendar.getInstance().getTime();
        try {
            date = dateFormat.parse(dateString);
            //set the local variable end date as the date format so we can find the day interval.
            Log.d(Constant.LOG_TAG, date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
