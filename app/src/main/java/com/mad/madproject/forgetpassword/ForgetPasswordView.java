package com.mad.madproject.forgetpassword;

import com.mad.madproject.base.BaseView;

/**
 * Created by limyandivicotrico on 5/16/18.
 */

public interface ForgetPasswordView extends BaseView {
    void showValidationError(String message);
    void forgetPasswordSuccess();
    void forgetPasswordError();
    void setProgressVisibility(boolean visibility);
}
