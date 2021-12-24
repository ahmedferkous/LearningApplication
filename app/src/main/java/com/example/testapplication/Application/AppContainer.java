package com.example.testapplication.Application;

import com.example.testapplication.Repositories.FirebaseRepository;
import com.example.testapplication.Repositories.LoginRepository;
import com.example.testapplication.ViewModels.ViewModelFactories.LessonItemViewModelFactory;
import com.example.testapplication.ViewModels.ViewModelFactories.LoginViewModelFactory;
import com.example.testapplication.ViewModels.ViewModelFactories.TestItemViewModelFactory;

public class AppContainer {
    private final LoginRepository loginRepository = new LoginRepository();
    public final FirebaseRepository firebaseRepository = new FirebaseRepository();
    public LoginViewModelFactory loginViewModelFactory = new LoginViewModelFactory(loginRepository);
    public LessonItemViewModelFactory lessonItemViewModelFactory = new LessonItemViewModelFactory(firebaseRepository);
    public TestItemViewModelFactory testItemViewModelFactory = new TestItemViewModelFactory(firebaseRepository);
}
