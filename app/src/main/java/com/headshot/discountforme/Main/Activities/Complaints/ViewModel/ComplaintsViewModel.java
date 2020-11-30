package com.headshot.discountforme.Main.Activities.Complaints.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.headshot.discountforme.Main.Activities.Complaints.Repository.ComplaintsRepository;
import com.headshot.discountforme.Model.GeneralResponse.GeneralResponse;

public class ComplaintsViewModel extends ViewModel {

    ComplaintsRepository complaintsRepository;

    public LiveData<GeneralResponse> sendHelp(String brand,String email,String address,String message,String coupon) {
        complaintsRepository = ComplaintsRepository.getInstance();
        return complaintsRepository.sendHelp(brand,email,address,message,coupon);
    }

}
