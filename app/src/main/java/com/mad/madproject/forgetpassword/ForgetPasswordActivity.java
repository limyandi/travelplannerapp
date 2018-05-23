package com.mad.madproject.forgetpassword;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.mad.madproject.R;
import com.mad.madproject.login.LoginActivity;
import com.mad.madproject.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPasswordActivity extends AppCompatActivity implements ForgetPasswordView {

    @BindView(R.id.email)
    EditText inputEmail;
    @BindView(R.id.btn_reset_password)
    Button btnReset;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private FirebaseAuth auth;
    private ForgetPasswordPresenter mForgetPasswordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        //create the model using the view model provider .of (classname)

        //observe(livedata fields from the viewmodel)

        //bind butterknife after setContentView.
        ButterKnife.bind(this);

        auth = FirebaseAuth.getInstance();
        mForgetPasswordPresenter = new ForgetPasswordPresenterImpl(auth);

        mForgetPasswordPresenter.attachView(this);
    }

    @OnClick(R.id.btn_reset_password) void onResetButtonClick() {
        String emailText = inputEmail.getText().toString().trim();
        mForgetPasswordPresenter.requestForgetPassword(emailText);
    }

    @OnClick(R.id.btn_back) void onBackButtonClicked() {
        finish();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showValidationError(String message) {
        Utils.showMessage(this, message);
    }

    @Override
    public void forgetPasswordSuccess() {
        //TODO: change this, we should not use toast later.
        Utils.showMessage(this, "Forget Password Success! Check your email!");
        Utils.setIntent(this, LoginActivity.class);
    }

    @Override
    public void forgetPasswordError() {
        Utils.showMessage(this, "Forget password error!");
    }

    @Override
    public void setProgressVisibility(boolean visibility) {
        if (visibility) progressBar.setVisibility(View.VISIBLE);
        else progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mForgetPasswordPresenter.detachView();
    }
}
