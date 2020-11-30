package com.headshot.discountforme.Main.Activities.Deals.Repository;


import androidx.lifecycle.MutableLiveData;

import com.headshot.discountforme.Model.CategoriesModel.CategoriesModel;
import com.headshot.discountforme.Model.SliderModel.Datum;
import com.headshot.discountforme.Model.SliderModel.SliderModel;
import com.headshot.discountforme.Network.MainServices;
import com.headshot.discountforme.Utils.RetroWeb;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DealsRepository {
    private static DealsRepository dealsRepository;

    public static DealsRepository getInstance() {
        if (dealsRepository == null) {
            dealsRepository = new DealsRepository();
        }
        return dealsRepository;
    }

    public MutableLiveData<List<Datum>> getSliders() {
        MutableLiveData<List<Datum>> sliderData = new MutableLiveData<>();

        Observable observable = RetroWeb.getClient().create(MainServices.class).offers_sliders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<SliderModel> observer = new Observer<SliderModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(@NotNull SliderModel categoriesModel) {
                if (categoriesModel != null) {
                    if (categoriesModel.isValue()) {
                        sliderData.setValue(categoriesModel.getData());
                    } else {
                        sliderData.setValue(null);
                    }

                } else {
                    sliderData.setValue(null);
                }
            }

            @Override
            public void onError(Throwable t) {
                sliderData.setValue(null);
            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);
        return sliderData;

    }


}
