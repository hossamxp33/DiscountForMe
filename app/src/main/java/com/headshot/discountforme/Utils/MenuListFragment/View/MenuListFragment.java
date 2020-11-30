
package com.headshot.discountforme.Utils.MenuListFragment.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.headshot.discountforme.Authentication.Login.View.LoginActivity;
import com.headshot.discountforme.Main.Activities.Complaints.View.ComplaintsActivity;
import com.headshot.discountforme.Main.Activities.Deals.View.DealsActivity;
import com.headshot.discountforme.Main.Activities.Favourites.View.FavouriteActivity;
import com.headshot.discountforme.Main.Activities.Home.View.HomeActivity;
import com.headshot.discountforme.Main.Activities.Notification.View.NotificationsActivity;
import com.headshot.discountforme.Main.Activities.Profile.View.ProfileActivity;
import com.headshot.discountforme.Main.Activities.SuggestCoupon.View.SuggestCouponActivity;
import com.headshot.discountforme.Main.Activities.UsedCoupons.View.UsedCouponsActivity;
import com.headshot.discountforme.R;
import com.headshot.discountforme.Utils.Constants;
import com.headshot.discountforme.Utils.MenuListFragment.Presenter.MenuListFragmentPresenter;
import com.headshot.discountforme.Utils.ParentClass;
import com.headshot.discountforme.Utils.SharedPrefManager;
import com.headshot.discountforme.databinding.FragmentMenuListBinding;

import spencerstudios.com.bungeelib.Bungee;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by mxn on 2016/12/13.
 * MenuListFragment
 */

public class MenuListFragment extends Fragment {
    private FragmentMenuListBinding binding;
    SharedPrefManager sharedPrefManager;
    String deviceToken = "tokenToBeChanged";
    MenuListFragmentPresenter menuListFragmentPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        binding = FragmentMenuListBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        initUi();

        NavigationView vNavigation = (NavigationView) view.findViewById(R.id.nav_view);
        vNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
//                Toast.makeText(getActivity(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        handelClicks();
        return view;
    }

    private void handelClicks() {
        binding.layoutSideBarUser.relativeHome.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),HomeActivity.class);
            startActivity(intent);
            Bungee.split(getActivity());

        });
        binding.header.relativeMenuHeader.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),ProfileActivity.class);
            startActivity(intent);
            Bungee.split(getActivity());
        });

        binding.layoutSideBarUser.relativeDeals.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),DealsActivity.class);
            startActivity(intent);
            Bungee.split(getActivity());
        });

        binding.layoutSideBarUser.relativeFavourites.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),FavouriteActivity.class);
            startActivity(intent);
            Bungee.split(getActivity());
        });

        binding.layoutSideBarUser.relativeNotifications.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),NotificationsActivity.class);
            startActivity(intent);
            Bungee.split(getActivity());
        });

        binding.layoutSideBarUser.relativeUsedCoupons.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),UsedCouponsActivity.class);
            startActivity(intent);
            Bungee.split(getActivity());
        });

//        binding.layoutSideBarUser.relativeRateUs.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(),.class);
//            startActivity(intent);
//            Bungee.split(getActivity());
//        });

        binding.layoutSideBarUser.relativeSuggestCoupon.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),SuggestCouponActivity.class);
            startActivity(intent);
            Bungee.split(getActivity());
        });

        binding.layoutSideBarUser.relativeHelp.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),ComplaintsActivity.class);
            startActivity(intent);
            Bungee.split(getActivity());
        });
        binding.layoutSideBarUser.relativeEnglish.setOnClickListener(v -> {
            ParentClass.storeLang("en",getActivity());
            Intent intent = new Intent(getActivity(),HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });
        binding.layoutSideBarUser.rlArabic.setOnClickListener(v -> {
            ParentClass.storeLang("ar",getActivity());
            Intent intent = new Intent(getActivity(),HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });
        binding.layoutSideBarUser.relativeLogout.setOnClickListener(v -> {
            menuListFragmentPresenter.logout(deviceToken);
        });
    }

    private void initUi() {
        sharedPrefManager = new SharedPrefManager(getContext());
        SharedPreferences prefs = getActivity().getSharedPreferences(Constants.mobileToken,MODE_PRIVATE);
        if (!prefs.getString("m_token","").equals("")) {
            deviceToken = prefs.getString("m_token","");
        }
        menuListFragmentPresenter = new MenuListFragmentPresenter(getActivity(),sharedPrefManager);
        binding.header.tvName.setText(sharedPrefManager.getUserDate().getName());
        binding.header.tvEmail.setText(sharedPrefManager.getUserDate().getEmail());

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
