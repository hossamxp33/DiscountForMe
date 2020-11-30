package com.headshot.discountforme.Main.Activities.Deals.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.headshot.discountforme.Main.Activities.Home.Repository.HomeDataSource;
import com.headshot.discountforme.Model.HomeModel.Datum;

public class DealsFactory extends DataSource.Factory {

    private LiveData<Boolean> _isViewLoading;

    public LiveData<Boolean> isViewLoading() {
        return _isViewLoading;
    }

    public DealsFactory() {
        _isViewLoading = new MutableLiveData<>();
    }

    private MutableLiveData<PageKeyedDataSource<Integer, Datum>> homeDataSource = new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource create() {
        DealsDataSource itemDataSource = new DealsDataSource();
        homeDataSource.postValue(itemDataSource);
        _isViewLoading = itemDataSource.isViewLoading();
        return itemDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Datum>> getRestaurantDataSource() {
        return homeDataSource;
    }
}
