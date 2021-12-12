package com.example.testapplication.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testapplication.Items.LoginResponse;
import com.example.testapplication.Repositories.LoginRepository;

public class LoginViewModel extends ViewModel {
    public MutableLiveData<String> userID = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

    private final LoginRepository loginRepository;

    public LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public MutableLiveData<LoginResponse> login() {
        return loginRepository.loginWithData(userID.getValue(), password.getValue());
    }

    public MutableLiveData<LoginResponse> checkLoginData() {
        return loginRepository.registerLoginDataChangeListener(userID.getValue(), password.getValue());
    }


}
