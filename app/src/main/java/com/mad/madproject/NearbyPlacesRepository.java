package com.mad.madproject;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.mad.madproject.model.Place;
import com.mad.madproject.model.PlacesResponse;
import com.mad.madproject.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NearbyPlacesRepository {
    private NearbyPlacesService mNearbyPlacesService;
    //for a singleton.
    private static NearbyPlacesRepository mNearbyPlacesRepository;

    private NearbyPlacesRepository() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(NearbyPlacesService.baseUrl).
                addConverterFactory(new NullOnEmptyConverterFactory()).
                addConverterFactory(GsonConverterFactory.create()).
                build();

        //creating the service to be used.
        mNearbyPlacesService = retrofit.create(NearbyPlacesService.class);
    }

    /**
     * Singleton pattern.
     * @return the repository.
     */
    public synchronized static NearbyPlacesRepository getInstance() {
        if(mNearbyPlacesRepository == null) {
            mNearbyPlacesRepository = new NearbyPlacesRepository();
        }
        return mNearbyPlacesRepository;
    }


    public void getPlaces(String type, String location, int radius, final NearbyPlacesCallback callback) {
        NearbyPlacesService nearbyPlacesService = mNearbyPlacesService;

        nearbyPlacesService.getNearbyPlaces(type, location ,radius).enqueue(new Callback<PlacesResponse>() {
            @Override
            public void onResponse(Call<PlacesResponse> call, Response<PlacesResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<PlacesResponse> call, Throwable t) {
                //TODO: Can have better error handling.
                callback.onFailure(t.getMessage());
            }
        });


    }

    public interface NearbyPlacesCallback {
        void onSuccess(PlacesResponse placesResponse);
        void onFailure(String message);
    }

}
