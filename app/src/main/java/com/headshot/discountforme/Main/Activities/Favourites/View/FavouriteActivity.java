package com.headshot.discountforme.Main.Activities.Favourites.View;

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

import com.headshot.discountforme.Main.Activities.Favourites.ViewModel.FavouriteViewModel;
import com.headshot.discountforme.Main.Activities.FilterAndSearch.View.FilterAndSearchActivity;
import com.headshot.discountforme.Main.Adapters.FavouriteAdapter;
import com.headshot.discountforme.Main.Adapters.HomeAdapter;
import com.headshot.discountforme.Model.FavouritesModel.Datum;
import com.headshot.discountforme.R;
import com.headshot.discountforme.Utils.Constants;
import com.headshot.discountforme.Utils.ParentClass;
import com.headshot.discountforme.databinding.ActivityFavouriteBinding;

import spencerstudios.com.bungeelib.Bungee;

public class FavouriteActivity extends ParentClass {
    ActivityFavouriteBinding binding;
    FavouriteViewModel favouriteViewModel;
    FavouriteAdapter favouriteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavouriteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initUi();
        initEventDriven();
        handleDirection();
    }

    private void handleDirection() {
        if (getLang(FavouriteActivity.this).equals("ar")) {
            binding.ivBack.setImageResource(R.mipmap.back_ar);
        } else {
            binding.ivBack.setImageResource(R.mipmap.back);
        }


    }

    private void initEventDriven() {
        binding.ivBack.setOnClickListener(v -> {
            finish();
            Bungee.split(FavouriteActivity.this);
        });
    }

    private void initUi() {
        favouriteViewModel = new ViewModelProvider(FavouriteActivity.this).get(FavouriteViewModel.class);
        favouriteViewModel.getFavourite(Constants.beforApiToken + sharedPrefManager.getUserDate().getToken());
        favouriteViewModel.itemPagedList.observe(FavouriteActivity.this, new Observer<PagedList<Datum>>() {
            @Override
            public void onChanged(@Nullable PagedList<Datum> listBeans) {
                binding.rvFavourites.hideShimmerAdapter();
                favouriteAdapter.submitList(listBeans);
                Log.v("size", listBeans.size() + "GOOD");
            }
        });

        initRecycler();

    }

    private void initRecycler() {
        favouriteAdapter = new FavouriteAdapter(FavouriteActivity.this,
                Constants.beforApiToken + sharedPrefManager.getUserDate().getToken());
        LinearLayoutManager linearLayoutManager1 =
                new LinearLayoutManager(FavouriteActivity.this, RecyclerView.VERTICAL, false);
        binding.rvFavourites.setLayoutManager(linearLayoutManager1);
        binding.rvFavourites.setAdapter(favouriteAdapter);


    }
}