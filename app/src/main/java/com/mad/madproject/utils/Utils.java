package com.mad.madproject.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by limyandivicotrico on 5/16/18.
 */


/**
 * Handle intent and intent data
 * @param <Data> The data to be passed in to the intent
 */
public class Utils<Data> {

    /**
     * set the intent
     * @param context the intent to start
     * @param destination the destination class
     * @return the intent
     */
    public static Intent setIntent(Context context, Class destination) {

        Intent intent = new Intent(context, destination);
        context.startActivity(intent);

        return intent ;
    }

    /**
     * Set intent with extra
     * @param context the intent to start
     * @param destination the destination class
     * @param key the name of the intent
     * @param data the data to be passed in.
     * @return
     */
    public Intent setIntentExtra(Context context, Class destination, String key, Data data) {
        Intent intent = new Intent(context, destination);
        intent.putExtra(key, (Parcelable) data);
        context.startActivity(intent);

        return intent ;
    }
}
