package com.mad.madproject.model;

import java.io.Serializable;

/**
 * Created by limyandivicotrico on 4/18/18.
 */

public class User implements Serializable {
    private String mUid;
    private String mEmail;
    private String mUsername;

    public User() {
    }

    public User(String uid, String username, String email) {
        this.mUid = uid;
        this.mEmail = email;
        this.mUsername = username;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        this.mUid = uid;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        this.mUsername = username;
    }

}
