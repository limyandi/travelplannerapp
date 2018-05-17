package com.mad.madproject.login;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.mad.madproject.R;
import com.mad.madproject.activity.ForgetPasswordActivity;
import com.mad.madproject.activity.MainActivity;
import com.mad.madproject.register.RegisterActivity;
import com.mad.madproject.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.email)
    EditText mInputEmail;
    @BindView(R.id.password)
    EditText mInputPassword;
    @BindView(R.id.btn_signup)
    Button mBtnSignup;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.btn_reset_password)
    Button mBtnReset;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    private FirebaseAuth mAuth;
    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //Get Firebase mAuth instance
        mAuth = FirebaseAuth.getInstance();
        mLoginPresenter = new LoginPresenterImpl(mAuth);

        mLoginPresenter.attachView(this);
        mLoginPresenter.checkLogin();
    }

    @OnClick(R.id.btn_login) void onLoginButtonClick() {
        String email_text = mInputEmail.getText().toString().trim();
        String password_text = mInputPassword.getText().toString().trim();
        mLoginPresenter.login(email_text, password_text);
    }

    @OnClick(R.id.btn_signup) void onSignUpButtonClick() {
        Utils.setIntent(this, RegisterActivity.class);
    }

    @OnClick(R.id.btn_reset_password) void onResetPasswordButtonClick() {
        Utils.setIntent(this, ForgetPasswordActivity.class);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void loginSuccess() {
        Utils.setIntent(this, MainActivity.class);
    }

    @Override
    public void loginError() {
        //TODO: Handle error here. (Instead of showing toast, show the default error for a textview?).
        Utils.showMessage(this, "Login Error ! ");
    }

    @Override
    public void showValidationError(String message) {
        //TODO: Handle validation.
        Utils.showMessage(this, message);
    }

    @Override
    public void isLogin(boolean isLogin) {
        if (isLogin) {
            Utils.setIntent(this, MainActivity.class);
            finish();
        }
    }

    @Override
    public void setProgressVisibility(boolean visibility) {
        if (visibility)
            mProgressBar.setVisibility(View.VISIBLE);
        else
            mProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.detachView();
    }
}