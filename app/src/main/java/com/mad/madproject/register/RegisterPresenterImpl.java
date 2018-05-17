package com.mad.madproject.register;

/**
 * Created by limyandivicotrico on 5/16/18.
 */

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mad.madproject.model.User;

public class RegisterPresenterImpl implements RegisterPresenter {

    private RegisterView registerView;
    private FirebaseAuth auth;

    public RegisterPresenterImpl(FirebaseAuth auth) {
        this.auth = auth ;
    }

    @Override
    public void signUp(final String username, final String email, final String password) {
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ) {
            registerView.showValidationError();
        } else {
            registerView.setProgressVisibility(true);
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) registerView, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            registerView.setProgressVisibility(false);
                            if (!task.isSuccessful()) {
                                registerView.signUpError();
                            } else {
                                User user = new User(username, email, password);
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                //get Users branch.
                                final DatabaseReference ref = database.getReference("Users");
                                //TODO: Handle if auth null.
                                ref.child(auth.getUid()).setValue(user);
                                registerView.signUpSuccess();
                            }
                        }
                    });
        }
    }

    @Override
    public void attachView(RegisterView view) {
        registerView = view ;
    }

    @Override
    public void detachView() {
        registerView = null ;
    }
}
