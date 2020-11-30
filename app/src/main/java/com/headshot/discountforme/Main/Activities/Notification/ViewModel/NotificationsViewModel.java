package com.headshot.discountforme.Main.Activities.Notification.ViewModel;


import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.headshot.discountforme.Main.Activities.Notification.Repository.NotificationsFactory;
import com.headshot.discountforme.Main.Activities.Notification.Repository.NotificationsRepository;
import com.headshot.discountforme.Model.NotificationModel.Datum;
import com.headshot.discountforme.Model.NotificationModel.NotificationModel;


public class NotificationsViewModel extends ViewModel {
    NotificationsRepository notificationsRepository;

    public LiveData<PagedList<Datum>> itemPagedList;
    public LiveData<PageKeyedDataSource<Integer, Datum>> liveDataSource;
    public LiveData<Boolean> liveDataViewLoading;



    public void getNotifications(String token) {

        liveDataViewLoading = new MutableLiveData<>();

        NotificationsFactory itemDataSourceFactory = new NotificationsFactory(token);
        liveDataSource = itemDataSourceFactory.getRestaurantDataSource();
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(10).build();

        liveDataViewLoading = itemDataSourceFactory.isViewLoading();


        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory,pagedListConfig)).build();

    }


    public LiveData<NotificationModel> delete_all_notifications(String token) {
        notificationsRepository = NotificationsRepository.getInstance();
        return notificationsRepository.delete_all_notifications(token);
    }
}

