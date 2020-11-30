package com.headshot.discountforme.Main.Activities.SuggestCoupon.View;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.headshot.discountforme.Main.Activities.Home.View.HomeActivity;
import com.headshot.discountforme.Main.Activities.SuggestCoupon.ViewModel.SuggestCouponViewModel;
import com.headshot.discountforme.Model.GeneralResponse.GeneralResponse;
import com.headshot.discountforme.R;
import com.headshot.discountforme.Utils.ParentClass;
import com.headshot.discountforme.databinding.ActivitySuggestCouponBinding;

import spencerstudios.com.bungeelib.Bungee;

public class SuggestCouponActivity extends ParentClass {
    ActivitySuggestCouponBinding binding;
    SuggestCouponViewModel suggestCouponViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySuggestCouponBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initUi();
        handleDirections();
    }

    private void handleDirections() {
        if (ParentClass.getLang(this).equals("ar")) {
            binding.etEmailAddress.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            binding.etUserName.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            binding.etNumber.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            binding.etBrand.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            binding.etCoupon.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            binding.etLocation.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            binding.ivBack.setImageResource(R.mipmap.back_ar);
        } else {
            binding.etEmailAddress.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            binding.etUserName.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            binding.etNumber.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            binding.etBrand.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            binding.etCoupon.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            binding.etLocation.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            binding.ivBack.setImageResource(R.mipmap.back);
        }
    }

    private void initUi() {
        dismiss_keyboard();
        suggestCouponViewModel = new ViewModelProvider(this).get(SuggestCouponViewModel.class);
        binding.etUserName.setText(sharedPrefManager.getUserDate().getName());
        binding.etEmailAddress.setText(sharedPrefManager.getUserDate().getEmail());
        binding.tvEnter.setOnClickListener(v -> {
            boolean cancel = false;
            View focusView = null;

            if (TextUtils.isEmpty(binding.etEmailAddress.getText().toString())) {
                binding.etEmailAddress.setError(SuggestCouponActivity.this.getString(R.string.emailAddress));
                focusView = binding.etEmailAddress;
                cancel = true;
            }

            if (TextUtils.isEmpty(binding.etUserName.getText().toString())) {
                binding.etUserName.setError(SuggestCouponActivity.this.getString(R.string.userName));
                focusView = binding.etUserName;
                cancel = true;
            }
            if (TextUtils.isEmpty(binding.etLocation.getText().toString())) {
                binding.etLocation.setError(SuggestCouponActivity.this.getString(R.string.location));
                focusView = binding.etLocation;
                cancel = true;
            }
            if (TextUtils.isEmpty(binding.etCoupon.getText().toString())) {
                binding.etCoupon.setError(SuggestCouponActivity.this.getString(R.string.coupon));
                focusView = binding.etCoupon;
                cancel = true;
            }
            if (TextUtils.isEmpty(binding.etBrand.getText().toString())) {
                binding.etBrand.setError(SuggestCouponActivity.this.getString(R.string.brand));
                focusView = binding.etBrand;
                cancel = true;
            }
            if (TextUtils.isEmpty(binding.etNumber.getText().toString())) {
                binding.etNumber.setError(SuggestCouponActivity.this.getString(R.string.whatsAppNumber));
                focusView = binding.etNumber;
                cancel = true;
            }

            if (cancel) {

            } else {
                login();
            }
        });

        binding.ivBack.setOnClickListener(v -> {
            finish();
            Bungee.split(SuggestCouponActivity.this);
        });
    }

    private void login() {
        showFlipDialog();
        suggestCouponViewModel.suggestCoupon(binding.etUserName.getText().toString(),binding.etLocation.getText().toString(),binding.etEmailAddress.getText().toString()
                ,binding.etNumber.getText().toString(),binding.etCoupon.getText().toString(),binding.etBrand.getText().toString())
                .observe(SuggestCouponActivity.this,new Observer<GeneralResponse>() {
                    @Override
                    public void onChanged(GeneralResponse userModel) {
                        if (userModel != null) {
                            if (userModel.getValue()) {
                                dismissFlipDialog();
                                Log.e("happenedHere","200");
                                Intent intent = new Intent(SuggestCouponActivity.this,HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(intent);
                                Bungee.split(SuggestCouponActivity.this);
                                makeSuccessToast(SuggestCouponActivity.this,userModel.getData());
                            } else {
                                Log.e("happenedHere","wrong");
                                dismissFlipDialog();
                                makeErrorToast(SuggestCouponActivity.this,userModel.getData());
                            }
                        } else {
                            dismissFlipDialog();
                            Log.e("happenedHere","500");
                            errorToast(SuggestCouponActivity.this,getString(R.string.somethingWentWrong));
                        }
                    }
                });
    }


}