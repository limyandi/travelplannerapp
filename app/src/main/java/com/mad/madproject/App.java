package com.mad.madproject;

import android.app.Application;

import com.bumptech.glide.request.target.ViewTarget;

/**
 * Created by limyandivicotrico on 5/21/18.
 */

public class App extends Application {
    @Override
    public void onCreate() {

        //Handle attaching picture to glide API.
        super.onCreate();
        ViewTarget.setTagId(R.id.glide_tag);
    }
}
