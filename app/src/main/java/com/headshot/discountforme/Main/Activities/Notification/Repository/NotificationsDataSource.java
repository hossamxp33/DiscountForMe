package com.headshot.discountforme.Main.Activities.Notification.Repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.headshot.discountforme.Model.NotificationModel.Datum;
import com.headshot.discountforme.Model.NotificationModel.NotificationModel;
import com.headshot.discountforme.Network.MainServices;
import com.headshot.discountforme.Utils.MainRequest;
import com.headshot.discountforme.Utils.RetroWeb;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NotificationsDataSource extends PageKeyedDataSource<Integer, Datum> {
    private static final String TAG = "RestaurantDataSource";
    private static final int FIRST_PAGE = 1;
    private MainRequest mainRequest;
    String token;

    private MutableLiveData<Boolean> _isViewLoading;


    public LiveData<Boolean> isViewLoading() {
        return _isViewLoading;
    }

    public NotificationsDataSource(String token) {
        this.token = token;
        _isViewLoading = new MutableLiveData<>();
    }


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,@NonNull LoadInitialCallback<Integer, Datum> callback) {
        _isViewLoading.postValue(true);

        Observable observable = RetroWeb.getClient().create(MainServices.class).notifications(token,FIRST_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<NotificationModel> observer = new Observer<NotificationModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(NotificationModel homeModel) {
                _isViewLoading.postValue(false);
                if (homeModel != null) {
                    if (homeModel.isValue()) {
                        callback.onResult(homeModel.getData(),null,FIRST_PAGE + 1);
                        Log.e(TAG,"initial : " + FIRST_PAGE);

                        Log.e("WQ","intial : " + _isViewLoading.getValue());

                    } else {
                        _isViewLoading.postValue(false);
                    }

                } else {
                    _isViewLoading.postValue(false);

                }
            }

            @Override
            public void onError(Throwable t) {
                _isViewLoading.postValue(false);
                mainRequest.onFailure(t.getLocalizedMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params,@NonNull LoadCallback<Integer, Datum> callback) {
        _isViewLoading.postValue(true);

        Observable observable = RetroWeb.getClient().create(MainServices.class).notifications(token,FIRST_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<NotificationModel> observer = new Observer<NotificationModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(NotificationModel homeModel) {
                _isViewLoading.postValue(false);
                Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;

                if (homeModel != null) {
                    if (homeModel.isValue()) {
                        callback.onResult(homeModel.getData(),adjacentKey);
                        Log.e(TAG,"before : " + params.key + " : " + FIRST_PAGE + " : " + adjacentKey);

                    } else {
                        _isViewLoading.postValue(false);
                    }

                } else {
                    _isViewLoading.postValue(false);

                }
            }

            @Override
            public void onError(Throwable t) {
                _isViewLoading.postValue(false);
                mainRequest.onFailure(t.getLocalizedMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params,@NonNull LoadCallback<Integer, Datum> callback) {
        _isViewLoading.postValue(true);

        Observable observable = RetroWeb.getClient().create(MainServices.class).notifications(token,params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<NotificationModel> observer = new Observer<NotificationModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(NotificationModel homeModel) {
                _isViewLoading.postValue(false);

                if (homeModel != null) {
                    if (homeModel.isValue()) {
                        callback.onResult(homeModel.getData(),params.key + 1);
                    } else {
                        _isViewLoading.postValue(false);
                    }

                } else {
                    _isViewLoading.postValue(false);

                }
                Log.e(TAG,"after : " + params.key + " : " + FIRST_PAGE);

            }

            @Override
            public void onError(Throwable t) {
                _isViewLoading.postValue(false);
                mainRequest.onFailure(t.getLocalizedMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);

    }
}

