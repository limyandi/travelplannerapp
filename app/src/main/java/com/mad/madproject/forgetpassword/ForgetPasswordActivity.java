package com.mad.madproject.forgetpassword;

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
import com.mad.madproject.databinding.ActivityForgetPasswordBinding;
import com.mad.madproject.login.LoginActivity;
import com.mad.madproject.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The forget password utility page/activity.
 */
public class ForgetPasswordActivity extends AppCompatActivity {

    @BindView(R.id.btn_reset_password)
    Button btnReset;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.failed_forget_password)
    TextView mFailedForgetPasswordTv;

    ForgetPasswordViewModel mForgetPasswordViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create the view model using the view model provider .of (classname)
        //observe(livedata fields from the viewmodel)
        ActivityForgetPasswordBinding binding = DataBindingUtil.setContentView(
                this, R.layout.activity_forget_password
        );

        ButterKnife.bind(this);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFailedForgetPasswordTv.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                mForgetPasswordViewModel.onForgetPasswordClick();
            }
        });

        mForgetPasswordViewModel = ViewModelProviders.of(this).get(ForgetPasswordViewModel.class);

        binding.setForgetPasswordViewModel(mForgetPasswordViewModel);

        observeForgetPassword();
    }

    /**
     * Observe the successfulness of the forget password.
     */
    private void observeForgetPassword() {
        mForgetPasswordViewModel.getIsSuccessful().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isSuccessful) {
                if(isSuccessful) {
                    Utils.setIntent(ForgetPasswordActivity.this, LoginActivity.class);
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.GONE);
                    mFailedForgetPasswordTv.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * Handle when the back button is clicked.
     */
    @OnClick(R.id.btn_back) void onButtonBackClicked() {
        Utils.setIntent(this, LoginActivity.class);
    }

}
