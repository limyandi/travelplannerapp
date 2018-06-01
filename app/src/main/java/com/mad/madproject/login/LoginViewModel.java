package com.mad.madproject.login;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mad.madproject.model.User;

/**
 * Created by limyandivicotrico on 5/24/18.
 */

public class LoginViewModel extends ViewModel {
    private MutableLiveData<String> email;
    private MutableLiveData<String> password;


    public LoginViewModel() {
        email = new MutableLiveData<>();
        password = new MutableLiveData<>();

    }

    public void setEmail(MutableLiveData<String> email) {
        this.email = email;
    }

    public void setPassword(MutableLiveData<String> password) {
        this.password = password;
    }

    public LiveData<String> getEmail() {
        return email;
    }

    public LiveData<String> getPassword() {
        return password;
    }

    public void onLoginClick() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email.getValue(), password.getValue())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()) {
                            Log.d("MVVM", "Not successful!");
                        } else {
                            Log.d("MVVM", "Successful!");
                        }
                    }
                });
    }
}
