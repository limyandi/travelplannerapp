package com.mad.madproject.register;

import com.mad.madproject.base.BaseView;

/**
 * Created by limyandivicotrico on 5/16/18.
 */

public interface RegisterView extends BaseView {
    void showValidationError();
    void signUpSuccess();
    void signUpError();
    void setProgressVisibility(boolean visibility);
}
