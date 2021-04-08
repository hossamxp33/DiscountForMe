
package com.headshot.discountforme.Utils.MenuListFragment.View;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

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
    protected HomeActivity mActivity;

    Dialog dialogLogin;
    RelativeLayout rlCancel;
    RelativeLayout rlLogin;

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
            Intent intent = new Intent(mActivity,HomeActivity.class);
            startActivity(intent);
            Bungee.split(mActivity);

        });
        binding.header.relativeMenuHeader.setOnClickListener(v -> {
            if (sharedPrefManager.getLoginStatus()) {
                Intent intent = new Intent(mActivity,ProfileActivity.class);
                startActivity(intent);
                Bungee.split(mActivity);
            }


        });

        binding.layoutSideBarUser.relativeDeals.setOnClickListener(v -> {
            Intent intent = new Intent(mActivity,DealsActivity.class);
            startActivity(intent);
            Bungee.split(mActivity);
        });

        binding.layoutSideBarUser.relativeFavourites.setOnClickListener(v -> {
            if (sharedPrefManager.getLoginStatus()) {
                Intent intent = new Intent(mActivity,FavouriteActivity.class);
                startActivity(intent);
                Bungee.split(mActivity);
            } else {
                dialogLogin.show();
            }

        });

        binding.layoutSideBarUser.relativeNotifications.setOnClickListener(v -> {
            if (sharedPrefManager.getLoginStatus()) {
                Intent intent = new Intent(mActivity,NotificationsActivity.class);
                startActivity(intent);
                Bungee.split(mActivity);
            } else {
                dialogLogin.show();
            }

        });

        binding.layoutSideBarUser.relativeUsedCoupons.setOnClickListener(v -> {
            if (sharedPrefManager.getLoginStatus()) {
                Intent intent = new Intent(mActivity,UsedCouponsActivity.class);
                startActivity(intent);
                Bungee.split(mActivity);
            } else {
                dialogLogin.show();
            }

        });

        binding.layoutSideBarUser.relativeRateUs.setOnClickListener(v -> {
            try {
                mActivity.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=com.headshot.discountforme")));
            } catch (android.content.ActivityNotFoundException anfe) {
                mActivity.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=com.headshot.discountforme")));
            }
        });

        binding.layoutSideBarUser.relativeSuggestCoupon.setOnClickListener(v -> {
            Intent intent = new Intent(mActivity,SuggestCouponActivity.class);
            startActivity(intent);
            Bungee.split(mActivity);
        });

        binding.layoutSideBarUser.relativeHelp.setOnClickListener(v -> {
            Intent intent = new Intent(mActivity,ComplaintsActivity.class);
            startActivity(intent);
            Bungee.split(mActivity);
        });
        binding.layoutSideBarUser.relativeEnglish.setOnClickListener(v -> {
            ParentClass.storeLang("en",mActivity);
            Intent intent = new Intent(mActivity,HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });
        binding.layoutSideBarUser.rlArabic.setOnClickListener(v -> {
            ParentClass.storeLang("ar",mActivity);
            Intent intent = new Intent(mActivity,HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });
        binding.layoutSideBarUser.relativeLogout.setOnClickListener(v -> {
            if (sharedPrefManager.getLoginStatus()) {
                menuListFragmentPresenter.logout(deviceToken);
            } else {
                Intent intent = new Intent(mActivity,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }

        });
    }

    private void initUi() {
        sharedPrefManager = new SharedPrefManager(mActivity);
        SharedPreferences prefs = mActivity.getSharedPreferences(Constants.mobileToken,MODE_PRIVATE);
        if (!prefs.getString("m_token","").equals("")) {
            deviceToken = prefs.getString("m_token","");
        }
        menuListFragmentPresenter = new MenuListFragmentPresenter(mActivity,sharedPrefManager);
        if (sharedPrefManager.getLoginStatus()) {
            binding.header.tvName.setText(sharedPrefManager.getUserDate().getName());
            binding.header.tvEmail.setText(sharedPrefManager.getUserDate().getEmail());
        } else {
            binding.layoutSideBarUser.tvLogOut.setText(mActivity.getText(R.string.login));
            binding.header.ivEdit.setVisibility(View.GONE);
        }
        initDialog();


    }

    public void initDialog() {
        dialogLogin = new Dialog(mActivity);
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
                Intent intent = new Intent(mActivity,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                Bungee.split(mActivity);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mActivity = (HomeActivity) context;
        }
    }

}
