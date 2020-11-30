package com.headshot.discountforme.Main.Activities.SuggestCoupon.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.headshot.discountforme.Main.Activities.SuggestCoupon.Repository.SuggestCouponRepository;
import com.headshot.discountforme.Model.GeneralResponse.GeneralResponse;

public class SuggestCouponViewModel extends ViewModel {
    SuggestCouponRepository suggestCouponRepository;

    public LiveData<GeneralResponse> suggestCoupon(String userName,String address,String email,String whatsApp,String couponCode,String brand) {
        suggestCouponRepository = SuggestCouponRepository.getInstance();
        return suggestCouponRepository.suggestCoupon(userName,address,email,whatsApp,couponCode,brand);
    }
}
