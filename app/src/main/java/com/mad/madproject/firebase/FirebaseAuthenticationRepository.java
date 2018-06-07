package com.mad.madproject.firebase;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mad.madproject.model.User;

public class FirebaseAuthenticationRepository {


    public void login(String email, String password, final MutableLiveData<Boolean> isSuccessful) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()) {
                            isSuccessful.postValue(false);
                        } else {
                            isSuccessful.postValue(true);
                        }
                    }
                });
    }

    public void register(final String username, final String email, String password, final MutableLiveData<Boolean> isSuccessful) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()) {
                            Log.d("MVVM", "Not successful!!");
                            isSuccessful.postValue(false);
                        }
                        else {
                            User user = new User(username, email);
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            //get Users branch.
                            final DatabaseReference ref = database.getReference("Users");
                            //TODO: Handle if auth null.
                            ref.child(FirebaseAuth.getInstance().getUid()).setValue(user);
                            isSuccessful.postValue(true);
                        }
                    }


                });
    }

    public void forgetPassword(String email, final MutableLiveData<Boolean> isSuccessful) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Log.d("MVVM", "Not Successful!");
                            isSuccessful.postValue(false);
                        } else {
                            Log.d("MVVM", "Successful!");
                            isSuccessful.postValue(true);
                        }
                    }
                });
    }


}
