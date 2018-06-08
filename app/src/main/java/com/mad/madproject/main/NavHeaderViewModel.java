package com.mad.madproject.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mad.madproject.model.User;
import com.mad.madproject.utils.Util;

public class NavHeaderViewModel extends ViewModel {
    private MutableLiveData<User> user;

    public NavHeaderViewModel() {
        user = new MutableLiveData<>();
    }

    public LiveData<User> getUser() {
        if(user.getValue() == null) {
            Util.getDatabaseReference("Users").child(Util.getUserUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    user.postValue(dataSnapshot.getValue(User.class));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        return user;
    }
}
