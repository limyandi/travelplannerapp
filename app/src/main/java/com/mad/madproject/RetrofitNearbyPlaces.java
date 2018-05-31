package com.mad.madproject;

import com.mad.madproject.model.PlacesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by limyandivicotrico on 5/30/18.
 */

public interface RetrofitNearbyPlaces {

    @GET("api/place/nearbysearch/json?key=AIzaSyAUIVnQu5Pc9K48reQl0btDc2VrSrESzS8&rankby=prominence")
    Call<PlacesResponse> getNearbyPlaces(@Query("type") String type, @Query("location") String location, @Query("radius") int radius);

}