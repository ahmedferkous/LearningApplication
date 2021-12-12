package com.example.testapplication.Application;

import com.example.testapplication.Repositories.LoginRepository;
import com.example.testapplication.ViewModels.ViewModelFactories.LoginViewModelFactory;

public class AppContainer {
    private final LoginRepository loginRepository = new LoginRepository();
    public LoginViewModelFactory loginViewModelFactory = new LoginViewModelFactory(loginRepository);
}
