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
import com.mad.madproject.FirebaseAuthenticationRepository;
import com.mad.madproject.validator.Validator;

/**
 * Created by limyandivicotrico on 5/24/18.
 */

public class LoginViewModel extends ViewModel {

    private FirebaseAuthenticationRepository mFirebaseAuthenticationRepository = new FirebaseAuthenticationRepository();
    //create the live data to observe the value whether the login is successful.
    private MutableLiveData<Boolean> mIsSuccessful;

    //should be declared final becasue bindings only detect changes in the field's value, not of the field itself.
    public final ObservableField<String> email = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();

    public final ObservableField<String> errorEmail = new ObservableField<>();
    public final ObservableField<String> errorPassword = new ObservableField<>();


    public LoginViewModel() {
        mIsSuccessful = new MutableLiveData<>();
    }

    private void loginSuccessful() {
        Log.d("MVVM", "Successful!");
        mIsSuccessful.postValue(true);
    }

    /**
     * Firebase Login Utility. TODO: Create repository for this.
     */
    public void onLoginClick() {
        if(validateInputs()) {
            mFirebaseAuthenticationRepository.login(email.get(), password.get(), mIsSuccessful);
        } else {
            mIsSuccessful.postValue(false);
        }
    }

    private boolean validateInputs() {
        boolean isValid = true;

        if (email.get() == null || !Validator.isEmailValid(email.get())) {

            errorEmail.set("Invalid Email");
            isValid = false;

        } else {
            errorEmail.set(null);
        }

        if (password.get() == null || password.get().length() < 4) {
            errorPassword.set("Password too short");

            isValid = false;

        } else {
            errorPassword.set(null);
        }

        return isValid;
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

