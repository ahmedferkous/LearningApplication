package com.example.testapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.testapplication.Application.AppContainer;
import com.example.testapplication.Application.MyApplication;
import com.example.testapplication.Fragments.FragmentNotes;
import com.example.testapplication.Items.LoginResponse;
import com.example.testapplication.R;
import com.example.testapplication.ViewModels.LessonItemViewModel;
import com.example.testapplication.ViewModels.LoginViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class ScreenActivity extends AppCompatActivity {
    private static final String TAG = "ScreenActivity";
    public static final String USER_ID_KEY = "user_id_key";
    public static final String MD5_PASSWORD_KEY = "md5_password_key";

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private MaterialToolbar toolbar;

    private LoginViewModel loginViewModel;

    /*
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("Exit application?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                });
        builder.create().show();
    }
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        initViews();
        initNavigationView();
        initBottomNavigationView();

        Intent incomingIntent = getIntent();
        if (incomingIntent != null) {
            String userID = incomingIntent.getStringExtra(USER_ID_KEY);
            String passwordMD5 = incomingIntent.getStringExtra(MD5_PASSWORD_KEY);
            if (userID != null && passwordMD5 != null) {
                setupLoginChangeCheck(userID, passwordMD5);
            }
        }
    }

    private void setupLoginChangeCheck(String userID, String passwordMD5) {
        loginViewModel.userID.setValue(userID);
        loginViewModel.password.setValue(passwordMD5);

        MutableLiveData<LoginResponse> response = loginViewModel.checkLoginData();
        response.observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                Log.d(TAG, "onChanged: " + loginResponse.getResponse());
                if (loginResponse.getResponse() == LoginResponse.FORCED_SIGN_OUT) {
                    navigateBackToLoginActivity();
                }
            }
        });
    }

    private void navigateBackToLoginActivity() {
        Toast.makeText(this, "Re-login required.", Toast.LENGTH_SHORT).show();
        Intent loginActivityIntent = new Intent(this, LoginActivity.class);
        loginActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(loginActivityIntent);
    }

    private void initViews() {
        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        loginViewModel = new ViewModelProvider(this, appContainer.loginViewModelFactory).get(LoginViewModel.class);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationDrawerView);
        bottomNavigationView = findViewById(R.id.bottomNavView);
    }

    private void initNavigationView() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_notes:
                        transactToNotes();
                        break;
                    case R.id.ic_tests:
                        break;
                    case R.id.ic_feedback:
                        break;
                    case R.id.ic_settings:
                        break;
                    case R.id.ic_switch:
                        break;
                    case R.id.ic_about_us:
                        break;
                    case R.id.ic_licenses:
                        break;
                }

                return true;
            }
        });
    }

    private void initBottomNavigationView() {
        bottomNavigationView.setSelectedItemId(R.id.notes);
        transactToNotes();

        bottomNavigationView.setItemOnTouchListener(R.id.notes, new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                transactToNotes();
                return true;
            }
        });

        bottomNavigationView.setItemOnTouchListener(R.id.tests, new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        bottomNavigationView.setItemOnTouchListener(R.id.feedback, new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    private void transactToNotes() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, new FragmentNotes());
        transaction.commit();
    }
}