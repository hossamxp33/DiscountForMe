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

public class FilterAndSearchDataSource extends PageKeyedDataSource<Integer, Datum> {

    private static final String TAG = "OrdersDataSource";
    private static final int FIRST_PAGE = 1;
    String token, categories, order_by;
    private MutableLiveData<Boolean> _isViewLoading;

    public LiveData<Boolean> isViewLoading() {
        return _isViewLoading;
    }

    public FilterAndSearchDataSource(String token, String categories, String order_by) {
        this.token = token;
        this.categories = categories;
        this.order_by = order_by;
        _isViewLoading = new MutableLiveData<>();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Datum> callback) {
        _isViewLoading.postValue(true);
        Observable observable = RetroWeb.getClient().create(MainServices.class).filter(token, categories, order_by, FIRST_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<HomeModel> observer = new Observer<HomeModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(HomeModel homeModel) {
                _isViewLoading.postValue(false);
                if (homeModel != null) {
                    if (homeModel.isValue()) {
                        callback.onResult(homeModel.getData(), null, FIRST_PAGE + 1);
                        Log.e(TAG, "initial : " + FIRST_PAGE);

                        Log.e("WQ", "intial : " + _isViewLoading.getValue());

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
        observable.subscribe(observer);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Datum> callback) {

        _isViewLoading.postValue(true);

        Observable observable = RetroWeb.getClient().create(MainServices.class).filter(token, categories, order_by, FIRST_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<HomeModel> observer = new Observer<HomeModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(HomeModel homeModel) {
                _isViewLoading.postValue(false);
                Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;

                if (homeModel != null) {
                    if (homeModel.isValue()) {
                        callback.onResult(homeModel.getData(), adjacentKey);
                        Log.e(TAG, "before : " + params.key + " : " + FIRST_PAGE + " : " + adjacentKey);

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
        observable.subscribe(observer);


    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Datum> callback) {
        _isViewLoading.postValue(true);

        Observable observable = RetroWeb.getClient().create(MainServices.class).filter(token, categories,
                order_by, params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<HomeModel> observer = new Observer<HomeModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(HomeModel homeModel) {
                _isViewLoading.postValue(false);

                if (homeModel != null) {
                    if (homeModel.isValue()) {
                        callback.onResult(homeModel.getData(), params.key + 1);
                    } else {
                        _isViewLoading.postValue(false);
                    }

                } else {
                    _isViewLoading.postValue(false);

                }
                Log.e(TAG, "after : " + params.key + " : " + FIRST_PAGE);

            }

            @Override
            public void onError(Throwable t) {
                _isViewLoading.postValue(false);
            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);

    }

}
