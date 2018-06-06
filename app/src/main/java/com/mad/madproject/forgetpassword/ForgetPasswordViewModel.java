package com.mad.madproject.forgetpassword;

/**
 * Created by limyandivicotrico on 5/16/18.
 */

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
import com.mad.madproject.validator.Validator;


//ViewModel
public class ForgetPasswordViewModel extends ViewModel {

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
            FirebaseAuth.getInstance().sendPasswordResetEmail(email.get())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                Log.d("MVVM", "Not Successful!");
                                mIsSuccessful.postValue(false);
                            } else {
                                Log.d("MVVM", "Successful!");
                                mIsSuccessful.postValue(true);
                            }
                        }
                    });
        }
        else {
            mIsSuccessful.postValue(false);
        }
    }

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
