package com.mad.madproject.register;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

import com.mad.madproject.firebase.FirebaseAuthenticationRepository;
import com.mad.madproject.validator.Validator;

public class RegisterViewModel extends ViewModel {

    private FirebaseAuthenticationRepository mFirebaseAuthenticationRepository = new FirebaseAuthenticationRepository();

    private MutableLiveData<Boolean> mIsSuccessful;

    public final ObservableField<String> username = new ObservableField<>();
    public final ObservableField<String> email = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();

    public final ObservableField<String> errorUsername = new ObservableField<>();
    public final ObservableField<String> errorEmail = new ObservableField<>();
    public final ObservableField<String> errorPassword = new ObservableField<>();

    public RegisterViewModel() {
        mIsSuccessful = new MutableLiveData<>();
    }

    /**
     * The utility function to be called when the register button is clicked.
     */
    public void onRegisterClicked() {
        if(inputIsValidated()) {
            mFirebaseAuthenticationRepository.register(username.get(), email.get(), password.get(), mIsSuccessful);
        } else {
            mIsSuccessful.postValue(false);
        }

    }

    private boolean inputIsValidated() {
        boolean isValid = true;

        if(username.get() == null || !Validator.isUsernameValid(username.get())) {
            errorUsername.set("Invalid username!");
            isValid = false;
        } else {
            errorUsername.set(null);
        }

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

    /**
     * Share the live data to observe it.
     * @return whether the register is successful or not.
     */
    public LiveData<Boolean> getIsSuccessful() {
        return mIsSuccessful;
    }
}
