package com.mad.madproject.forgetpassword;

/**
 * Created by limyandivicotrico on 5/16/18.
 */

import android.app.Activity;
import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.android.databinding.library.baseAdapters.BR;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mad.madproject.R;

//ViewModel
public class ForgetPasswordViewModel extends BaseObservable {

    //LiveData.
    private String mEmail;
    private ViewListener mListener;

    public ForgetPasswordViewModel() {
        mEmail = "";
    }

    public void setViewListener(ViewListener mListener) {
        this.mListener = mListener;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
        notifyPropertyChanged(R.id.email);
    }

    public void onBackSuccess() {
        mListener.onBackSuccess();
    }

    public void onResetClick() {
        FirebaseAuth.getInstance().sendPasswordResetEmail(mEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mListener.onEmailSentSuccess();
                        } else {
                            mListener.onMessage("Failed!", "Enter a valid email!");
                        }

                    }
                });
    }

    public interface ViewListener {

        void onEmailSentSuccess();

        void onBackSuccess();

        void onMessage(String title, String message);
    }

}
