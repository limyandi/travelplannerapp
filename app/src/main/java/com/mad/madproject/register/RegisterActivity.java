package com.mad.madproject.register;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
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
import com.mad.madproject.databinding.ActivitySignupBinding;
import com.mad.madproject.login.LoginActivity;
import com.mad.madproject.model.User;
import com.mad.madproject.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.sign_in_button)
    Button btnSignIn;
    @BindView(R.id.sign_up_button)
    Button btnSignUp;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    RegisterViewModel mRegisterViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySignupBinding binding = DataBindingUtil.setContentView(
                this, R.layout.activity_signup
        );

        ButterKnife.bind(this);

        mRegisterViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        binding.setViewModel(mRegisterViewModel);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                mRegisterViewModel.onRegisterClicked();
            }
        });

        observeRegister();
    }

    @OnClick(R.id.sign_in_button) void onSignInButtonClick() {
        Utils.setIntent(this, LoginActivity.class);
    }

    private void observeRegister() {
        mRegisterViewModel.getIsSuccessful().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isSuccessful) {
                if(isSuccessful) {
                    Utils.setIntent(RegisterActivity.this, MainActivity.class);
                    progressBar.setVisibility(View.GONE);
                }
                //TODO: Handle else.
                else {
                    Toast.makeText(RegisterActivity.this, "Failed Registering", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}
