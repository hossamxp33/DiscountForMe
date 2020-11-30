package com.headshot.discountforme.Main.Activities.Favourites.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.headshot.discountforme.Main.Activities.Favourites.Repository.FavouriteFactory;
import com.headshot.discountforme.Main.Activities.FilterAndSearch.Repository.FilterAndSearchFactory;
import com.headshot.discountforme.Model.FavouritesModel.Datum;


public class FavouriteViewModel extends ViewModel {
    public LiveData<PagedList<Datum>> itemPagedList;
    public LiveData<PageKeyedDataSource<Integer, Datum>> liveDataSource;
    public LiveData<Boolean> liveDataViewLoading;

    public void getFavourite(String token) {

        liveDataViewLoading = new MutableLiveData<>();

        FavouriteFactory itemDataSourceFactory = new FavouriteFactory(token);
        liveDataSource = itemDataSourceFactory.getRestaurantDataSource();
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(10).build();

        liveDataViewLoading = itemDataSourceFactory.isViewLoading();


        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, pagedListConfig)).build();

    }
}
