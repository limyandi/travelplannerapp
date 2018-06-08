package com.mad.madproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by limyandivicotrico on 4/18/18.
 */


/**
 * This class used parcelable instead of Serializable just to compare which one is better.
 * https://www.3pillarglobal.com/insights/parcelable-vs-java-serialization-in-android-app-development (Read on Parcelable vs Serializable)
 * This class used parcelable, could have used serializable as well, but is good for future development
 *
 * This class defines the user object with a username, email and password.
 */
public class User implements Parcelable {
    private String mUsername;
    private String mEmail;

    /**
     * Default Constructor
     */
    public User() {
    }

    /**
     * Alternate constructor that defines the user object that contains a username, email, and password
     * @param username define the username of the user.
     * @param email define the email of the user.
     */
    public User(String username, String email) {
        this.mUsername = username;
        this.mEmail = email;
    }

    /**
     * The protected constructor for the parcelable class.
     * @param in the parcel instances.
     */
    protected User(Parcel in) {
        mUsername = in.readString();
        mEmail = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mUsername);
        parcel.writeString(mEmail);
    }
}
