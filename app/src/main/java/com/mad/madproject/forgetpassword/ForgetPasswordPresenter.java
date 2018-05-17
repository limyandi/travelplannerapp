package com.mad.madproject.forgetpassword;

import com.mad.madproject.base.BasePresenter;

/**
 * Created by DhytoDev on 3/7/17.
 */

public interface ForgetPasswordPresenter extends BasePresenter<ForgetPasswordView> {
    void requestForgetPassword(String email);
}