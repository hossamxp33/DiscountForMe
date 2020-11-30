package com.headshot.discountforme.Main.Activities.Favourites.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.headshot.discountforme.Main.Activities.FilterAndSearch.Repository.SearchDataSource;
import com.headshot.discountforme.Model.FavouritesModel.Datum;

public class FavouriteFactory extends DataSource.Factory {


    String token, search;
    private LiveData<Boolean> _isViewLoading;

    public LiveData<Boolean> isViewLoading() {
        return _isViewLoading;
    }

    public FavouriteFactory(String token) {
        this.token = token;
        _isViewLoading = new MutableLiveData<>();
    }

    private MutableLiveData<PageKeyedDataSource<Integer, Datum>> homeDataSource = new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource create() {

        FavouriteDataSource itemDataSource = new FavouriteDataSource(token);
        homeDataSource.postValue(itemDataSource);
        _isViewLoading = itemDataSource.isViewLoading();
        return itemDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Datum>> getRestaurantDataSource() {
        return homeDataSource;
    }
}
