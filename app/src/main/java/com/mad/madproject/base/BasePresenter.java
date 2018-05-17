package com.mad.madproject.base;

/**
 * Created by limyandivicotrico on 5/16/18.
 */

public interface BasePresenter<V extends BaseView> {
    void attachView(V view);
    void detachView();
}

