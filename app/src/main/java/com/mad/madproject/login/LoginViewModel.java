package com.mad.madproject.login;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by limyandivicotrico on 5/24/18.
 */

public class LoginViewModel extends ViewModel {

    private MutableLiveData<Boolean> mIsSuccessful;

    //should be declared final becasue bindings only detect changes in the field's value, not of the field itself.
    public final ObservableField<String> email = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();


    public LoginViewModel() {
        mIsSuccessful = new MutableLiveData<>();
    }

    private void loginSuccessful() {
        Log.d("MVVM", "Successful!");
        mIsSuccessful.postValue(true);
    }

    public void onLoginClick() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email.get(), password.get())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()) {
                            //TODO: Handle error.
                            Log.d("MVVM", "Not successful!");
                        } else {
                            loginSuccessful();
                        }
                    }
                });
    }

    //TODO: Might not be right. the logic in here does not feel right.
    public boolean getIsLoggedIn() {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            return true;
        }
        return false;
    }

    public LiveData<Boolean> getIsSuccessful() {
        return mIsSuccessful;
    }
}

