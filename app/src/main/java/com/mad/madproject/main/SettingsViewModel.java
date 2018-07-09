package com.mad.madproject.main;

import android.arch.lifecycle.ViewModel;

import com.mad.madproject.firebase.FirebaseDatabaseRepository;
import com.mad.madproject.utils.Util;

public class SettingsViewModel extends ViewModel {
    private FirebaseDatabaseRepository mFirebaseDatabaseRepository = new FirebaseDatabaseRepository();

    public void onConfirmClicked() {
        mFirebaseDatabaseRepository.deletePastTrips(Util.getUserUid());
    }
}
