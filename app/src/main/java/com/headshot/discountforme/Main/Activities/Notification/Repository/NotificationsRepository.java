package com.headshot.discountforme.Main.Activities.Notification.Repository;

import androidx.lifecycle.MutableLiveData;

import com.headshot.discountforme.Model.NotificationModel.NotificationModel;
import com.headshot.discountforme.Network.MainServices;
import com.headshot.discountforme.Utils.RetroWeb;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NotificationsRepository {
    private static NotificationsRepository notificationsRepository;

    public static NotificationsRepository getInstance() {
        if (notificationsRepository == null) {
            notificationsRepository = new NotificationsRepository();
        }
        return notificationsRepository;
    }

    public MutableLiveData<NotificationModel> delete_all_notifications(String token) {
        final MutableLiveData<NotificationModel> mutableLiveData = new MutableLiveData<>();
        Observable observable = RetroWeb.getClient().create(MainServices.class).delete_all_notifications(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<NotificationModel> observer = new Observer<NotificationModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(NotificationModel userModel) {
                mutableLiveData.setValue(userModel);

            }

            @Override
            public void onError(Throwable e) {
//                handleApiException(context, e);

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
