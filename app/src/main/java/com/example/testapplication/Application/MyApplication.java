package com.example.testapplication.Application;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.google.firebase.FirebaseApp;

public class MyApplication extends Application {
    public AppContainer appContainer = new AppContainer();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
