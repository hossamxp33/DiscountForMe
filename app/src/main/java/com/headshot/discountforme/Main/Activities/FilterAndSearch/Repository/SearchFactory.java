package com.headshot.discountforme.Main.Activities.FilterAndSearch.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.headshot.discountforme.Model.HomeModel.Datum;

public class SearchFactory extends DataSource.Factory {


    String token, search;
    private LiveData<Boolean> _isViewLoading;

    public LiveData<Boolean> isViewLoading() {
        return _isViewLoading;
    }

    public SearchFactory(String token, String search) {
        this.search = search;
        this.token = token;
        _isViewLoading = new MutableLiveData<>();
    }

    private MutableLiveData<PageKeyedDataSource<Integer, Datum>> homeDataSource = new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource create() {

        SearchDataSource itemDataSource = new SearchDataSource(token, search);
        homeDataSource.postValue(itemDataSource);
        _isViewLoading = itemDataSource.isViewLoading();
        return itemDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Datum>> getRestaurantDataSource() {
        return homeDataSource;
    }
}
