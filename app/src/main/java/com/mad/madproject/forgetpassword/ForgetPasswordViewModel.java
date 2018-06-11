package com.mad.madproject.forgetpassword;

/**
 * Created by limyandivicotrico on 5/16/18.
 */

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

import com.mad.madproject.firebase.FirebaseAuthenticationRepository;
import com.mad.madproject.validator.Validator;

/**
 * View Model to handle the logic for the forget password activity.
 */
public class ForgetPasswordViewModel extends ViewModel {

    private FirebaseAuthenticationRepository mFirebaseAuthenticationRepository = new FirebaseAuthenticationRepository();

    private MutableLiveData<Boolean> mIsSuccessful;

    public final ObservableField<String> email = new ObservableField<>();

    public final ObservableField<String> errorEmail = new ObservableField<>();


    public ForgetPasswordViewModel() {
        mIsSuccessful = new MutableLiveData<>();
    }

    /**
     * Handle the call when the forget password button is clicked
     */
    public void onForgetPasswordClick() {
        if(inputIsValidated()) {
            mFirebaseAuthenticationRepository.forgetPassword(email.get(), mIsSuccessful);
        }
        else {
            mIsSuccessful.postValue(false);
        }
    }

    /**
     * Function to validate the input
     * @return
     */
    private boolean inputIsValidated() {
        boolean isValid = true;

        if (email.get() == null || !Validator.isEmailValid(email.get())) {

            errorEmail.set("Invalid Email");
            isValid = false;

        } else {
            errorEmail.set(null);
        }

        return isValid;
    }

    /**
     * Share the live data to check whether the forget password utility is successful
     * @return the successful boolean.
     */
    public LiveData<Boolean> getIsSuccessful() {
        return mIsSuccessful;
    }

}
