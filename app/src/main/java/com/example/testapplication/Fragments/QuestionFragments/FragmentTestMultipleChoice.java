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

public class FragmentTestMultipleChoice extends Fragment {
    private LinearLayout linLayoutMultipleChoice;
    private MaterialCardView cardViewChoiceOneMultipleChoice;
    private ImageView imgViewEmptyChoiceOneMultipleChoice;
    private ImageView imgViewCheckedChoiceOneMultipleChoice;
    private TextView txtViewChoiceOneMultipleChoice;

    private MaterialCardView cardViewChoiceTwoMultipleChoice;
    private ImageView imgViewEmptyChoiceTwoMultipleChoice;
    private ImageView imgViewCheckedChoiceTwoMultipleChoice;
    private TextView txtViewChoiceTwoMultipleChoice;

    private MaterialCardView cardViewChoiceThreeMultipleChoice;
    private ImageView imgViewEmptyChoiceThreeMultipleChoice;
    private ImageView imgViewCheckedChoiceThreeMultipleChoice;
    private TextView txtViewChoiceThreeMultipleChoice;

    private MaterialCardView cardViewChoiceFourMultipleChoice;
    private ImageView imgViewEmptyChoiceFourMultipleChoice;
    private ImageView imgViewCheckedChoiceFourMultipleChoice;
    private TextView txtViewChoiceFourMultipleChoice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_multiplechoice, container, false);

        return view;
    }
}
