package com.example.testapplication.ViewModels.ViewModelFactories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.testapplication.Repositories.LoginRepository;
import com.example.testapplication.ViewModels.LoginViewModel;

public class LoginViewModelFactory implements ViewModelProvider.Factory {
    private final LoginRepository loginRepository;

    public LoginViewModelFactory(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LoginViewModel(loginRepository);
    }
}
