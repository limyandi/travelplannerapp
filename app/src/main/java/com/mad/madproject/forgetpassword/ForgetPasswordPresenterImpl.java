package com.mad.madproject.forgetpassword;

/**
 * Created by limyandivicotrico on 5/16/18.
 */

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

//ViewModel
public class ForgetPasswordPresenterImpl implements ForgetPasswordPresenter {
    private FirebaseAuth auth ;
    private ForgetPasswordView mForgetPasswordView;

    //LiveData.

    public ForgetPasswordPresenterImpl(FirebaseAuth auth) {
        this.auth = auth;
    }

    @Override
    public void requestForgetPassword(String email) {
        if (TextUtils.isEmpty(email)) {
            mForgetPasswordView.showValidationError("Please entered your registered id");
        } else {
            mForgetPasswordView.setProgressVisibility(true);
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                mForgetPasswordView.forgetPasswordSuccess();
                            } else {
                                mForgetPasswordView.forgetPasswordError();
                            }

                        }
                    });
        }
    }

    @Override
    public void attachView(ForgetPasswordView view) {
        mForgetPasswordView = view ;
    }

    @Override
    public void detachView() {
        mForgetPasswordView = null ;
    }
}
