package com.headshot.discountforme.Utils.MenuListFragment.Presenter;

import android.content.Context;
import android.content.Intent;

import com.headshot.discountforme.Authentication.Login.View.LoginActivity;
import com.headshot.discountforme.Model.GeneralResponse.GeneralResponse;
import com.headshot.discountforme.Network.MainServices;
import com.headshot.discountforme.R;
import com.headshot.discountforme.Utils.BasePresenter;
import com.headshot.discountforme.Utils.Constants;
import com.headshot.discountforme.Utils.RetroWeb;
import com.headshot.discountforme.Utils.SharedPrefManager;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import spencerstudios.com.bungeelib.Bungee;

public class MenuListFragmentPresenter extends BasePresenter implements MenuListFragmentViewPresenter {
    Context context;
    SharedPrefManager sharedPrefManager;

    public MenuListFragmentPresenter(Context context,SharedPrefManager sharedPrefManager) {
        this.context = context;
        this.sharedPrefManager = sharedPrefManager;
    }

    @Override
    public void logout(String deviceToken) {
        startLoading();
        Observable observable = RetroWeb.getClient().create(MainServices.class).logout(Constants.beforApiToken +
                sharedPrefManager.getUserDate().getToken(),deviceToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<GeneralResponse> observer = new Observer<GeneralResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(GeneralResponse userModel) {
                stopLoading();
                if (userModel != null) {
                    if (userModel.getValue()) {
                        sharedPrefManager.Logout();
                        Intent intent = new Intent(context,LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        context.startActivity(intent);
                        Bungee.inAndOut(context);
                        sharedPrefManager.setNumberOfChats(0);


                    } else {
                        errorToast(context,userModel.getMsg());
                    }
                } else {
                    errorToast(context,context.getString(R.string.somethingWentWrong));
                }

            }

            @Override
            public void onError(Throwable e) {
                handleApiException(context,e);
                stopLoading();
            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);

    }
}
