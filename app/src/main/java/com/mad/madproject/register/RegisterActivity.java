package com.mad.madproject.register;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mad.madproject.R;
import com.mad.madproject.main.MainActivity;
import com.mad.madproject.databinding.ActivitySignupBinding;
import com.mad.madproject.login.LoginActivity;
import com.mad.madproject.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.sign_up_button)
    Button btnSignUp;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.register_failed_text)
    TextView mRegisterUnsuccessfulTv;


    RegisterViewModel mRegisterViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySignupBinding binding = DataBindingUtil.setContentView(
                this, R.layout.activity_signup
        );

        ButterKnife.bind(this);

        mRegisterViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        binding.setRegisterViewModel(mRegisterViewModel);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRegisterUnsuccessfulTv.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                mRegisterViewModel.onRegisterClicked();
            }
        });

        observeRegister();
    }

    @OnClick(R.id.sign_in_button) void onSignInButtonClick() {
        Utils.setIntent(this, LoginActivity.class);
    }

    /**
     * Observe the register whether it is successful or not.
     */
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
                    progressBar.setVisibility(View.GONE);
                    mRegisterUnsuccessfulTv.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
