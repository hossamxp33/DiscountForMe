package com.headshot.discountforme.Main.Activities.FilterAndSearch.Repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.headshot.discountforme.Model.HomeModel.Datum;
import com.headshot.discountforme.Model.HomeModel.HomeModel;
import com.headshot.discountforme.Network.MainServices;
import com.headshot.discountforme.Utils.RetroWeb;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchDataSource extends PageKeyedDataSource<Integer, Datum> {

    private static final String TAG = "OrdersDataSource";
    private static final int FIRST_PAGE = 1;
    String token, search, order_by;
    private MutableLiveData<Boolean> _isViewLoading;

    public LiveData<Boolean> isViewLoading() {
        return _isViewLoading;
    }

    Boolean isLogin;

    public SearchDataSource(String token,String search,Boolean isLogin) {
        this.token = token;
        this.search = search;
        this.order_by = order_by;
        this.isLogin = isLogin;
        _isViewLoading = new MutableLiveData<>();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,@NonNull LoadInitialCallback<Integer, Datum> callback) {
        _isViewLoading.postValue(true);
        Observable observable;
        Observer<HomeModel> observer;
        if (isLogin) {
            observable = RetroWeb.getClient().create(MainServices.class).search(token,search,FIRST_PAGE)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            observer = new Observer<HomeModel>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(HomeModel homeModel) {
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
                }

                @Override
                public void onComplete() {

                }
            };
        } else {
            observable = RetroWeb.getClient().create(MainServices.class).searchVisitor(search,FIRST_PAGE)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            observer = new Observer<HomeModel>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(HomeModel homeModel) {
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
                }

                @Override
                public void onComplete() {

                }
            };
        }
        observable.subscribe(observer);


    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params,@NonNull LoadCallback<Integer, Datum> callback) {
        _isViewLoading.postValue(true);

        Observable observable;
        Observer<HomeModel> observer;
        if (isLogin) {
            observable = RetroWeb.getClient().create(MainServices.class).search(token,search,FIRST_PAGE)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            observer = new Observer<HomeModel>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(HomeModel homeModel) {
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
                }

                @Override
                public void onComplete() {

                }
            };
        } else {
            observable = RetroWeb.getClient().create(MainServices.class).searchVisitor(search,FIRST_PAGE)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            observer = new Observer<HomeModel>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(HomeModel homeModel) {
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
                }

                @Override
                public void onComplete() {

                }
            };
        }
        observable.subscribe(observer);

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params,@NonNull LoadCallback<Integer, Datum> callback) {
        _isViewLoading.postValue(true);
        Observable observable;
        Observer<HomeModel> observer;
        if (isLogin) {
            observable = RetroWeb.getClient().create(MainServices.class).search(token,search,params.key)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            observer = new Observer<HomeModel>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(HomeModel homeModel) {
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
                }

                @Override
                public void onComplete() {

                }
            };
        } else {
            observable = RetroWeb.getClient().create(MainServices.class).searchVisitor(search,params.key)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            observer = new Observer<HomeModel>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(HomeModel homeModel) {
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
                }

                @Override
                public void onComplete() {

                }
            };
        }
        observable.subscribe(observer);


    }

}
