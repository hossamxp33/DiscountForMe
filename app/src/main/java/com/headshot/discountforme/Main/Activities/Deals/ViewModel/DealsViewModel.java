package com.headshot.discountforme.Main.Activities.Deals.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.headshot.discountforme.Main.Activities.Deals.Repository.DealsFactory;
import com.headshot.discountforme.Main.Activities.Deals.Repository.DealsRepository;
import com.headshot.discountforme.Model.SliderModel.Datum;

import java.util.List;

public class DealsViewModel extends ViewModel {
    MutableLiveData<List<Datum>> slidersList;
    DealsRepository dealsRepository;

    public LiveData<PagedList<com.headshot.discountforme.Model.HomeModel.Datum>> itemPagedList;
    public LiveData<PageKeyedDataSource<Integer, com.headshot.discountforme.Model.HomeModel.Datum>> liveDataSource;
    public LiveData<Boolean> liveDataViewLoading;


    public void getSliders() {
        dealsRepository = DealsRepository.getInstance();
        slidersList = dealsRepository.getSliders();
    }

    public LiveData<List<Datum>> getSlidersList() {
        return slidersList;
    }



    public void getDeals() {

        liveDataViewLoading = new MutableLiveData<>();

        DealsFactory itemDataSourceFactory = new DealsFactory();
        liveDataSource = itemDataSourceFactory.getRestaurantDataSource();
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(10).build();

        liveDataViewLoading = itemDataSourceFactory.isViewLoading();


        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, pagedListConfig)).build();

    }


}
