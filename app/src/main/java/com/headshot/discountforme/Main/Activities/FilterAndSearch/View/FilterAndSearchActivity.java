package com.headshot.discountforme.Main.Activities.FilterAndSearch.View;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.headshot.discountforme.Authentication.Login.View.LoginActivity;
import com.headshot.discountforme.Main.Activities.FilterAndSearch.ViewModel.FilterAndSSearchViewModel;
import com.headshot.discountforme.Main.Activities.Home.ViewModel.HomeViewModel;
import com.headshot.discountforme.Main.Adapters.HomeAdapter;
import com.headshot.discountforme.Model.HomeModel.Datum;
import com.headshot.discountforme.R;
import com.headshot.discountforme.Utils.Constants;
import com.headshot.discountforme.Utils.ParentClass;
import com.headshot.discountforme.databinding.ActivityFilterAndSearchBinding;

import spencerstudios.com.bungeelib.Bungee;

public class FilterAndSearchActivity extends ParentClass {
    ActivityFilterAndSearchBinding binding;
    HomeViewModel homeViewModel;
    FilterAndSSearchViewModel filterAndSSearchViewModel;
    HomeAdapter homeAdapter;
    Boolean isLogin;

    Dialog dialogLogin;
    RelativeLayout rlCancel;
    RelativeLayout rlLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFilterAndSearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        handleDirections();
        initUi();
        initEventDriven();

    }

    private void initEventDriven() {
        binding.ivBack.setOnClickListener(v -> {
            finish();
            Bungee.split(FilterAndSearchActivity.this);
        });
    }

    private void initUi() {
        initDialog();
        isLogin = sharedPrefManager.getLoginStatus();
        homeViewModel = new ViewModelProvider(FilterAndSearchActivity.this).get(HomeViewModel.class);
        filterAndSSearchViewModel = new ViewModelProvider(FilterAndSearchActivity.this).get(FilterAndSSearchViewModel.class);
        if (getIntent().getStringExtra("type_go").equals("search")) {
            filterAndSSearchViewModel.getSearch(Constants.beforApiToken + sharedPrefManager.getUserDate().getToken(),
                    getIntent().getStringExtra("search"),isLogin);
            filterAndSSearchViewModel.itemPagedList2.observe(FilterAndSearchActivity.this,new Observer<PagedList<Datum>>() {
                @Override
                public void onChanged(@Nullable PagedList<com.headshot.discountforme.Model.HomeModel.Datum> listBeans) {
                    binding.rvNotifications.hideShimmerAdapter();
                    homeAdapter.submitList(listBeans);
                    Log.v("size",listBeans.size() + "GOOD");
                }
            });

        } else {
            filterAndSSearchViewModel.getFilter(Constants.beforApiToken + sharedPrefManager.getUserDate().getToken(),
                    getIntent().getStringExtra("servicesId"),getIntent().getStringExtra("type"),isLogin);
            filterAndSSearchViewModel.itemPagedList.observe(FilterAndSearchActivity.this,new Observer<PagedList<Datum>>() {
                @Override
                public void onChanged(@Nullable PagedList<com.headshot.discountforme.Model.HomeModel.Datum> listBeans) {
                    binding.rvNotifications.hideShimmerAdapter();
                    homeAdapter.submitList(listBeans);
                    Log.v("size",listBeans.size() + "GOOD");
                }
            });
        }

        initRecycler();
    }

    private void handleDirections() {
        if (getLang(FilterAndSearchActivity.this).equals("ar")) {
            binding.ivBack.setImageResource(R.mipmap.back_ar);
        } else {
            binding.ivBack.setImageResource(R.mipmap.back);

        }

    }

    private void initRecycler() {
        homeAdapter = new HomeAdapter(FilterAndSearchActivity.this,homeViewModel,
                Constants.beforApiToken + sharedPrefManager.getUserDate().getToken(),isLogin,dialogLogin);
        LinearLayoutManager linearLayoutManager1 =
                new LinearLayoutManager(FilterAndSearchActivity.this,RecyclerView.VERTICAL,false);
        binding.rvNotifications.setLayoutManager(linearLayoutManager1);
        binding.rvNotifications.setAdapter(homeAdapter);


    }

    public void initDialog() {
        dialogLogin = new Dialog(FilterAndSearchActivity.this);
        dialogLogin.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogLogin.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogLogin.setContentView(R.layout.pop_up_login);
        WindowManager.LayoutParams lp1 = new WindowManager.LayoutParams();
        lp1.copyFrom(dialogLogin.getWindow().getAttributes());
        lp1.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp1.height = WindowManager.LayoutParams.WRAP_CONTENT;
        rlCancel = dialogLogin.findViewById(R.id.rlCancel);
        rlLogin = dialogLogin.findViewById(R.id.rlLogin);
        rlCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLogin.dismiss();
            }
        });
        rlLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLogin.dismiss();
                Intent intent = new Intent(FilterAndSearchActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                Bungee.split(FilterAndSearchActivity.this);
            }
        });
    }

}