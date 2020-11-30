package com.headshot.discountforme.Authentication.ForgetPasswordStepOne.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.headshot.discountforme.Authentication.ForgetPasswordStepOne.Repository.ForgetPasswordStepOneRepository;
import com.headshot.discountforme.Model.UserModel.UserModel;

public class ForgetPasswordStepOneViewModel extends ViewModel {
    ForgetPasswordStepOneRepository forgetPasswordStepOneRepository;

    public LiveData<UserModel> forgetPassword(String email) {
        forgetPasswordStepOneRepository = ForgetPasswordStepOneRepository.getInstance();
        return forgetPasswordStepOneRepository.forgetPassword(email);
    }
}
