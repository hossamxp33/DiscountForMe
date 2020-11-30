package com.headshot.discountforme.Main.Activities.Notification.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.headshot.discountforme.Model.NotificationModel.Datum;
import com.headshot.discountforme.Utils.MainRequest;
import com.headshot.discountforme.Utils.SharedPrefManager;


public class NotificationsFactory extends DataSource.Factory {


    private String lat, lng, id;
    String token;
    private LiveData<Boolean> _isViewLoading;

    public LiveData<Boolean> isViewLoading() {
        return _isViewLoading;
    }

    public NotificationsFactory(String token) {
        this.token = token;
        _isViewLoading = new MutableLiveData<>();
    }

    private MutableLiveData<PageKeyedDataSource<Integer, Datum>> notificationsDataSource
            = new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource create() {
        NotificationsDataSource itemDataSource = new NotificationsDataSource(token);
        notificationsDataSource.postValue(itemDataSource);
        _isViewLoading = itemDataSource.isViewLoading();


        return itemDataSource;

    }

    public MutableLiveData<PageKeyedDataSource<Integer, Datum>> getRestaurantDataSource() {
        return notificationsDataSource;
    }
}
