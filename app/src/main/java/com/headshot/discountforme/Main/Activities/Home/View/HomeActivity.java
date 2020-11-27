package com.headshot.discountforme.Main.Activities.Home.View;


import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;


import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.headshot.discountforme.Main.Activities.Home.ViewModel.HomeViewModel;
import com.headshot.discountforme.Main.Adapters.CategoriesAdapter;
import com.headshot.discountforme.Main.Adapters.HomeAdapter;
import com.headshot.discountforme.Model.CategoriesModel.Datum;
import com.headshot.discountforme.R;
import com.headshot.discountforme.Utils.Constants;
import com.headshot.discountforme.Utils.MenuListFragment.View.MenuListFragment;
import com.headshot.discountforme.Utils.ParentClass;
import com.headshot.discountforme.databinding.ActivityHomeBinding;
import com.headshot.discountforme.databinding.HomeNavBinding;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import java.util.List;
import java.util.Locale;

public class HomeActivity extends ParentClass {
    HomeNavBinding homeNavBinding;
    FlowingDrawer mDrawer;

    HomeViewModel homeViewModel;
    CategoriesAdapter categoriesAdapter;
    HomeAdapter homeAdapter;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeNavBinding = HomeNavBinding.inflate(getLayoutInflater());
        View view = homeNavBinding.getRoot();
        setContentView(view);
        if (getLang(this).equals("ar")) {
            homeNavBinding.drawerlayout.setVisibility(View.VISIBLE);
            homeNavBinding.drawerlayout2.setVisibility(View.GONE);
            mDrawer = homeNavBinding.drawerlayout;
        }
        if (getLang(this).equals("en")) {
            homeNavBinding.drawerlayout2.setVisibility(View.VISIBLE);
            homeNavBinding.drawerlayout.setVisibility(View.GONE);
            mDrawer = homeNavBinding.drawerlayout2;
        }
        setupMenu();
        initUi();
        handleDirections();
        initEventDriven();


    }

    private void initEventDriven() {
        activityHomeBinding().ivMenu.setOnClickListener(v -> {
            mDrawer.openMenu();
        });
    }

    private void handleDirections() {
        if (getLang(this).equals("ar")) {
            activityHomeBinding().etSearch.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        } else {
            activityHomeBinding().etSearch.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
        }
    }

    private void initUi() {
        dismiss_keyboard();
        homeViewModel = new ViewModelProvider(HomeActivity.this).get(HomeViewModel.class);
        homeViewModel.getCategories();
        homeViewModel.getCategoriesList().observe(HomeActivity.this, new Observer<List<Datum>>() {
            @Override
            public void onChanged(List<Datum> data) {
                if (data != null) {
                    activityHomeBinding().rvCategories.hideShimmerAdapter();
                    categoriesAdapter.addAll(homeViewModel.getCategoriesList().getValue());
                    categoriesAdapter.notifyDataSetChanged();
                    if (data.size() > 0) {
                        activityHomeBinding().rvCategories.setVisibility(View.VISIBLE);
                    } else {
                        activityHomeBinding().rvCategories.setVisibility(View.GONE);
                    }

                } else {
                    errorToast(HomeActivity.this, getString(R.string.somethingWentWrong));
                    activityHomeBinding().rvCategories.setVisibility(View.GONE);
                }
            }
        });

        homeViewModel.getHome("0", Constants.beforApiToken + sharedPrefManager.getUserDate().getToken());
        homeViewModel.itemPagedList.observe(HomeActivity.this, new Observer<PagedList<com.headshot.discountforme.Model.HomeModel.Datum>>() {
            @Override
            public void onChanged(@Nullable PagedList<com.headshot.discountforme.Model.HomeModel.Datum> listBeans) {
                activityHomeBinding().rvCoupons.hideShimmerAdapter();
                homeAdapter.submitList(listBeans);
                Log.v("size", listBeans.size() + "GOOD");
            }
        });


        initRecycler();

        mDrawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
            @Override
            public void onDrawerStateChange(int oldState, int newState) {
                if (newState == ElasticDrawer.STATE_CLOSED) {
                    Log.i("MainActivity", "Drawer STATE_CLOSED");
                }
            }

            @Override
            public void onDrawerSlide(float openRatio, int offsetPixels) {
                Log.i("MainActivity", "openRatio=" + openRatio + " ,offsetPixels=" + offsetPixels);
            }
        });

        if (mDrawer.isMenuVisible()) {
            mDrawer.closeMenu();
        }


    }

    public void setupMenu() {
        FragmentManager fm = getSupportFragmentManager();
        MenuListFragment mMenuFragment = (MenuListFragment) fm.findFragmentById(R.id.id_container_menu);
        if (mMenuFragment == null) {
            mMenuFragment = new MenuListFragment();
            fm.beginTransaction().add(R.id.id_container_menu, mMenuFragment).commit();
        }

    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isMenuVisible()) {
            mDrawer.closeMenu();
        } else {
            super.onBackPressed();
            finish();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setLocale();
    }

    private void setLocale() {
        Locale locale = new Locale(ParentClass.getLang(HomeActivity.this));
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDrawer.isMenuVisible()) {
            mDrawer.closeMenu();
        }
    }


    ActivityHomeBinding activityHomeBinding() {
        if (getLang(this).equals("ar")) {
            return homeNavBinding.home;

        } else {
            return homeNavBinding.home2;
        }

    }

    private void initRecycler() {
        homeAdapter = new HomeAdapter(HomeActivity.this, homeViewModel,
                Constants.beforApiToken + sharedPrefManager.getUserDate().getToken());
        LinearLayoutManager linearLayoutManager1 =
                new LinearLayoutManager(HomeActivity.this, RecyclerView.VERTICAL, false);
        activityHomeBinding().rvCoupons.setLayoutManager(linearLayoutManager1);
        activityHomeBinding().rvCoupons.setAdapter(homeAdapter);

        categoriesAdapter = new CategoriesAdapter(HomeActivity.this, activityHomeBinding().rvCategories,
                homeViewModel, Constants.beforApiToken + sharedPrefManager.getUserDate().getToken(), activityHomeBinding().relativeAll, activityHomeBinding().rvCoupons, homeAdapter);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(HomeActivity.this, RecyclerView.HORIZONTAL, false);
        activityHomeBinding().rvCategories.setLayoutManager(linearLayoutManager);
        activityHomeBinding().rvCategories.setAdapter(categoriesAdapter);

    }

}