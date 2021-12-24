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

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.testapplication.Application.AppContainer;
import com.example.testapplication.Application.MyApplication;
import com.example.testapplication.Fragments.FragmentNotes;
import com.example.testapplication.Fragments.FragmentTests;
import com.example.testapplication.Items.LoginResponse;
import com.example.testapplication.R;
import com.example.testapplication.ViewModels.LoginViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class ScreenActivity extends AppCompatActivity {
    private static final String TAG = "ScreenActivity";

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private MaterialToolbar toolbar;

    private LoginClass loginClass;
    private LoginViewModel loginViewModel;
    private AppContainer appContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        initViews();

        Intent incomingIntent = getIntent();
        if (incomingIntent != null) {
            loginClass = new LoginClass(incomingIntent, loginViewModel);
            loginClass.setupLoginChangeCheck(this);
            appContainer.firebaseRepository.initDb(loginViewModel.userID.getValue());

            initNavigationView();
            initBottomNavigationView();
        }

    }

    private void initViews() {
        appContainer = ((MyApplication) getApplication()).appContainer;
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
                if (!(bottomNavigationView.getSelectedItemId() == item.getItemId())) {
                    Log.d(TAG, "onNavigationItemSelected: " + bottomNavigationView.getSelectedItemId() + " " + item.getItemId());
                    switch (item.getItemId()) {
                        case R.id.notes:
                            transactToNotes();
                            break;
                        case R.id.tests:
                            transactToTests();
                            break;
                        case R.id.feedback:
                            break;
                        case R.id.ic_settings:
                            break;
                        case R.id.ic_logout:
                            AlertDialog.Builder builder = new AlertDialog.Builder(ScreenActivity.this)
                                    .setMessage("Are you sure you want to log out?")
                                    .setNegativeButton("No", null)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            loginClass.navigateBackToLoginActivity(ScreenActivity.this, false);
                                        }
                                    });
                            builder.create().show();
                            break;
                        case R.id.ic_about_us:
                            break;
                        case R.id.ic_licenses:
                            break;
                    }
                }
                return true;
            }
        });
    }

    private void initBottomNavigationView() {
        transactToNotes();
        bottomNavigationView.setItemOnTouchListener(R.id.notes, new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (buttonReleased(event) && !(bottomNavigationView.getSelectedItemId() == R.id.notes)) {
                    transactToNotes();
                }
                return true;
            }
        });

        bottomNavigationView.setItemOnTouchListener(R.id.tests, new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (buttonReleased(event) && !(bottomNavigationView.getSelectedItemId() == R.id.tests)) {
                    transactToTests();
                }
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

    private boolean buttonReleased(MotionEvent event) {
        return event.getAction() == MotionEvent.ACTION_UP;
    }

    private void transactToNotes() {
        bottomNavigationView.setSelectedItemId(R.id.notes);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, new FragmentNotes());
        transaction.commit();
    }

    private void transactToTests() {
        bottomNavigationView.setSelectedItemId(R.id.tests);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, new FragmentTests());
        transaction.commit();
    }

}