package com.headshot.discountforme.Authentication.Login.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.headshot.discountforme.Authentication.Login.Repository.LoginRepository;
import com.headshot.discountforme.Model.UserModel.UserModel;

public class LoginViewModel extends ViewModel {
    LoginRepository loginRepository;

    public LiveData<UserModel> login(String email,String password,String deviceToken,String socialToken) {
        loginRepository = LoginRepository.getInstance();
        return loginRepository.login(email,password,deviceToken,socialToken);
    }
}
