package com.mad.madproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by limyandivicotrico on 4/18/18.
 */

public class User implements Parcelable {
    private String mUsername;
    private String mEmail;
    private String mPassword;

    public User() {
    }

    public User(String username, String email, String password) {
        this.mUsername = username;
        this.mEmail = email;
        this.mPassword = password;
    }

    protected User(Parcel in) {
        mUsername = in.readString();
        mEmail = in.readString();
        mPassword = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        this.mUsername = username;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mUsername);
        parcel.writeString(mEmail);
        parcel.writeString(mPassword);
    }
}
