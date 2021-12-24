package com.example.testapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.testapplication.Adapters.TextWatcherAdapter;
import com.example.testapplication.Application.AppContainer;
import com.example.testapplication.Application.MyApplication;
import com.example.testapplication.Items.LoginResponse;
import com.example.testapplication.R;
import com.example.testapplication.ViewModels.LoginViewModel;
import com.google.firebase.FirebaseApp;

import static com.example.testapplication.Repositories.LoginRepository.PASSWORD;
import static com.example.testapplication.Repositories.LoginRepository.USERS;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    public static final String REMEMBER = "remember_password";
    public static final String KEY = "preferences-key";

    private LoginViewModel loginViewModel;
    private EditText edtTxtUserID, edtTxtPassword;
    private ImageView emptyCheckbox, filledCheckbox;
    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);

        initViews();
        checkIfRememberPassword();

        emptyCheckbox.setOnClickListener(v -> {
            SharedPreferences preferences = getPreferences();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(REMEMBER, true);
            filledCheckbox.setVisibility(View.VISIBLE);
            emptyCheckbox.setVisibility(View.GONE);
            editor.apply();
        });

        filledCheckbox.setOnClickListener(v -> {
            SharedPreferences preferences = getPreferences();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(REMEMBER, false);
            emptyCheckbox.setVisibility(View.VISIBLE);
            filledCheckbox.setVisibility(View.GONE);
            editor.apply();
        });

        edtTxtUserID.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginViewModel.userID.setValue(s.toString());
            }
        });

        edtTxtPassword.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginViewModel.password.setValue(s.toString());
            }
        });

        btnSignIn.setOnClickListener(v -> {
            if (!edtTxtPassword.getText().toString().equals("") && !edtTxtUserID.getText().toString().equals("")) {
                String strippedUserID = edtTxtUserID.getText().toString().replace(" ", "");
                String strippedPassword = edtTxtPassword.getText().toString().replace(" ", "");
                loginViewModel.userID.setValue(strippedUserID);
                loginViewModel.password.setValue(strippedPassword);

                btnSignIn.setClickable(false);

                MutableLiveData<LoginResponse> response = loginViewModel.login();
                response.observe(this, new Observer<LoginResponse>() {
                    @Override
                    public void onChanged(LoginResponse loginResponse) {
                        switch (loginResponse.getResponse()) {
                            case LoginResponse.SUCCESS:
                                if (shouldSaveLoginData()) {
                                    saveLoginData(strippedUserID, strippedPassword);
                                }
                                navigateToScreenActivity(strippedUserID, loginResponse.getMd5_password());
                                Toast.makeText(LoginActivity.this, "Welcome " + loginResponse.getType(), Toast.LENGTH_SHORT).show();
                                break;
                            case LoginResponse.FAILED_PASSWORD:
                                Toast.makeText(LoginActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                                break;
                            case LoginResponse.FAILED_USER_ID:
                                Toast.makeText(LoginActivity.this, "Invalid UserID", Toast.LENGTH_SHORT).show();
                                break;
                            case LoginResponse.UNKNOWN_ERROR:
                                Toast.makeText(LoginActivity.this, "Unknown Error, please try again later.", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                        btnSignIn.setClickable(true);
                        response.removeObserver(this);
                    }
                });
            } else {
                Toast.makeText(this, "Fill in both fields", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean shouldSaveLoginData() {
        return emptyCheckbox.getVisibility() == View.GONE && filledCheckbox.getVisibility() == View.VISIBLE;
    }

    private void saveLoginData(String userID, String password) {
        SharedPreferences preferences = getPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USERS, userID);
        editor.putString(PASSWORD, password);
        editor.apply();
    }

    private void navigateToScreenActivity(String userID, String passwordMD5) {
        Intent screenActivityIntent = new Intent(this, ScreenActivity.class);
        screenActivityIntent.putExtra(LoginClass.USER_ID_KEY, userID);
        screenActivityIntent.putExtra(LoginClass.MD5_PASSWORD_KEY, passwordMD5);
        screenActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(screenActivityIntent);
    }

    private void checkIfRememberPassword() {
        SharedPreferences preferences = getPreferences();
        boolean rememberPassword = preferences.getBoolean(REMEMBER, false);
        if (rememberPassword) {
            edtTxtUserID.setText(preferences.getString(USERS, ""));
            edtTxtPassword.setText(preferences.getString(PASSWORD, ""));
            filledCheckbox.setVisibility(View.VISIBLE);
            emptyCheckbox.setVisibility(View.GONE);
        } else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(USERS, "");
            editor.putString(PASSWORD, "");
            filledCheckbox.setVisibility(View.GONE);
            emptyCheckbox.setVisibility(View.VISIBLE);
            editor.apply();
        }
    }

    private SharedPreferences getPreferences() {
        return getSharedPreferences(KEY, MODE_PRIVATE);
    }

    private void initViews() {
        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        loginViewModel = new ViewModelProvider(this, appContainer.loginViewModelFactory).get(LoginViewModel.class);
        edtTxtUserID = findViewById(R.id.edtTxtUserID);
        edtTxtPassword = findViewById(R.id.edtTxtPassword);
        emptyCheckbox = findViewById(R.id.emptyCheckbox);
        filledCheckbox = findViewById(R.id.filledCheckbox);
        btnSignIn = findViewById(R.id.btnSignIn);
    }
}