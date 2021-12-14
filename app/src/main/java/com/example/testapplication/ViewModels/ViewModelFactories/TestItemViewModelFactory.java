package com.example.testapplication.ViewModels.ViewModelFactories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.testapplication.Repositories.FirebaseRepository;
import com.example.testapplication.ViewModels.LessonItemViewModel;
import com.example.testapplication.ViewModels.TestItemViewModel;

public class TestItemViewModelFactory implements ViewModelProvider.Factory {
    private final FirebaseRepository firebaseRepository;

    public TestItemViewModelFactory(FirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TestItemViewModel(firebaseRepository);
    }
}
