package com.headshot.discountforme.Authentication.ForgetPasswordStepTwo.Repository;

import androidx.lifecycle.MutableLiveData;

import com.headshot.discountforme.Model.UserModel.UserModel;
import com.headshot.discountforme.Network.MainServices;
import com.headshot.discountforme.Utils.RetroWeb;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ForgetPasswordStepTwoRepository {

    private static ForgetPasswordStepTwoRepository forgetPasswordStepTwoRepository;

    public static ForgetPasswordStepTwoRepository getInstance() {
        if (forgetPasswordStepTwoRepository == null) {
            forgetPasswordStepTwoRepository = new ForgetPasswordStepTwoRepository();
        }
        return forgetPasswordStepTwoRepository;
    }

    public MutableLiveData<UserModel> forgetPassword(String email,String code) {
        final MutableLiveData<UserModel> mutableLiveData = new MutableLiveData<>();


        Observable observable = RetroWeb.getClient().create(MainServices.class).checkCode(email,code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<UserModel> observer = new Observer<UserModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(UserModel userModel) {
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
