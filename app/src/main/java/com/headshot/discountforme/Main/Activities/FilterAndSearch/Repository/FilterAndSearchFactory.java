package com.headshot.discountforme.Main.Activities.FilterAndSearch.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.headshot.discountforme.Model.HomeModel.Datum;

public class FilterAndSearchFactory extends DataSource.Factory {


    String token, categories, order_by;
    private LiveData<Boolean> _isViewLoading;
    Boolean isLogin;

    public LiveData<Boolean> isViewLoading() {
        return _isViewLoading;
    }

    public FilterAndSearchFactory(String token,String categories,String order_by,Boolean isLogin) {
        this.categories = categories;
        this.order_by = order_by;
        this.token = token;
        this.isLogin = isLogin;
        _isViewLoading = new MutableLiveData<>();
    }

    private MutableLiveData<PageKeyedDataSource<Integer, Datum>> homeDataSource = new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource create() {

        FilterAndSearchDataSource itemDataSource = new FilterAndSearchDataSource(token,categories,order_by,isLogin);
        homeDataSource.postValue(itemDataSource);
        _isViewLoading = itemDataSource.isViewLoading();
        return itemDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Datum>> getRestaurantDataSource() {
        return homeDataSource;
    }
}
