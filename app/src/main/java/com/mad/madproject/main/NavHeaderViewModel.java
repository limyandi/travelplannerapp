package com.mad.madproject.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.mad.madproject.firebase.FirebaseDatabaseRepository;
import com.mad.madproject.model.User;
import com.mad.madproject.utils.Util;

/**
 * HeaderViewModel to get the user data.
 */
public class NavHeaderViewModel extends ViewModel {
    private FirebaseDatabaseRepository mFirebaseDatabaseRepository = new FirebaseDatabaseRepository();

    private LiveData<User> user;

    public NavHeaderViewModel() {
        //TODO: Passing the user id here might not be right?
        user = mFirebaseDatabaseRepository.getUserDetails(Util.getUserUid());
    }

    /**
     * Get the live data of user to be observed
     * @return live data of the user.
     */
    public LiveData<User> getUser() {
        return user;
    }
}
