package com.headshot.discountforme.Main.Activities.SuggestCoupon.Repository;

import androidx.lifecycle.MutableLiveData;

import com.headshot.discountforme.Model.GeneralResponse.GeneralResponse;
import com.headshot.discountforme.Network.MainServices;
import com.headshot.discountforme.Utils.RetroWeb;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SuggestCouponRepository {
    private static SuggestCouponRepository suggestCouponRepository;

    public static SuggestCouponRepository getInstance() {
        if (suggestCouponRepository == null) {
            suggestCouponRepository = new SuggestCouponRepository();
        }
        return suggestCouponRepository;
    }

    public MutableLiveData<GeneralResponse> suggestCoupon(String userName,String address,String email,String whatsApp,String couponCode,String brand) {
        final MutableLiveData<GeneralResponse> mutableLiveData = new MutableLiveData<>();

        Observable observable = RetroWeb.getClient().create(MainServices.class).suggestCoupon(userName,address,email,whatsApp,couponCode,brand)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<GeneralResponse> observer = new Observer<GeneralResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(GeneralResponse userModel) {
                mutableLiveData.setValue(userModel);

            }

            @Override
            public void onError(Throwable e) {
                mutableLiveData.setValue(null);
            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);

        return mutableLiveData;
    }
}
