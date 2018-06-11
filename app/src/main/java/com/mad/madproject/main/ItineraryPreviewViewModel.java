package com.mad.madproject.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.mad.madproject.firebase.FirebaseDatabaseRepository;
import com.mad.madproject.model.ItineraryPreview;
import com.mad.madproject.utils.Util;

import java.util.ArrayList;

/**
 * View Model for the itinerary preview.
 */
public class ItineraryPreviewViewModel extends ViewModel {
    private FirebaseDatabaseRepository mFirebaseDatabaseRepository = new FirebaseDatabaseRepository();
    private LiveData<ArrayList<ItineraryPreview>> mItineraryPreviews;

    public ItineraryPreviewViewModel() {
        mItineraryPreviews = mFirebaseDatabaseRepository.getUpcomingItineraryPreviews(Util.getUserUid());
    }

    /**
     * Method for the activity/fragment to observe the itinerarypreview data from the database.
     * @return live data of an array list of itinerary previews.
     */
    public LiveData<ArrayList<ItineraryPreview>> getItineraryPreviews() {
        return mItineraryPreviews;
    }
}
