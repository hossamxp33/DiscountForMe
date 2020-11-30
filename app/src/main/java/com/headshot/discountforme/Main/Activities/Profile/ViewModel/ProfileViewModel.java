package com.headshot.discountforme.Main.Activities.Profile.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.headshot.discountforme.Main.Activities.Profile.Repository.ProfileRepository;
import com.headshot.discountforme.Model.UserModel.UserModel;

public class ProfileViewModel extends ViewModel {
    ProfileRepository profileRepository;

    public LiveData<UserModel> updateProfile(String userName,String email,String UserToken) {
        profileRepository = ProfileRepository.getInstance();
        return profileRepository.updateProfile(userName,email,UserToken);
    }
}
