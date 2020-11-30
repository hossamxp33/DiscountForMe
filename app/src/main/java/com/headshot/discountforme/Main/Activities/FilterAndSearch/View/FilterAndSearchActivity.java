package com.headshot.discountforme.Main.Activities.FilterAndSearch.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.headshot.discountforme.Main.Activities.FilterAndSearch.ViewModel.FilterAndSSearchViewModel;
import com.headshot.discountforme.Main.Activities.Home.View.HomeActivity;
import com.headshot.discountforme.Main.Activities.Home.ViewModel.HomeViewModel;
import com.headshot.discountforme.Main.Adapters.BrandsAdapter;
import com.headshot.discountforme.Main.Adapters.CategoriesAdapter;
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
        homeViewModel = new ViewModelProvider(FilterAndSearchActivity.this).get(HomeViewModel.class);
        filterAndSSearchViewModel = new ViewModelProvider(FilterAndSearchActivity.this).get(FilterAndSSearchViewModel.class);
        if (getIntent().getStringExtra("type_go").equals("search")) {
            filterAndSSearchViewModel.getSearch(Constants.beforApiToken + sharedPrefManager.getUserDate().getToken(),
                    getIntent().getStringExtra("search"));
            filterAndSSearchViewModel.itemPagedList2.observe(FilterAndSearchActivity.this, new Observer<PagedList<Datum>>() {
                @Override
                public void onChanged(@Nullable PagedList<com.headshot.discountforme.Model.HomeModel.Datum> listBeans) {
                    binding.rvNotifications.hideShimmerAdapter();
                    homeAdapter.submitList(listBeans);
                    Log.v("size", listBeans.size() + "GOOD");
                }
            });

        } else {
            filterAndSSearchViewModel.getFilter(Constants.beforApiToken + sharedPrefManager.getUserDate().getToken(),
                    getIntent().getStringExtra("servicesId"), getIntent().getStringExtra("type"));
            filterAndSSearchViewModel.itemPagedList.observe(FilterAndSearchActivity.this, new Observer<PagedList<Datum>>() {
                @Override
                public void onChanged(@Nullable PagedList<com.headshot.discountforme.Model.HomeModel.Datum> listBeans) {
                    binding.rvNotifications.hideShimmerAdapter();
                    homeAdapter.submitList(listBeans);
                    Log.v("size", listBeans.size() + "GOOD");
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
        homeAdapter = new HomeAdapter(FilterAndSearchActivity.this, homeViewModel,
                Constants.beforApiToken + sharedPrefManager.getUserDate().getToken());
        LinearLayoutManager linearLayoutManager1 =
                new LinearLayoutManager(FilterAndSearchActivity.this, RecyclerView.VERTICAL, false);
        binding.rvNotifications.setLayoutManager(linearLayoutManager1);
        binding.rvNotifications.setAdapter(homeAdapter);


    }

}