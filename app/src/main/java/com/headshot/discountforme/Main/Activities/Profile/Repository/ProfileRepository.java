package com.headshot.discountforme.Main.Activities.Profile.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.headshot.discountforme.Model.UserModel.UserModel;
import com.headshot.discountforme.Network.MainServices;
import com.headshot.discountforme.Utils.RetroWeb;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProfileRepository {
    private static ProfileRepository profileRepository;

    public static ProfileRepository getInstance() {
        if (profileRepository == null) {
            profileRepository = new ProfileRepository();
        }
        return profileRepository;
    }

    public MutableLiveData<UserModel> updateProfile(String userName,String email,String UserToken) {
        final MutableLiveData<UserModel> mutableLiveData = new MutableLiveData<>();
        Log.e("userToken",UserToken);

        Observable observable = RetroWeb.getClient().create(MainServices.class).update_profile(UserToken,userName,email)
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
