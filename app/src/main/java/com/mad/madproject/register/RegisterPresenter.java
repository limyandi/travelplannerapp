package com.mad.madproject.register;

import com.mad.madproject.base.BasePresenter;

/**
 * Created by limyandivicotrico on 5/16/18.
 */

public interface RegisterPresenter extends BasePresenter<RegisterView> {
    void signUp(String username, String email, String password);
}
