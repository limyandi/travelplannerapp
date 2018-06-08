package com.mad.madproject.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.mad.madproject.firebase.FirebaseDatabaseRepository;
import com.mad.madproject.model.User;
import com.mad.madproject.utils.Util;

public class NavHeaderViewModel extends ViewModel {
    private FirebaseDatabaseRepository mFirebaseDatabaseRepository = new FirebaseDatabaseRepository();

    private MutableLiveData<User> user;

    public NavHeaderViewModel() {
        user = new MutableLiveData<>();
    }

    public LiveData<User> getUser() {
        if(user.getValue() == null) {
            mFirebaseDatabaseRepository.getUser(Util.getUserUid(), new FirebaseDatabaseRepository.FirebaseUserCallback() {
                @Override
                public void onSuccess(User theUser) {
                    user.postValue(theUser);
                }
                @Override
                public void onFailure(String message) {
                    Log.d("MVVM", message);
                }
            });
        }
        return user;
    }
}
