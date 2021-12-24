package com.example.testapplication.Fragments.QuestionFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testapplication.R;
import com.google.android.material.textfield.TextInputEditText;

public class FragmentTestShortAnswer extends Fragment {
    private TextInputEditText edtTxtInputResponse;
    private EditText edtTxtResponse;
    private LinearLayout linLayoutCorrectAnswer;
    private TextView txtViewUserResponse;
    private TextView txtViewCorrectAnswer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_shortanswer, container, false);

        return view;
    }
}
