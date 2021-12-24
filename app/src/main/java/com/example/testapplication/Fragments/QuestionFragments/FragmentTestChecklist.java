package com.example.testapplication.Fragments.QuestionFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testapplication.R;
import com.google.android.material.card.MaterialCardView;

public class FragmentTestChecklist extends Fragment {
    private LinearLayout linLayoutChecklist;
    private MaterialCardView cardViewChoiceOneChecklist;
    private ImageView imgViewEmptyChoiceOneChecklist;
    private ImageView imgViewCheckedChoiceOneChecklist;
    private TextView txtViewChoiceOneChecklist;

    private MaterialCardView cardViewChoiceTwoChecklist;
    private ImageView imgViewEmptyChoiceTwoChecklist;
    private ImageView imgViewCheckedChoiceTwoChecklist;
    private TextView txtViewChoiceTwoChecklist;

    private MaterialCardView cardViewChoiceThreeChecklist;
    private ImageView imgViewEmptyChoiceThreeChecklist;
    private ImageView imgViewCheckedChoiceThreeChecklist;
    private TextView txtViewChoiceThreeChecklist;

    private MaterialCardView cardViewChoiceFourChecklist;
    private ImageView imgViewEmptyChoiceFourChecklist;
    private ImageView imgViewCheckedChoiceFourChecklist;
    private TextView txtViewChoiceFourChecklist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_checklist, container, false);

        return view;
    }
}
