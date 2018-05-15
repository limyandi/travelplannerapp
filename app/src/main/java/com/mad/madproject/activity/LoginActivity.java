package com.mad.madproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mad.madproject.R;
import com.mad.madproject.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Firebase mAuth instance
        mAuth = FirebaseAuth.getInstance();
        Intent mainActivityIntent = new Intent(LoginActivity.this, MainActivity.class);

        //keep user session.
        if (mAuth.getCurrentUser() != null) {
            Log.d(Constant.LOG_TAG, mAuth.getUid());
            startActivity(mainActivityIntent);
            finish();
        }

        //set the view now
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        //Get Firebase mAuth instance
        mAuth = FirebaseAuth.getInstance();

        mBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        mBtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
            }
        });

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mInputEmail.getText().toString();
                final String password = mInputPassword.getText().toString();

                FirebaseLoginHandler(email, password);
            }
        });
    }

    private void FirebaseLoginHandler(String email, final String password) {
        //check if email is empty or null.
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        //check if password is empty or null
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        mProgressBar.setVisibility(View.VISIBLE);

        //authenticate user.
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the mAuth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        mProgressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            // Handle Error.
                            if (password.length() < 6) {
                                mInputPassword.setError(getString(R.string.minimum_password));
                            } else {
                                Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}