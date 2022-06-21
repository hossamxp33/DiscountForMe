package com.headshot.discountforme.Main.Activities.Notification.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.headshot.discountforme.Main.Activities.Home.View.HomeActivity;
import com.headshot.discountforme.Main.Activities.Notification.ViewModel.NotificationsViewModel;
import com.headshot.discountforme.Main.Adapters.NotificationsAdapter;
import com.headshot.discountforme.Model.NotificationModel.Datum;
import com.headshot.discountforme.Model.NotificationModel.NotificationModel;
import com.headshot.discountforme.R;
import com.headshot.discountforme.Utils.Constants;
import com.headshot.discountforme.Utils.ParentClass;
import com.headshot.discountforme.databinding.ActivityNotificationsBinding;

import spencerstudios.com.bungeelib.Bungee;

public class NotificationsActivity extends ParentClass {
    ActivityNotificationsBinding binding;
    NotificationsAdapter notificationsAdapter;
    private NotificationsViewModel notificationsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setUpViewModel();
        initUi();
        handleDirections();
        initEventDriven();
    }

    private void initUi() {
        binding.rvNotifications.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rvNotifications.setLayoutManager(linearLayoutManager);
        notificationsAdapter = new NotificationsAdapter(NotificationsActivity.this);
        binding.rvNotifications.setAdapter(notificationsAdapter);
    }

    private void initEventDriven() {
        binding.ivBack.setOnClickListener(v -> {
            finish();
            Bungee.split(NotificationsActivity.this);
        });
        binding.tvDeleteAll.setOnClickListener(v -> {
            deleteAllNotifications();
        });
    }

    private void handleDirections() {
        if (getLang(this).equals("ar")) {
            binding.ivBack.setImageResource(R.mipmap.back_ar);
        } else {
            binding.ivBack.setImageResource(R.mipmap.back);
        }
    }

    private void setUpViewModel() {
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        getNotifications();
        notificationsViewModel.liveDataViewLoading.observe(this,new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.e("WQ","Progress : " + aBoolean);
            }
        });

    } // fun of setUpViewModel

    private void getNotifications() {
        notificationsViewModel.getNotifications(Constants.beforApiToken + sharedPrefManager.getUserDate().getToken());

        binding.pbPagination.setVisibility(View.VISIBLE);

        notificationsViewModel.itemPagedList.observe(this,new Observer<PagedList<Datum>>() {
            @Override
            public void onChanged(@Nullable PagedList<Datum> listBeans) {
                binding.rvNotifications.hideShimmerAdapter();
                binding.pbPagination.setVisibility(View.GONE);
                notificationsAdapter.submitList(listBeans);
                Log.v("size",listBeans.size() + "GOOD");
            }
        });


    }

    private void deleteAllNotifications() {
        showFlipDialog();
        notificationsViewModel.delete_all_notifications(Constants.beforApiToken + sharedPrefManager.getUserDate().getToken()).observe(NotificationsActivity.this,
                new Observer<NotificationModel>() {
                    @Override
                    public void onChanged(NotificationModel notificationsModel) {
                        if (notificationsModel != null) {
                            if (notificationsModel.isValue()) {
                                Log.e("happenedHere","200");
                                dismissFlipDialog();
                                Intent intent = new Intent(NotificationsActivity.this, HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(intent);
                                Bungee.split(NotificationsActivity.this);


                            } else {
                                Log.e("happenedHere","wrong");
                                dismissFlipDialog();
                                errorToast(NotificationsActivity.this,getString(R.string.somethingWentWrong));
                            }
                        } else {
                            Log.e("happenedHere","500");
                            dismissFlipDialog();
                            errorToast(NotificationsActivity.this,getString(R.string.somethingWentWrong));
                        }
                    }
                });
    }


}