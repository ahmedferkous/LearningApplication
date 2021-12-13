package com.example.testapplication.ViewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testapplication.Items.LessonItem;
import com.example.testapplication.Repositories.FirebaseRepository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class LessonItemViewModel extends ViewModel {
    private static final String TAG = "LessonItemViewModel";

    private final MutableLiveData<List<LessonItem>> lessonItemListMutableLiveData;
    private final FirebaseRepository repository;

    public LessonItemViewModel() {
        repository = new FirebaseRepository();
        lessonItemListMutableLiveData = repository.getLessonItemListMutableLiveData();
    }

    public MutableLiveData<List<LessonItem>> getLiveLessonItemData() {
        return lessonItemListMutableLiveData;
    }

}
