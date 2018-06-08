package com.mad.madproject.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mad.madproject.model.User;
import com.mad.madproject.utils.Util;

public class FirebaseDatabaseRepository {
    public void getUser(String userUid, final FirebaseUserCallback callback) {

        Util.getDatabaseReference("Users").child(userUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callback.onSuccess(dataSnapshot.getValue(User.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError.getMessage());
            }
        });
    }

    public interface FirebaseUserCallback {
        void onSuccess(User user);
        void onFailure(String message);
    }
}

