package com.mad.madproject.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.mad.madproject.firebase.FirebaseDatabaseRepository;
import com.mad.madproject.model.ItineraryPreview;
import com.mad.madproject.utils.Util;

import java.util.ArrayList;

/**
 * Not working.
 */
public class MyTripFilterViewModel extends ViewModel {
    private FirebaseDatabaseRepository mFirebaseDatabaseRepository = new FirebaseDatabaseRepository();
    private LiveData<ArrayList<ItineraryPreview>> mItineraryPreviews;

    public MyTripFilterViewModel() {
        mItineraryPreviews = mFirebaseDatabaseRepository.getAllItineraryPreviews(Util.getUserUid());
    }

    public void onMoveToPresent() {
        mItineraryPreviews = mFirebaseDatabaseRepository.getUpcomingItineraryPreviews(Util.getUserUid());
    }

    public void onMoveToPast() {
        mItineraryPreviews = mFirebaseDatabaseRepository.getPastItineraryPreviews(Util.getUserUid());
    }

    public void onMoveToAll() {
        mItineraryPreviews = mFirebaseDatabaseRepository.getAllItineraryPreviews(Util.getUserUid());
    }

    public LiveData<ArrayList<ItineraryPreview>> getItineraryPreviews() {
        return mItineraryPreviews;
    }
}
