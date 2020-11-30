package com.headshot.discountforme.Main.Activities.Home.Repository;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.headshot.discountforme.Model.CategoriesModel.CategoriesModel;
import com.headshot.discountforme.Model.CategoriesModel.Datum;
import com.headshot.discountforme.Model.GeneralResponse.GeneralResponse;
import com.headshot.discountforme.Network.MainServices;
import com.headshot.discountforme.Utils.RetroWeb;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeRepository {
    private static HomeRepository homeRepository;

    public static HomeRepository getInstance() {
        if (homeRepository == null) {
            homeRepository = new HomeRepository();
        }
        return homeRepository;
    }

    public MutableLiveData<List<Datum>> getCategories() {
        MutableLiveData<List<Datum>> categoriesData = new MutableLiveData<>();

        Observable observable = RetroWeb.getClient().create(MainServices.class).brands()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<CategoriesModel> observer = new Observer<CategoriesModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(@NotNull CategoriesModel categoriesModel) {
                if (categoriesModel != null) {
                    if (categoriesModel.isValue()) {
                        categoriesData.setValue(categoriesModel.getData());
                    } else {
                        categoriesData.setValue(null);
                    }

                } else {
                    categoriesData.setValue(null);
                }
            }

            @Override
            public void onError(Throwable t) {
                categoriesData.setValue(null);
            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);
        return categoriesData;

    }

    public MutableLiveData<List<Datum>> getBrands() {
        MutableLiveData<List<Datum>> brandsData = new MutableLiveData<>();

        Observable observable = RetroWeb.getClient().create(MainServices.class).categories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<CategoriesModel> observer = new Observer<CategoriesModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(@NotNull CategoriesModel categoriesModel) {
                if (categoriesModel != null) {
                    if (categoriesModel.isValue()) {
                        brandsData.setValue(categoriesModel.getData());
                    } else {
                        brandsData.setValue(null);
                    }

                } else {
                    brandsData.setValue(null);
                }
            }

            @Override
            public void onError(Throwable t) {
                brandsData.setValue(null);
            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);
        return brandsData;

    }


    public MutableLiveData<GeneralResponse> useCoupon(String id, String token) {
        MutableLiveData<GeneralResponse> useCouponData = new MutableLiveData<>();

        Observable observable = RetroWeb.getClient().create(MainServices.class).use_coupon(token, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<GeneralResponse> observer = new Observer<GeneralResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(@NotNull GeneralResponse categoriesModel) {
                if (categoriesModel != null) {
                    if (categoriesModel.getValue()) {
                        useCouponData.setValue(categoriesModel);
                    } else {
                        useCouponData.setValue(categoriesModel);
                    }

                } else {
                    useCouponData.setValue(null);
                }
            }

            @Override
            public void onError(Throwable t) {
                useCouponData.setValue(null);
            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);
        return useCouponData;

    }

    public MutableLiveData<GeneralResponse> couponReview(String id, String status, String token) {
        MutableLiveData<GeneralResponse> couponReviewData = new MutableLiveData<>();

        Observable observable = RetroWeb.getClient().create(MainServices.class).coupon_review(token, id, status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<GeneralResponse> observer = new Observer<GeneralResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(@NotNull GeneralResponse categoriesModel) {
                if (categoriesModel != null) {
                    if (categoriesModel.getValue()) {
                        couponReviewData.setValue(categoriesModel);
                    } else {
                        couponReviewData.setValue(categoriesModel);
                    }

                } else {
                    couponReviewData.setValue(null);
                }
            }

            @Override
            public void onError(Throwable t) {
                couponReviewData.setValue(null);
            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);
        return couponReviewData;

    }


    public MutableLiveData<GeneralResponse> favouriteCoupon(String id, String token) {
        MutableLiveData<GeneralResponse> favouriteCouponData = new MutableLiveData<>();

        Observable observable = RetroWeb.getClient().create(MainServices.class).favourite_coupon(token, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<GeneralResponse> observer = new Observer<GeneralResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(@NotNull GeneralResponse categoriesModel) {
                if (categoriesModel != null) {
                    if (categoriesModel.getValue()) {
                        favouriteCouponData.setValue(categoriesModel);
                    } else {
                        favouriteCouponData.setValue(categoriesModel);
                    }

                } else {
                    favouriteCouponData.setValue(null);
                }
            }

            @Override
            public void onError(Throwable t) {
                favouriteCouponData.setValue(null);
            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);
        return favouriteCouponData;
    }


}
