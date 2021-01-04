package com.headshot.discountforme.Main.Activities.Home.View;


import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.headshot.discountforme.Authentication.Login.View.LoginActivity;
import com.headshot.discountforme.Main.Activities.FilterAndSearch.View.FilterAndSearchActivity;
import com.headshot.discountforme.Main.Activities.Home.ViewModel.HomeViewModel;
import com.headshot.discountforme.Main.Adapters.BrandsAdapter;
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

import spencerstudios.com.bungeelib.Bungee;

public class HomeActivity extends ParentClass {
    HomeNavBinding homeNavBinding;
    FlowingDrawer mDrawer;
    HomeViewModel homeViewModel;
    CategoriesAdapter categoriesAdapter;
    HomeAdapter homeAdapter;
    BottomSheetDialog popFilter;
    RecyclerView rvCategories;
    RelativeLayout relativeHighestSale;
    TextView tvHighestSale;
    RelativeLayout rlAToZ;
    TextView tvAToZ;
    RelativeLayout rlZToA;
    TextView tvZToA;
    TextView tvSave;

    BrandsAdapter brandsAdapter;

    Boolean isLogin;
    Dialog dialogLogin;
    RelativeLayout rlCancel;
    RelativeLayout rlLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeNavBinding = HomeNavBinding.inflate(getLayoutInflater());
        View view = homeNavBinding.getRoot();
        setContentView(view);
        Log.e("lang",getLang(this));
        if (getLang(this).equals("ar")) {
            homeNavBinding.drawerlayout.setVisibility(View.VISIBLE);
            homeNavBinding.drawerlayout2.setVisibility(View.GONE);
            mDrawer = homeNavBinding.drawerlayout;
            setupMenu();
        }
        if (getLang(this).equals("en")) {
            homeNavBinding.drawerlayout2.setVisibility(View.VISIBLE);
            homeNavBinding.drawerlayout.setVisibility(View.GONE);
            mDrawer = homeNavBinding.drawerlayout2;
            setupMenu2();

        }
        Log.e("rf",homeNavBinding.drawerlayout2.getVisibility() + "GOODD");
        Log.e("rfff",homeNavBinding.drawerlayout.getVisibility() + "GOODD");

//        setupMenu();
        initUi();
        handleDirections();
        initEventDriven();


    }

    private void initEventDriven() {
        Log.e("token",sharedPrefManager.getUserDate().getToken() + "GOOD");
        activityHomeBinding().ivMenu.setOnClickListener(v -> {
            mDrawer.openMenu();
        });
        activityHomeBinding().ivFilter.setOnClickListener(v -> {
            popFilter.show();
        });
        activityHomeBinding().ivSearch.setOnClickListener(v -> {
            if (TextUtils.isEmpty(activityHomeBinding().etSearch.getText().toString())) {
                activityHomeBinding().etSearch.setError(getString(R.string.search));
            } else {
                Intent intent = new Intent(HomeActivity.this,FilterAndSearchActivity.class);
                intent.putExtra("type_go","search");
                intent.putExtra("search",activityHomeBinding().etSearch.getText().toString());
                startActivity(intent);
                Bungee.split(HomeActivity.this);
            }

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
        initDialog();
        isLogin = sharedPrefManager.getLoginStatus();
        homeViewModel = new ViewModelProvider(HomeActivity.this).get(HomeViewModel.class);
        homeViewModel.getCategories();
        homeViewModel.getCategoriesList().observe(HomeActivity.this,new Observer<List<Datum>>() {
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
                    errorToast(HomeActivity.this,getString(R.string.somethingWentWrong));
                    activityHomeBinding().rvCategories.setVisibility(View.GONE);
                }
            }
        });

        homeViewModel.getHome("0",Constants.beforApiToken + sharedPrefManager.getUserDate().getToken(),isLogin);
        homeViewModel.itemPagedList.observe(HomeActivity.this,new Observer<PagedList<com.headshot.discountforme.Model.HomeModel.Datum>>() {
            @Override
            public void onChanged(@Nullable PagedList<com.headshot.discountforme.Model.HomeModel.Datum> listBeans) {
                activityHomeBinding().rvCoupons.hideShimmerAdapter();
                homeAdapter.submitList(listBeans);
                Log.v("size",listBeans.size() + "GOOD");
            }
        });
//    RecyclerView rvCategories;
//    RelativeLayout relativeHighestSale;
//    TextView tvHighestSale;
//    RelativeLayout rlAToZ;
//    TextView tvAToZ;
//    RelativeLayout rlZToA;
//    TextView tvZToA;
        popFilter = new BottomSheetDialog(HomeActivity.this,R.style.AppBottomSheetDialogTheme);
        popFilter.setContentView(R.layout.popup_filter);
        rvCategories = (RecyclerView) popFilter.findViewById(R.id.rvCategories);
        relativeHighestSale = (RelativeLayout) popFilter.findViewById(R.id.relativeHighestSale);
        tvHighestSale = (TextView) popFilter.findViewById(R.id.tvHighestSale);
        rlAToZ = (RelativeLayout) popFilter.findViewById(R.id.rlAToZ);
        tvAToZ = (TextView) popFilter.findViewById(R.id.tvAToZ);
        rlZToA = (RelativeLayout) popFilter.findViewById(R.id.rlZToA);
        tvZToA = (TextView) popFilter.findViewById(R.id.tvZToA);
        tvSave = (TextView) popFilter.findViewById(R.id.tvSave);

        homeViewModel.getBrands();
        homeViewModel.getBrandsList().observe(HomeActivity.this,new Observer<List<Datum>>() {
            @Override
            public void onChanged(List<Datum> data) {
                if (data != null) {
                    brandsAdapter.addAll(homeViewModel.getBrandsList().getValue());
                    brandsAdapter.notifyDataSetChanged();
                    if (data.size() > 0) {
                        rvCategories.setVisibility(View.VISIBLE);
                    } else {
                        rvCategories.setVisibility(View.GONE);
                    }

                } else {
                    errorToast(HomeActivity.this,getString(R.string.somethingWentWrong));
                    rvCategories.setVisibility(View.GONE);
                }
            }
        });

        initRecycler();

        mDrawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
            @Override
            public void onDrawerStateChange(int oldState,int newState) {
                if (newState == ElasticDrawer.STATE_CLOSED) {
                    Log.i("MainActivity","Drawer STATE_CLOSED");
                }
            }

            @Override
            public void onDrawerSlide(float openRatio,int offsetPixels) {
                Log.i("MainActivity","openRatio=" + openRatio + " ,offsetPixels=" + offsetPixels);
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
            fm.beginTransaction().add(R.id.id_container_menu,mMenuFragment).commit();
        }

    }

    public void setupMenu2() {
        FragmentManager fm = getSupportFragmentManager();
        MenuListFragment mMenuFragment = (MenuListFragment) fm.findFragmentById(R.id.id_container_menu2);
        if (mMenuFragment == null) {
            mMenuFragment = new MenuListFragment();
            fm.beginTransaction().add(R.id.id_container_menu2,mMenuFragment).commit();
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
        homeAdapter = new HomeAdapter(HomeActivity.this,homeViewModel,
                Constants.beforApiToken + sharedPrefManager.getUserDate().getToken(),isLogin,dialogLogin);
        LinearLayoutManager linearLayoutManager1 =
                new LinearLayoutManager(HomeActivity.this,RecyclerView.VERTICAL,false);
        activityHomeBinding().rvCoupons.setLayoutManager(linearLayoutManager1);
        activityHomeBinding().rvCoupons.setAdapter(homeAdapter);


        brandsAdapter = new BrandsAdapter(HomeActivity.this,
                Constants.beforApiToken +
                        sharedPrefManager.getUserDate().getToken(),tvSave,relativeHighestSale,tvHighestSale,rlAToZ,tvAToZ,rlZToA,tvZToA);
        LinearLayoutManager linearLayoutManager2 =
                new LinearLayoutManager(HomeActivity.this,RecyclerView.HORIZONTAL,false);
        rvCategories.setLayoutManager(linearLayoutManager2);
        rvCategories.setAdapter(brandsAdapter);


        categoriesAdapter = new CategoriesAdapter(HomeActivity.this,activityHomeBinding().rvCategories,
                homeViewModel,Constants.beforApiToken + sharedPrefManager.getUserDate().getToken(),activityHomeBinding().relativeAll,
                activityHomeBinding().rvCoupons,homeAdapter,isLogin);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(HomeActivity.this,RecyclerView.HORIZONTAL,false);
        activityHomeBinding().rvCategories.setLayoutManager(linearLayoutManager);
        activityHomeBinding().rvCategories.setAdapter(categoriesAdapter);

    }

    public void initDialog() {
        dialogLogin = new Dialog(HomeActivity.this);
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
                Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                Bungee.split(HomeActivity.this);
            }
        });
    }

}