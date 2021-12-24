package com.example.testapplication.ViewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testapplication.Items.TestItem;
import com.example.testapplication.Repositories.FirebaseRepository;

import java.util.List;

public class TestItemViewModel extends ViewModel {
    private static final String TAG = "TestItemViewModel";

    private final MutableLiveData<List<TestItem>> testItemListMutableLiveData;
    private final FirebaseRepository firebaseRepository;

    public TestItemViewModel(FirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
        testItemListMutableLiveData = firebaseRepository.getTestItemListMutableLiveData();
    }

    public MutableLiveData<List<TestItem>> getLiveTestItemData() {
        return testItemListMutableLiveData;
    }
}
