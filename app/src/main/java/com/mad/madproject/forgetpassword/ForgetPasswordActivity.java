package com.mad.madproject.forgetpassword;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.mad.madproject.R;
import com.mad.madproject.databinding.ActivityForgetPasswordBinding;
import com.mad.madproject.login.LoginActivity;
import com.mad.madproject.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPasswordActivity extends AppCompatActivity implements ForgetPasswordViewModel.ViewListener {

    ForgetPasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create the model using the view model provider .of (classname)
        //observe(livedata fields from the viewmodel)

        ActivityForgetPasswordBinding binding = DataBindingUtil.setContentView(
                this, R.layout.activity_forget_password
        );

        viewModel = new ForgetPasswordViewModel();

        viewModel.setViewListener(this);
        binding.setViewModel(viewModel);
    }

    @Override
    public void onEmailSentSuccess() {
        Utils.setIntent(this, LoginActivity.class);
    }

    @Override
    public void onBackSuccess() {
        Utils.setIntent(this, LoginActivity.class);
    }

    @Override
    public void onMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppTheme);
        builder.setTitle(title).setMessage(message).create().show();
    }
}
