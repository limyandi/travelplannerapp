package com.mad.madproject.utils;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.maps.android.SphericalUtil;
import com.mad.madproject.db.Firebase;

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

    public static DatabaseReference getUserDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("Users");
        return usersRef;
    }

    public static int convertDateToDayInterval(String endDate, String startDate) {
        int intervalDay = Integer.valueOf(endDate.substring(0, 2)) - Integer.valueOf(startDate.substring(0, 2)) + 1;
        return intervalDay;
    }

}
