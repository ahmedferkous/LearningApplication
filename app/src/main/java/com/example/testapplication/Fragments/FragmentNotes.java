package com.example.testapplication.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.Adapters.LessonItemAdapter;
import com.example.testapplication.Application.AppContainer;
import com.example.testapplication.Application.MyApplication;
import com.example.testapplication.Items.LessonItem;
import com.example.testapplication.R;
import com.example.testapplication.ViewModels.LessonItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class FragmentNotes extends Fragment {
    private static final String TAG = "FragmentNotes";

    private LessonItemViewModel lessonItemViewModel;
    private LessonItemAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        initViews(view);

        MutableLiveData<List<LessonItem>> response = lessonItemViewModel.getLiveLessonItemData();
        response.observe(getViewLifecycleOwner(), new Observer<List<LessonItem>>() {
            @Override
            public void onChanged(List<LessonItem> lessonItems) {
                adapter.setLessonItems((ArrayList<LessonItem>) lessonItems);
                Log.d(TAG, "onChanged: " + lessonItems.size());
            }
        });

        return view;
    }

    private void initViews(View view) {
        AppContainer appContainer = ((MyApplication) getActivity().getApplication()).appContainer;
        lessonItemViewModel = new ViewModelProvider(requireActivity(), appContainer.lessonItemViewModelFactory).get(LessonItemViewModel.class);
        RecyclerView recView = view.findViewById(R.id.recView);
        recView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LessonItemAdapter(getContext());
        recView.setAdapter(adapter);
    }
}
