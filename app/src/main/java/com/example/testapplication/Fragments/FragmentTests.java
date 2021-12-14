package com.example.testapplication.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.Adapters.TestItemAdapter;
import com.example.testapplication.Application.AppContainer;
import com.example.testapplication.Application.MyApplication;
import com.example.testapplication.Items.TestItem;
import com.example.testapplication.R;
import com.example.testapplication.ViewModels.LoginViewModel;
import com.example.testapplication.ViewModels.TestItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class FragmentTests extends Fragment {
    private static final String TAG = "FragmentTests";

    private String userID;
    private RecyclerView recView;
    private TestItemAdapter adapter;
    private TestItemViewModel testItemViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tests, container, false);
        initViews(view);

        MutableLiveData<List<TestItem>> response = testItemViewModel.getLiveTestItemData(userID);
        response.observe(getViewLifecycleOwner(), new Observer<List<TestItem>>() {
            @Override
            public void onChanged(List<TestItem> testItems) {
                Log.d(TAG, "onChanged: " + testItems.size());
                adapter.setTestItems((ArrayList<TestItem>) testItems);
            }
        });

        return view;
    }

    private void initViews(View view) {
        AppContainer appContainer = ((MyApplication) getActivity().getApplication()).appContainer;
        testItemViewModel = new ViewModelProvider(requireActivity(), appContainer.testItemViewModelFactory).get(TestItemViewModel.class);
        userID = new ViewModelProvider(requireActivity(), appContainer.loginViewModelFactory).get(LoginViewModel.class).userID.getValue();
        recView = view.findViewById(R.id.recView);
        recView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TestItemAdapter(getContext());
        recView.setAdapter(adapter);
    }
}
