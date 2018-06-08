package com.mad.madproject.login;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mad.madproject.R;
import com.mad.madproject.databinding.ActivityLoginBinding;
import com.mad.madproject.forgetpassword.ForgetPasswordActivity;
import com.mad.madproject.main.MainActivity;
import com.mad.madproject.register.RegisterActivity;
import com.mad.madproject.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The LoginActivity handles the view presented to user to login.
 */
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    LoginViewModel mLoginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //bind the layout with data binding.
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        ButterKnife.bind(this);

        //create the view model.
        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding.setLoginViewModel(mLoginViewModel);

        //set the click listener.
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView) findViewById(R.id.failed_identity)).setVisibility(View.INVISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);
                mLoginViewModel.onLoginClick();
            }
        });

        authenticationHandling();
        observeLogin();
    }

    /**
     * Create an observer lifecycle for the live data is successful.
     */
    private void observeLogin() {
        mLoginViewModel.getIsSuccessful().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isSuccessful) {
                if(isSuccessful) {
                    Utils.setIntent(LoginActivity.this, MainActivity.class);
                    mProgressBar.setVisibility(View.GONE);
                }
                else {
                    mProgressBar.setVisibility(View.GONE);
                    ((TextView) findViewById(R.id.failed_identity)).setVisibility(View.VISIBLE);
                }

            }
        });
    }

    @OnClick(R.id.btn_signup) void onSignUpButtonClick() {
        Utils.setIntent(this, RegisterActivity.class);
    }

    @OnClick(R.id.btn_reset_password) void onResetPasswordButtonClick() {
        Utils.setIntent(this, ForgetPasswordActivity.class);
    }

    /**
     * Handle if the user currently has an active session in our application.
     */
    private void authenticationHandling() {
        //handle the authentication handler.
        if(mLoginViewModel.getIsLoggedIn()) {
            Utils.setIntent(LoginActivity.this, MainActivity.class);
            finish();
        }
    }
}