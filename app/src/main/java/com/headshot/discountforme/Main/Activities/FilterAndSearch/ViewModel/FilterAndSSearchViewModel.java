package com.headshot.discountforme.Main.Activities.FilterAndSearch.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.headshot.discountforme.Main.Activities.FilterAndSearch.Repository.FilterAndSearchFactory;
import com.headshot.discountforme.Main.Activities.FilterAndSearch.Repository.SearchFactory;
import com.headshot.discountforme.Model.HomeModel.Datum;

public class FilterAndSSearchViewModel extends ViewModel {
    public LiveData<PagedList<Datum>> itemPagedList;
    public LiveData<PageKeyedDataSource<Integer, Datum>> liveDataSource;
    public LiveData<Boolean> liveDataViewLoading;


    public LiveData<PagedList<Datum>> itemPagedList2;
    public LiveData<PageKeyedDataSource<Integer, Datum>> liveDataSource2;
    public LiveData<Boolean> liveDataViewLoading2;

    public void getFilter(String token, String categories, String order_by) {

        liveDataViewLoading = new MutableLiveData<>();

        FilterAndSearchFactory itemDataSourceFactory = new FilterAndSearchFactory(token, categories, order_by);
        liveDataSource = itemDataSourceFactory.getRestaurantDataSource();
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(10).build();

        liveDataViewLoading = itemDataSourceFactory.isViewLoading();


        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, pagedListConfig)).build();

    }

    public void getSearch(String token, String search) {

        liveDataViewLoading2 = new MutableLiveData<>();

        SearchFactory itemDataSourceFactory = new SearchFactory(token, search);
        liveDataSource2 = itemDataSourceFactory.getRestaurantDataSource();
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(10).build();

        liveDataViewLoading2 = itemDataSourceFactory.isViewLoading();


        itemPagedList2 = (new LivePagedListBuilder(itemDataSourceFactory, pagedListConfig)).build();

    }


}
