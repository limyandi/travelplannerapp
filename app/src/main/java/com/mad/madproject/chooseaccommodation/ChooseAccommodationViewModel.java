package com.mad.madproject.chooseaccommodation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.mad.madproject.model.PlacesResponse;

/**
 * Currently not used but should be used in the next version to make sure that the activity (main thread) just observe data and not doing everything.
 */
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
