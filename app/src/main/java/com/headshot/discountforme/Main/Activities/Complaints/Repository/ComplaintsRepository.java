package com.headshot.discountforme.Main.Activities.Complaints.Repository;

import androidx.lifecycle.MutableLiveData;

import com.headshot.discountforme.Model.GeneralResponse.GeneralResponse;
import com.headshot.discountforme.Network.MainServices;
import com.headshot.discountforme.Utils.RetroWeb;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ComplaintsRepository {
    private static ComplaintsRepository complaintsRepository;

    public static ComplaintsRepository getInstance() {
        if (complaintsRepository == null) {
            complaintsRepository = new ComplaintsRepository();
        }
        return complaintsRepository;
    }

    public MutableLiveData<GeneralResponse> sendHelp(String brand,String email,String address,String message,String coupon) {
        final MutableLiveData<GeneralResponse> mutableLiveData = new MutableLiveData<>();

        Observable observable = RetroWeb.getClient().create(MainServices.class).sendHelp(brand,email,address,message,coupon)
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
