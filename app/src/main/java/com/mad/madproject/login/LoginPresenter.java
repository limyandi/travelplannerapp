package com.mad.madproject.login;

import android.app.Activity;
import android.content.Context;

import com.mad.madproject.base.BasePresenter;

/**
 * Created by DhytoDev on 3/7/17.
 */

public interface LoginPresenter extends BasePresenter<LoginView> {
    void login(String email, String password);
    void checkLogin();
}