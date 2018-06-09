package com.mad.madproject.chooseaccommodation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.mad.madproject.model.PlacesResponse;

public class ChooseAccommodationViewModel extends ViewModel {
    private LiveData<PlacesResponse> mNearbyPlacesResult;

    public ChooseAccommodationViewModel() {

    }

    public void onConfirmClicked(String type, String location, int radius) {

    }

    public LiveData<PlacesResponse> getNearbyPlacesResult() {
        return mNearbyPlacesResult;
    }
}
