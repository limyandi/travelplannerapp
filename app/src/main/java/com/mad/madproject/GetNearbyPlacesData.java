package com.mad.madproject;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mad.madproject.model.Itinerary;
import com.mad.madproject.model.Trip;
import com.mad.madproject.utils.Util;
import com.mad.madproject.utils.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by limyandivicotrico on 5/17/18.
 */

/**
 * This class handles the nearby places retrieval.
 */
public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

    String googlePlacesData;
    GoogleMap mMap;
    String url;

    @Override
    protected String doInBackground(Object... objects) {
        url = (String) objects[0];

        try {
            googlePlacesData = Utils.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String, String>> nearbyPlaceList = null;
        DataParser parser = new DataParser();
        nearbyPlaceList = parser.parse(s);
        getNearbyPlace(nearbyPlaceList);
    }

    private void getNearbyPlace(List<HashMap<String, String>> nearbyPlaceList) {
        //For now, only take the first one because we are only ranking them based on the rating.
        HashMap<String, String> googlePlace = nearbyPlaceList.get(0);

        String placeName = googlePlace.get("place_name");
        String vicinity = googlePlace.get("vicinity");
        double lat = Double.parseDouble(googlePlace.get("lat"));
        double lng = Double.parseDouble(googlePlace.get("lng"));

        LatLng latLng = new LatLng(lat, lng);

        //TODO: We have to do this several time, how?
//        Itinerary it = new Itinerary("10:00 A.M.", placeName, "A");

//        Trip trip = new Trip("10:00 A.M.");



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("Users");

//        usersRef.child(Util.getUserUid()).child("Day1").push().setValue(it);

        Log.d("Get", placeName);
        Log.d("Get", vicinity);
        Log.d("Get", latLng.latitude + ", " + latLng.longitude);
    }
}
