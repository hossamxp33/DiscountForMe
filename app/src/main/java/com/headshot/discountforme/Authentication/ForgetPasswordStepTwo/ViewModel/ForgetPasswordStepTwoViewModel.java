package com.headshot.discountforme.Authentication.ForgetPasswordStepTwo.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.headshot.discountforme.Authentication.ForgetPasswordStepTwo.Repository.ForgetPasswordStepTwoRepository;
import com.headshot.discountforme.Model.UserModel.UserModel;

public class ForgetPasswordStepTwoViewModel extends ViewModel {
    ForgetPasswordStepTwoRepository forgetPasswordStepTwoRepository;

    public LiveData<UserModel> forgetPassword(String email,String code) {
        forgetPasswordStepTwoRepository = ForgetPasswordStepTwoRepository.getInstance();
        return forgetPasswordStepTwoRepository.forgetPassword(email,code);
    }
}
