package com.mad.madproject.retrofit;

import com.mad.madproject.model.PlacesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by limyandivicotrico on 5/30/18.
 */

/**
 * The API Services to get the nearby places.
 */
public interface NearbyPlacesService {
    //URL FOR ACCESSING GOOGLE MAPS API.
    String baseUrl = "https://maps.googleapis.com/maps/";

    //TODO: Put in your key here
    @GET("api/place/nearbysearch/json?rankby=prominence&language=en&key=YOURKEY")
    Call<PlacesResponse> getNearbyPlaces(@Query("type") String type, @Query("location") String location, @Query("radius") int radius);
}