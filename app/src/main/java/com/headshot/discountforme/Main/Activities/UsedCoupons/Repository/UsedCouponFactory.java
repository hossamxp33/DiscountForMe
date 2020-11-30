package com.headshot.discountforme.Main.Activities.UsedCoupons.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.headshot.discountforme.Model.FavouritesModel.Datum;

public class UsedCouponFactory extends DataSource.Factory {


    String token;
    private LiveData<Boolean> _isViewLoading;

    public LiveData<Boolean> isViewLoading() {
        return _isViewLoading;
    }

    public UsedCouponFactory(String token) {
        this.token = token;
        _isViewLoading = new MutableLiveData<>();
    }

    private MutableLiveData<PageKeyedDataSource<Integer, Datum>> homeDataSource = new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource create() {

        UsedCouponDataSource itemDataSource = new UsedCouponDataSource(token);
        homeDataSource.postValue(itemDataSource);
        _isViewLoading = itemDataSource.isViewLoading();
        return itemDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Datum>> getRestaurantDataSource() {
        return homeDataSource;
    }
}
