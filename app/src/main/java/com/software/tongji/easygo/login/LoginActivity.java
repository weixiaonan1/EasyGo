package com.software.tongji.easygo.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dd.processbutton.FlatButton;
import com.mukesh.OtpView;
import com.software.tongji.easygo.R;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginView{

    private final LoginPresenter mLoginPresenter = new LoginPresenter();

    @BindView(R.id.signup)
    TextView mSignUp;
    @BindView(R.id.login)
    TextView mLogin;
    @BindView(R.id.back_to_login)
    TextView mBackToLogin;

    @BindView(R.id.signup_layout)
    LinearLayout mSignUpLayout;
    @BindView(R.id.login_layout)
    LinearLayout mLoginLayout;
    @BindView(R.id.forgot_password_layout)
    LinearLayout mForgotPasswordLayout;
    @BindView(R.id.reset_code_layout)
    LinearLayout mResetCodeLayout;
    @BindView(R.id.new_password_layout)
    LinearLayout mNewPasswordLayout;

    @BindView(R.id.input_email_login)
    EditText mEmailLogin;
    @BindView(R.id.input_pass_login)
    EditText mPassLogin;
    @BindView(R.id.input_email_signup)
    EditText mEmailSignUp;
    @BindView(R.id.input_pass_signup)
    EditText mPassSignUp;
    @BindView(R.id.input_confirm_pass_signup)
    EditText mConfirmPassSignUp;
    @BindView(R.id.input_username)
    EditText mUserName;

    @BindView(R.id.input_email_forgot_password)
    EditText mEmailForgotPassword;
    @BindView(R.id.input_pass_reset)
    EditText mNewPasswordReset;
    @BindView(R.id.input_confirm_pass_reset)
    EditText mConfirmNewPasswordReset;

    @BindView(R.id.ok_login)
    FlatButton mOkLogin;
    @BindView(R.id.ok_signup)
    FlatButton mOkSignUp;
    @BindView(R.id.ok_submit_pass_reset)
    FlatButton mOkSubmitReset;
    @BindView(R.id.ok_confirm_pass_reset)
    FlatButton mOkConfirmReset;

    @BindView(R.id.forgot_password)
    TextView mForgotPasswordText;
    @BindView(R.id.code_sent_alert)
    TextView mCodeSentAlert;
    @BindView(R.id.resend_code)
    TextView mResendCodeText;

    @BindView(R.id.reset_code)
    OtpView mResetCode;

    private MaterialDialog mDialog;
    private Handler mHandler;
    private String mOptCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginPresenter.bind(this);
        ButterKnife.bind(this);

        mHandler = new Handler(Looper.getMainLooper());

        //Open SignUp
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignUp.setVisibility(View.GONE);
                mForgotPasswordText.setVisibility(View.GONE);
                mBackToLogin.setVisibility(View.GONE);
                mLogin.setVisibility(View.VISIBLE);
                mLoginPresenter.signUp();
            }
        });

        // Open login
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignUp.setVisibility(View.VISIBLE);
                mForgotPasswordText.setVisibility(View.VISIBLE);
                mBackToLogin.setVisibility(View.GONE);
                mLogin.setVisibility(View.GONE);
                mLoginPresenter.login();
            }
        });

        // Call login
        mOkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailLogin.getText().toString();
                String password = mPassLogin.getText().toString();
                mLoginPresenter.okLogin(email, password, mHandler);
            }
        });

        //Call signup
        mOkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailSignUp.getText().toString();
                String password = mPassSignUp.getText().toString();
                String confirmPassword = mConfirmPassSignUp.getText().toString();
                String username = mUserName.getText().toString();
                if (validateEmail(email)) {
                    if (validatePassword(password)) {
                        if (password.equals(confirmPassword)) {
                            mLoginPresenter.okSignUp(username, email, password, mHandler);
                        } else {
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                                            R.string.passwords_check, Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }
                }
            }
        });

        //open forgot password
        mForgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLogin.setVisibility(View.GONE);
                mSignUp.setVisibility(View.GONE);
                mForgotPasswordText.setVisibility(View.GONE);
                mBackToLogin.setVisibility(View.VISIBLE);
                mLoginPresenter.forgotPassword();
            }
        });

        //call submit password reset request
        mOkSubmitReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailForgotPassword.getText().toString();
                if(validateEmail(email)){
                    mBackToLogin.setVisibility(View.GONE);
                    mLoginPresenter.okPasswordResetRequest(email, mHandler);
                }
            }
        });

        //back to login
        mBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignUp.setVisibility(View.VISIBLE);
                mForgotPasswordText.setVisibility(View.VISIBLE);
                mBackToLogin.setVisibility(View.GONE);
                mLogin.setVisibility(View.GONE);
                mLoginPresenter.login();
            }
        });

        //call resend reset code request
        mResendCodeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailForgotPassword.getText().toString();
                mLoginPresenter.resendResetCode(email, mHandler);
            }
        });

        //call confirm reset request
        mOkConfirmReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailForgotPassword.getText().toString();
                mLoginPresenter.okPasswordResetConfirm(email);
            }
        });

    }

    @Override
    public void openSignUp() {
        mLoginLayout.setVisibility(View.GONE);
        mForgotPasswordLayout.setVisibility(View.GONE);
        mSignUpLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void openLogin() {
        mForgotPasswordLayout.setVisibility(View.GONE);
        mNewPasswordLayout.setVisibility(View.GONE);
        mSignUpLayout.setVisibility(View.GONE);
        mLoginLayout.setVisibility(View.VISIBLE);
        mForgotPasswordText.setVisibility(View.VISIBLE);
        mEmailForgotPassword.setText("");
    }


    @Override
    public void forgotPassword() {
        mLoginLayout.setVisibility(View.GONE);
        mSignUpLayout.setVisibility(View.GONE);
        mForgotPasswordLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void openResetPin(String email) {
        mForgotPasswordLayout.setVisibility(View.GONE);
        mResetCodeLayout.setVisibility(View.VISIBLE);
        mCodeSentAlert.setText(String.format(getString(R.string.text_code_sent_alert), email));
        mResendCodeText.setVisibility(View.VISIBLE);
    }

    @Override
    public void resendResetCode() {
        showMessage(getString(R.string.text_code_resent_alert));
    }

    @Override
    public void newPasswordInput() {
        mResetCodeLayout.setVisibility(View.GONE);
        mResendCodeText.setVisibility(View.GONE);
        mNewPasswordLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void confirmPasswordReset(String email) {
        String newPassword = mNewPasswordReset.getText().toString();
        String confirmNewPassword = mConfirmNewPasswordReset.getText().toString();
    }

    @Override
    public void showMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void setLoginEmail(String email) {

    }


    public boolean validateEmail(String email) {
        Matcher matcher = Patterns.EMAIL_ADDRESS.matcher(email);
        if (!email.equals("") && matcher.matches()) {
            return true;
        } else {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), R.string.invalid_email, Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }
    }

    public boolean validatePassword(String password){
        if(password.length() >= 8){
            Pattern pattern;
            Matcher matcher;
            final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[_@#$%^?&+=!])(?=\\S+$).{4,}$";
            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(password);

            if(matcher.matches()){
                return true;
            }else{
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), R.string.invalid_password, Snackbar.LENGTH_LONG);
                snackbar.show();
                return false;
            }
        }else{
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), R.string.password_length, Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }
    }

    @Override
    public void showLoadingDialog() {
        mDialog = new MaterialDialog.Builder(this)
                .title(R.string.app_name)
                .content("Please Wait...")
                .progress(true, 0)
                .show();
    }

    @Override
    public void dismissLoadingDialog() {
        mDialog.dismiss();
    }
}