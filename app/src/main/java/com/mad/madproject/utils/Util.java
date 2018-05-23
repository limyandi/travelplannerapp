package com.mad.madproject.utils;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.maps.android.SphericalUtil;

/**
 * Created by limyandivicotrico on 5/13/18.
 */

public class Util {
    /**
     * Convert a latitude and longitude and radius to a new bounds (using android-maps-utils package)
     * @param center
     * @param radiusInMeters
     * @return
     */
    public static LatLngBounds toBounds(LatLng center, double radiusInMeters) {
        double distanceFromCenterToCorner = radiusInMeters * Math.sqrt(2.0);
        LatLng southwestCorner = SphericalUtil.computeOffset(center, distanceFromCenterToCorner, 225.0);
        LatLng northeastCorner = SphericalUtil.computeOffset(center, distanceFromCenterToCorner, 45.0);
        return new LatLngBounds(southwestCorner, northeastCorner);
    }

    public static String getUserUid() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        return auth.getUid();
    }

    public static DatabaseReference getDatabaseReference(String reference) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        return database.getReference(reference);
    }

    public static StorageReference getStorageReference(String reference) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        return storage.getReference(reference);
    }

    public static int convertDateToDayInterval(String endDate, String startDate) {
        int intervalDay = Integer.valueOf(endDate.substring(0, 2)) - Integer.valueOf(startDate.substring(0, 2)) + 1;
        return intervalDay;
    }

    public static String getUrl(double latitude, double longitude, String nearbyPlace) {
        StringBuilder googleNearbyPlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googleNearbyPlaceUrl.append("location="+latitude+","+longitude);
        googleNearbyPlaceUrl.append("&radius=5000");
        googleNearbyPlaceUrl.append("&rankby=prominence");
        googleNearbyPlaceUrl.append("&type="+nearbyPlace);
        googleNearbyPlaceUrl.append("&key="+"AIzaSyAUIVnQu5Pc9K48reQl0btDc2VrSrESzS8");

//        StringBuilder googleNearbyPlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=1500&type=restaurant&keyword=cruise&key=AIzaSyAUIVnQu5Pc9K48reQl0btDc2VrSrESzS8");

        return googleNearbyPlaceUrl.toString();
    }

}
