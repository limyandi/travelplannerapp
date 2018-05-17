package com.mad.madproject.login;

import com.mad.madproject.base.BaseView;

/**
 * Created by limyandivicotrico on 5/16/18.
 */

public interface LoginView extends BaseView {
    void showValidationError(String message);
    void loginSuccess();
    void loginError();
    void setProgressVisibility(boolean visibility);
    void isLogin(boolean isLogin);
}
