package com.headshot.discountforme.Authentication.Register.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.headshot.discountforme.Authentication.Register.Repository.RegisterRepository;
import com.headshot.discountforme.Model.UserModel.UserModel;

public class RegisterViewModel extends ViewModel {
    RegisterRepository registerRepository;

    public LiveData<UserModel> register(String name,String email,String password,String confirmPassword,String deviceToken,String socialToken) {
        registerRepository = RegisterRepository.getInstance();
        return registerRepository.register(name,email,password,confirmPassword,deviceToken,socialToken);
    }
}
