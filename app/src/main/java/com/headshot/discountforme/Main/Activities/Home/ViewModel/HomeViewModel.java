package com.headshot.discountforme.Main.Activities.Home.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.headshot.discountforme.Main.Activities.Home.Repository.HomeFactory;
import com.headshot.discountforme.Main.Activities.Home.Repository.HomeRepository;
import com.headshot.discountforme.Model.CategoriesModel.Datum;
import com.headshot.discountforme.Model.GeneralResponse.GeneralResponse;

import java.util.List;


public class HomeViewModel extends ViewModel {
    MutableLiveData<List<Datum>> categoriesList;
    MutableLiveData<List<Datum>> brandsList;
    HomeRepository homeRepository;


    public LiveData<PagedList<com.headshot.discountforme.Model.HomeModel.Datum>> itemPagedList;
    public LiveData<PageKeyedDataSource<Integer, com.headshot.discountforme.Model.HomeModel.Datum>> liveDataSource;
    public LiveData<Boolean> liveDataViewLoading;


    public void getCategories() {
        homeRepository = HomeRepository.getInstance();
        categoriesList = homeRepository.getCategories();
    }

    public void getBrands() {
        homeRepository = HomeRepository.getInstance();
        brandsList = homeRepository.getBrands();
    }

    public LiveData<List<Datum>> getBrandsList() {
        return brandsList;
    }

    public LiveData<List<Datum>> getCategoriesList() {
        return categoriesList;
    }


    public void getHome(String id,String token,Boolean isLogin) {

        liveDataViewLoading = new MutableLiveData<>();

        HomeFactory itemDataSourceFactory = new HomeFactory(id,token,isLogin);
        liveDataSource = itemDataSourceFactory.getRestaurantDataSource();
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(10).build();

        liveDataViewLoading = itemDataSourceFactory.isViewLoading();


        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory,pagedListConfig)).build();

    }

    public LiveData<GeneralResponse> useCoupon(String id,String token) {
        homeRepository = HomeRepository.getInstance();
        return homeRepository.useCoupon(id,token);
    }

    public LiveData<GeneralResponse> couponReview(String id,String status,String token) {
        homeRepository = HomeRepository.getInstance();
        return homeRepository.couponReview(id,status,token);
    }

    public LiveData<GeneralResponse> favouriteCoupon(String id,String token) {
        homeRepository = HomeRepository.getInstance();
        return homeRepository.favouriteCoupon(id,token);
    }

}