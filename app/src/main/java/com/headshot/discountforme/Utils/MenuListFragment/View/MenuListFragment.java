
package com.headshot.discountforme.Utils.MenuListFragment.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.headshot.discountforme.R;
import com.headshot.discountforme.Utils.MenuListFragment.Presenter.MenuListFragmentPresenter;
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

    RelativeLayout relativeOffers;
    String deviceToken = "tokenToBeChanged";

    MenuListFragmentPresenter menuListFragmentPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMenuListBinding.inflate(inflater, container, false);
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
//        binding.layoutSideBarUser.relativeHome.setOnClickListener(v -> {
//            if (sharedPrefManager.getUserDate().getType().equals("client")) {
//                Intent intent = new Intent(getActivity(), HomeActivity.class);
//                startActivity(intent);
//                Bungee.split(getActivity());
//
//            } else {
//                Intent intent = new Intent(getActivity(), HomeDriverActivity.class);
//                startActivity(intent);
//                Bungee.split(getActivity());
//
//            }
//
//        });
//        binding.layoutSideBarUser.relativeProfile.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), ProfileUserActivity.class);
//            startActivity(intent);
//            Bungee.split(getActivity());
//        });
//
//        binding.layoutSideBarUser.relativeRequests.setOnClickListener(v -> {
//            if (sharedPrefManager.getUserDate().getType().equals("client")) {
//                Intent intent = new Intent(getActivity(), MyOrdersActivity.class);
//                startActivity(intent);
//                Bungee.split(getActivity());
//
//            } else {
//                Intent intent = new Intent(getActivity(), DriverOrdersActivity.class);
//                startActivity(intent);
//                Bungee.split(getActivity());
//
//            }
//        });
//
//        binding.layoutSideBarUser.relativeWallet.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), WalletActivity.class);
//            startActivity(intent);
//            Bungee.split(getActivity());
//        });
//
//        binding.layoutSideBarUser.relativeNotifications.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), NotificationsActivity.class);
//            startActivity(intent);
//            Bungee.split(getActivity());
//        });
//
//        binding.layoutSideBarUser.relativeMyAddresses.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), MyAddressesActivity.class);
//            startActivity(intent);
//            Bungee.split(getActivity());
//        });
//
//        binding.layoutSideBarUser.relativeAboutUs.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), AboutUsActivity.class);
//            startActivity(intent);
//            Bungee.split(getActivity());
//        });
//
//        binding.layoutSideBarUser.relativePrivacyPolicy.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), UsagePolicyActivity.class);
//            startActivity(intent);
//            Bungee.split(getActivity());
//        });
//
//        binding.layoutSideBarUser.relativeContactUs.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), ContactUsActivity.class);
//            startActivity(intent);
//            Bungee.split(getActivity());
//        });
//        binding.layoutSideBarUser.relativeLogout.setOnClickListener(v -> {
//            menuListFragmentPresenter.logout(deviceToken);
//        });
    }

    private void initUi() {
//        sharedPrefManager = new SharedPrefManager(getContext());
//        SharedPreferences prefs = getActivity().getSharedPreferences(Constants.mobileToken, MODE_PRIVATE);
//        if (!prefs.getString("m_token", "").equals("")) {
//            deviceToken = prefs.getString("m_token", "");
//        }
//        menuListFragmentPresenter = new MenuListFragmentPresenter(getActivity(), sharedPrefManager);
//
////        menuListFragmentPresenter = new MenuListFragmentPresenter(getActivity(),sharedPrefManager);
//
//        if (sharedPrefManager.getUserDate().getImage().equals("")) {
//            binding.header.ivLogo.setImageResource(R.drawable.splash);
//        } else {
//            ParentClass.LoadImageWithPicasso(sharedPrefManager.getUserDate().getImage(), getActivity(), binding.header.ivLogo);
//        }
//        binding.header.tvName.setText(sharedPrefManager.getUserDate().getName());
//
//
//        if (sharedPrefManager.getUserDate().getType().equals("client")) {
//            binding.layoutSideBarUser.relativeWallet.setVisibility(View.VISIBLE);
//            binding.layoutSideBarUser.relativeMyAddresses.setVisibility(View.VISIBLE);
//        } else {
//            binding.layoutSideBarUser.relativeWallet.setVisibility(View.GONE);
//            binding.layoutSideBarUser.relativeMyAddresses.setVisibility(View.GONE);
//        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
