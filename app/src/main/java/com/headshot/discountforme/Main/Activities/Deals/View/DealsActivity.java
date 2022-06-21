package com.headshot.discountforme.Main.Activities.Deals.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.headshot.discountforme.Main.Activities.Deals.ViewModel.DealsViewModel;
import com.headshot.discountforme.Main.Adapters.DealAdapter;
import com.headshot.discountforme.Main.Adapters.SliderAdapter;
import com.headshot.discountforme.Model.SliderModel.Datum;
import com.headshot.discountforme.R;
import com.headshot.discountforme.Utils.ParentClass;
import com.headshot.discountforme.databinding.ActivityDealsBinding;

import java.util.List;

import spencerstudios.com.bungeelib.Bungee;

public class DealsActivity extends ParentClass {
    ActivityDealsBinding binding;
    SliderAdapter sliderAdapter;
    DealsViewModel dealsViewModel;

    DealAdapter dealAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDealsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initUi();
        initEventDriven();
        handleDirection();

    }

    private void initUi() {
        dealsViewModel = new ViewModelProvider(DealsActivity.this).get(DealsViewModel.class);
        dealsViewModel.getSliders();
        dealsViewModel.getSlidersList().observe(DealsActivity.this, new Observer<List<Datum>>() {
            @Override
            public void onChanged(List<Datum> data) {
                binding.rvSlider.hideShimmerAdapter();
                Log.e("happened", data.size() + "GO");
                if (data != null) {
                    sliderAdapter.addAll(dealsViewModel.getSlidersList().getValue());
                    sliderAdapter.notifyDataSetChanged();
                    if (data.size() > 0) {
                        binding.rvSlider.setVisibility(View.VISIBLE);
                    } else {
                        binding.rvSlider.setVisibility(View.GONE);
                    }

                } else {
                    errorToast(DealsActivity.this, getString(R.string.somethingWentWrong));
                    binding.rvSlider.setVisibility(View.GONE);
                }
            }
        });

        dealsViewModel.getDeals();
        dealsViewModel.itemPagedList.observe(DealsActivity.this, new Observer<PagedList<com.headshot.discountforme.Model.HomeModel.Datum>>() {
            @Override
            public void onChanged(@Nullable PagedList<com.headshot.discountforme.Model.HomeModel.Datum> listBeans) {
                binding.rvDeals.hideShimmerAdapter();
                dealAdapter.submitList(listBeans);
                Log.v("size", listBeans.size() + "GOOD");
            }
        });
        initRecycler();
    }

    private void initEventDriven() {
        binding.ivBack.setOnClickListener(v -> {
            finish();
            Bungee.split(DealsActivity.this);
        });
    }

    private void handleDirection() {
        if (getLang(DealsActivity.this).equals("ar")) {
            binding.ivBack.setImageResource(R.mipmap.back_ar);
        } else {
            binding.ivBack.setImageResource(R.mipmap.back);
        }
    }

    private void initRecycler() {
        sliderAdapter = new SliderAdapter(DealsActivity.this, binding.rvSlider);
        LinearLayoutManager linearLayoutManager1 =
                new LinearLayoutManager(DealsActivity.this, RecyclerView.HORIZONTAL, false);
        binding.rvSlider.setLayoutManager(linearLayoutManager1);
        binding.rvSlider.setAdapter(sliderAdapter);


        dealAdapter = new DealAdapter(DealsActivity.this);
        LinearLayoutManager linearLayoutManager2 =
                new LinearLayoutManager(DealsActivity.this, RecyclerView.VERTICAL, false);
        binding.rvDeals.setLayoutManager(linearLayoutManager2);
        binding.rvDeals.setAdapter(dealAdapter);


    }
}