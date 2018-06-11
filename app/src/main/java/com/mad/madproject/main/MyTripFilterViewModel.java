package com.mad.madproject.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.mad.madproject.firebase.FirebaseDatabaseRepository;
import com.mad.madproject.model.ItineraryPreview;
import com.mad.madproject.utils.Util;

import java.util.ArrayList;

/**
 * TripFilterViewModel is the view model for the filtering trip so
 * that user can see different itinerary whether it is all of the itinerary
 * they have, or all the past one, or all the upcoming one. The ViewModel allows
 * fragment to observe the data so that the responsibility does not all fall to the activity (Separation of concerns).
 */
public class MyTripFilterViewModel extends ViewModel {
    private FirebaseDatabaseRepository mFirebaseDatabaseRepository = new FirebaseDatabaseRepository();
    private LiveData<ArrayList<ItineraryPreview>> mItineraryPreviews;

    public MyTripFilterViewModel() {
        mItineraryPreviews = mFirebaseDatabaseRepository.getAllItineraryPreviews(Util.getUserUid());
    }

    /**
     * Call method from firebase when move to present to get only upcoming itinerary.
     */
    public void onMoveToPresent() {
        mItineraryPreviews = mFirebaseDatabaseRepository.getUpcomingItineraryPreviews(Util.getUserUid());
    }

    /**
     * Call method from firebase when move to past to get only past itinerary.
     */
    public void onMoveToPast() {
        mItineraryPreviews = mFirebaseDatabaseRepository.getPastItineraryPreviews(Util.getUserUid());
    }

    /**
     * Get all itinerary.
     */
    public void onMoveToAll() {
        mItineraryPreviews = mFirebaseDatabaseRepository.getAllItineraryPreviews(Util.getUserUid());
    }

    /**
     * Observe the itinerary previews data
     * @return live data of array list of itinerary previews.
     */
    public LiveData<ArrayList<ItineraryPreview>> getItineraryPreviews() {
        return mItineraryPreviews;
    }
}
