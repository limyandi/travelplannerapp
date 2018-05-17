package com.mad.madproject.register;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mad.madproject.R;
import com.mad.madproject.activity.MainActivity;
import com.mad.madproject.login.LoginActivity;
import com.mad.madproject.model.User;
import com.mad.madproject.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    @BindView(R.id.username)
    EditText inputUsername;
    @BindView(R.id.email)
    EditText inputEmail;
    @BindView(R.id.password)
    EditText inputPassword;
    @BindView(R.id.sign_in_button)
    Button btnSignIn;
    @BindView(R.id.sign_up_button)
    Button btnSignUp;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private RegisterPresenter mRegisterPresenter;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        mRegisterPresenter = new RegisterPresenterImpl(auth);
        mRegisterPresenter.attachView(this);
    }

    @OnClick(R.id.sign_up_button) void onRegisterButtonClick() {
        mRegisterPresenter.signUp(inputUsername.getText().toString().trim(),
                inputEmail.getText().toString().trim(), inputPassword.getText().toString().trim());
    }

    @OnClick(R.id.sign_in_button) void onSignInButtonClick() {
        Utils.setIntent(this, LoginActivity.class);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showValidationError() {
        Utils.showMessage(this, "Check email and password");
    }

    @Override
    public void signUpSuccess() {
        Utils.setIntent(this, MainActivity.class);
    }

    @Override
    public void signUpError() {
        Utils.showMessage(this, "Failed signing up!");
    }

    @Override
    public void setProgressVisibility(boolean visibility) {
        if (visibility) progressBar.setVisibility(View.VISIBLE);
        else progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRegisterPresenter.detachView();
    }
}
