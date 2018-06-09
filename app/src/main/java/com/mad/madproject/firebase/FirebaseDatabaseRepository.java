package com.mad.madproject.firebase;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mad.madproject.model.User;
import com.mad.madproject.utils.Util;

/**
 * The single source of truth for data for the database data retrieval and insert. (Repository).
 */
public class FirebaseDatabaseRepository {

    /**
     * Get user details
     * @param userUid the user firebase token id
     * @return the live data of user (to be observed).
     */
    public LiveData<User> getUserDetails(String userUid) {
        final MutableLiveData<User> user = new MutableLiveData<>();

        Util.getDatabaseReference("Users").child(userUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user.setValue(dataSnapshot.getValue(User.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                user.setValue(null);
            }
        });

        return user;
    }


}

