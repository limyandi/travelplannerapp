package com.mad.madproject.forgetpassword;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by limyandivicotrico on 5/23/18.
 */

//The model, handles the logic, tells user what happens.
//public class ForgetPasswordModel {
//    FirebaseAuth mFirebaseAuth;
//    public ForgetPasswordModel(FirebaseAuth auth) {
//        mFirebaseAuth = auth;
//    }
//
//    private void firebaseForgetPasswordHandler(final ForgottenPasswordCallback callback) {
//        mFirebaseAuth.sendPasswordResetEmail(email)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            callback.successful();
//
//                        } else {
//                            mForgetPasswordView.forgetPasswordError();
//                        }
//
//                    }
//                });
//    }
//
//    public interface ForgottenPasswordCallback {
//        void successful();
//        void error();
//    }
//}
