package com.headshot.discountforme.Authentication.ForgetPasswordStepThree.Repository;

import androidx.lifecycle.MutableLiveData;

import com.headshot.discountforme.Authentication.Login.Repository.LoginRepository;
import com.headshot.discountforme.Model.UserModel.UserModel;
import com.headshot.discountforme.Network.MainServices;
import com.headshot.discountforme.Utils.RetroWeb;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ForgetPasswordStepThreeRepository {

    private static ForgetPasswordStepThreeRepository forgetPasswordStepThreeRepository;

    public static ForgetPasswordStepThreeRepository getInstance() {
        if (forgetPasswordStepThreeRepository == null) {
            forgetPasswordStepThreeRepository = new ForgetPasswordStepThreeRepository();
        }
        return forgetPasswordStepThreeRepository;
    }

    public MutableLiveData<UserModel> changePassword(String email,String password,String deviceToken) {
        final MutableLiveData<UserModel> mutableLiveData = new MutableLiveData<>();


        Observable observable = RetroWeb.getClient().create(MainServices.class).changePassword(email,password,deviceToken,"android")
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
