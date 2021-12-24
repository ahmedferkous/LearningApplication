package com.example.testapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.testapplication.Application.AppContainer;
import com.example.testapplication.Application.MyApplication;
import com.example.testapplication.Items.TestItem;
import com.example.testapplication.R;
import com.example.testapplication.ViewModels.LoginViewModel;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class TestActivity extends AppCompatActivity {
    public static final String TEST_ITEM_KEY = "test_item_key";

    private TextView txtTestName;
    private LinearProgressIndicator linearProgressIndicator;
    private TextView txtQuestionNumber;
    private TextView txtQuestion;
    private Button btnPrevious;
    private Button btnNext;

    private LoginViewModel loginViewModel;
    private LoginClass loginClass;
    private TestItem testItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initViews();

        Intent incomingIntent = getIntent();
        if (incomingIntent != null) {
            loginClass = new LoginClass(incomingIntent, loginViewModel);
            loginClass.setupLoginChangeCheck(this);
            testItem = incomingIntent.getParcelableExtra(TEST_ITEM_KEY);

            if (testItem != null) {
                
            }
        }
    }

    private void initViews() {
        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        loginViewModel = new ViewModelProvider(this, appContainer.loginViewModelFactory).get(LoginViewModel.class);
        txtTestName = findViewById(R.id.txtTestName);
        linearProgressIndicator = findViewById(R.id.linearProgressIndicator);
        txtQuestionNumber = findViewById(R.id.txtQuestionNumber);
        txtQuestion = findViewById(R.id.txtQuestion);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
    }
}