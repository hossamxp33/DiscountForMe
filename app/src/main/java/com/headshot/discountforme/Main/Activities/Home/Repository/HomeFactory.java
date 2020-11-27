package com.headshot.discountforme.Main.Activities.Home.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.headshot.discountforme.Model.HomeModel.Datum;

public class HomeFactory extends DataSource.Factory {


    String id,token;
    private LiveData<Boolean> _isViewLoading;

    public LiveData<Boolean> isViewLoading() {
        return _isViewLoading;
    }

    public HomeFactory(String id,String token) {
        this.id = id;
        this.token = token;
        _isViewLoading = new MutableLiveData<>();
    }

    private MutableLiveData<PageKeyedDataSource<Integer, Datum>> homeDataSource = new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource create() {

        HomeDataSource itemDataSource = new HomeDataSource(id,token);
        homeDataSource.postValue(itemDataSource);
        _isViewLoading = itemDataSource.isViewLoading();

        return itemDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Datum>> getRestaurantDataSource() {
        return homeDataSource;
    }
}
