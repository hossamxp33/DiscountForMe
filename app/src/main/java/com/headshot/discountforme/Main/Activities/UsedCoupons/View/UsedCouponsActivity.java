package com.headshot.discountforme.Main.Activities.UsedCoupons.View;

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

import com.headshot.discountforme.Main.Activities.Favourites.View.FavouriteActivity;
import com.headshot.discountforme.Main.Activities.Favourites.ViewModel.FavouriteViewModel;
import com.headshot.discountforme.Main.Activities.UsedCoupons.ViewModel.UsedCouponViewModel;
import com.headshot.discountforme.Main.Adapters.FavouriteAdapter;
import com.headshot.discountforme.Model.FavouritesModel.Datum;
import com.headshot.discountforme.R;
import com.headshot.discountforme.Utils.Constants;
import com.headshot.discountforme.Utils.ParentClass;
import com.headshot.discountforme.databinding.ActivityUsedCouponsBinding;

import spencerstudios.com.bungeelib.Bungee;

public class UsedCouponsActivity extends ParentClass {
    ActivityUsedCouponsBinding binding;
    FavouriteAdapter favouriteAdapter;
    UsedCouponViewModel usedCouponViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsedCouponsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initUi();
        initEventDriven();
        handleDirection();

    }

    private void initUi() {
        usedCouponViewModel = new ViewModelProvider(UsedCouponsActivity.this).get(UsedCouponViewModel.class);
        usedCouponViewModel.getUsedCoupon(Constants.beforApiToken + sharedPrefManager.getUserDate().getToken());
        usedCouponViewModel.itemPagedList.observe(UsedCouponsActivity.this, new Observer<PagedList<Datum>>() {
            @Override
            public void onChanged(@Nullable PagedList<Datum> listBeans) {
                binding.rvFavourites.hideShimmerAdapter();
                favouriteAdapter.submitList(listBeans);
                Log.v("size", listBeans.size() + "GOOD");
            }
        });

        initRecycler();

    }

    private void initEventDriven() {
        binding.ivBack.setOnClickListener(v -> {
            finish();
            Bungee.split(UsedCouponsActivity.this);
        });
    }

    private void handleDirection() {
        if (getLang(UsedCouponsActivity.this).equals("ar")) {
            binding.ivBack.setImageResource(R.mipmap.back_ar);
        } else {
            binding.ivBack.setImageResource(R.mipmap.back);
        }


    }

    private void initRecycler() {
        favouriteAdapter = new FavouriteAdapter(UsedCouponsActivity.this,
                Constants.beforApiToken + sharedPrefManager.getUserDate().getToken());
        LinearLayoutManager linearLayoutManager1 =
                new LinearLayoutManager(UsedCouponsActivity.this, RecyclerView.VERTICAL, false);
        binding.rvFavourites.setLayoutManager(linearLayoutManager1);
        binding.rvFavourites.setAdapter(favouriteAdapter);


    }
}