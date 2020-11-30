package com.headshot.discountforme.Authentication.ForgetPasswordStepThree.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.headshot.discountforme.Authentication.ForgetPasswordStepThree.Repository.ForgetPasswordStepThreeRepository;
import com.headshot.discountforme.Model.UserModel.UserModel;

public class ForgetPasswordStepThreeViewModel extends ViewModel {
    ForgetPasswordStepThreeRepository forgetPasswordStepThreeRepository;

    public LiveData<UserModel> changePassword(String email,String password,String deviceToken) {
        forgetPasswordStepThreeRepository = ForgetPasswordStepThreeRepository.getInstance();
        return forgetPasswordStepThreeRepository.changePassword(email,password,deviceToken);
    }
}
