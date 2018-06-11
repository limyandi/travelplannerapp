package com.mad.madproject.viewitinerary;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.mad.madproject.firebase.FirebaseDatabaseRepository;
import com.mad.madproject.model.Itineraries;

/**
 * TODO: Implement the view model for the view itinerary page. (For next version).
 * NOT USED. Too hard to implement, the data is everywhere hahahahaha.
 */
public class ViewItineraryViewModel extends ViewModel {
    private FirebaseDatabaseRepository mFirebaseDatabaseRepository = new FirebaseDatabaseRepository();
    private LiveData<Itineraries> mItinerariesLiveData;

    public ViewItineraryViewModel() {
//        mItinerariesLiveData = mFirebaseDatabaseRepository.getItineraryDetails(itineraryPreviewId);
    }

    /**
     * call the repository to delete certain itinerary.
     * @param itineraryPreviewId the associated itinerary preview id.
     */
    public void deleteItineraryPreviewData(String itineraryPreviewId) {
        mFirebaseDatabaseRepository.deleteItinerary(itineraryPreviewId);
    }

    public LiveData<Itineraries> getItinerariesLiveData() {
        return mItinerariesLiveData;
    }
}
