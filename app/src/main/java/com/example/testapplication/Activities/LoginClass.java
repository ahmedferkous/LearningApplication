package com.example.testapplication.Activities;

import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.testapplication.Application.AppContainer;
import com.example.testapplication.Items.LoginResponse;
import com.example.testapplication.ViewModels.LoginViewModel;

public class LoginClass {
    public static final String USER_ID_KEY = "user_id_key";
    public static final String MD5_PASSWORD_KEY = "md5_password_key";

    private final Intent incomingIntent;
    private final LoginViewModel loginViewModel;

    public LoginClass(Intent receivedLoginDataIntent, LoginViewModel loginViewModel) {
        this.incomingIntent = receivedLoginDataIntent;
        this.loginViewModel = loginViewModel;
        setLoginData();
    }

    private void setLoginData() {
        String userID = incomingIntent.getStringExtra(USER_ID_KEY);
        String passwordMD5 = incomingIntent.getStringExtra(MD5_PASSWORD_KEY);
        if (userID != null && passwordMD5 != null) {
            loginViewModel.userID.setValue(userID);
            loginViewModel.password.setValue(passwordMD5);
        }
    }

    public void setupLoginChangeCheck(AppCompatActivity appCompatActivity) {
        MutableLiveData<LoginResponse> response = loginViewModel.checkLoginData();
        response.observe(appCompatActivity, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                if (loginResponse.getResponse() == LoginResponse.FORCED_SIGN_OUT) {
                    navigateBackToLoginActivity(appCompatActivity, true);
                }
            }
        });
    }

    public void navigateBackToLoginActivity(AppCompatActivity appCompatActivity, boolean forced) {
        if (forced) {
            Toast.makeText(appCompatActivity, "Re-login required.", Toast.LENGTH_SHORT).show();
        }
        Intent loginActivityIntent = new Intent(appCompatActivity, LoginActivity.class);
        loginActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        appCompatActivity.startActivity(loginActivityIntent);
    }
}
