package com.mad.madproject.main;

import android.arch.lifecycle.ViewModel;

import com.mad.madproject.firebase.FirebaseDatabaseRepository;
import com.mad.madproject.utils.Util;

/**
 * Settings View Model to handle deleting the trips.
 */
public class SettingsViewModel extends ViewModel {
    private FirebaseDatabaseRepository mFirebaseDatabaseRepository = new FirebaseDatabaseRepository();

    /**
     * Handle when on confirm is clicked, then delete the past trips from the firebase database.
     */
    public void onConfirmClicked() {
        mFirebaseDatabaseRepository.deletePastTrips(Util.getUserUid());
    }
}
