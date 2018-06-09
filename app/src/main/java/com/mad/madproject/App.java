package com.mad.madproject;

import android.app.Application;

import com.bumptech.glide.request.target.ViewTarget;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by limyandivicotrico on 5/21/18.
 */

/**
 * This class enable the access to glide older version API. And also handle ApiService Network Error.
 */
public class App extends Application {
    private ApiService mApiService;

    @Override
    public void onCreate() {

        //Handle attaching picture to glide API.
        super.onCreate();
        ViewTarget.setTagId(R.id.glide_tag);
    }

    /**
     * Get an instance of api service
     * @return apiservice
     */
    public ApiService getApiService() {
        if (mApiService == null) {
            mApiService = provideRetrofit(ApiService.baseUrl).create(ApiService.class);
        }
        return mApiService;
    }

    /**
     * Provide the retrofit
     * @param url the url to passed in to.
     * @return the Retrofit instance
     */
    private Retrofit provideRetrofit(String url) {
        return new Retrofit.Builder().baseUrl(url).client(provideOkHttpClient()).addConverterFactory(GsonConverterFactory.create(new Gson())).build();
    }

    /**
     * Handle the usage of okhttpclient by retrofit
     * @return an instance of OkHttpClient.
     */
    private okhttp3.OkHttpClient provideOkHttpClient() {
        okhttp3.OkHttpClient.Builder okhttpClientBuilder = new okhttp3.OkHttpClient.Builder();
        okhttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        okhttpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
        okhttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS);
        return okhttpClientBuilder.build();
    }

}
