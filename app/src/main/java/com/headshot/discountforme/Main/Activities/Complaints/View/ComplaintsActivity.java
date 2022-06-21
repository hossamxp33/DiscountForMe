package com.headshot.discountforme.Main.Activities.Complaints.View;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.headshot.discountforme.Main.Activities.Complaints.ViewModel.ComplaintsViewModel;
import com.headshot.discountforme.Main.Activities.Home.View.HomeActivity;
import com.headshot.discountforme.Model.GeneralResponse.GeneralResponse;
import com.headshot.discountforme.R;
import com.headshot.discountforme.Utils.ParentClass;
import com.headshot.discountforme.databinding.ActivityComplaintsBinding;

import spencerstudios.com.bungeelib.Bungee;

public class ComplaintsActivity extends ParentClass {
    ActivityComplaintsBinding binding;
    ComplaintsViewModel complaintsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityComplaintsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initUi();
        handleDirections();
    }

    private void handleDirections() {
        if (ParentClass.getLang(this).equals("ar")) {
            binding.etEmailAddress.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            binding.etComplaints.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            binding.etBrand.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            binding.etCoupon.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            binding.etLocation.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            binding.ivBack.setImageResource(R.mipmap.back_ar);
        } else {
            binding.etEmailAddress.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            binding.etComplaints.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            binding.etBrand.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            binding.etCoupon.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            binding.etLocation.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            binding.ivBack.setImageResource(R.mipmap.back);
        }
    }

    private void initUi() {
        dismiss_keyboard();
        complaintsViewModel = new ViewModelProvider(this).get(ComplaintsViewModel.class);
        binding.etEmailAddress.setText(sharedPrefManager.getUserDate().getEmail());
        binding.tvEnter.setOnClickListener(v -> {
            boolean cancel = false;
            View focusView = null;

            if (TextUtils.isEmpty(binding.etEmailAddress.getText().toString())) {
                binding.etEmailAddress.setError(ComplaintsActivity.this.getString(R.string.emailAddress));
                focusView = binding.etEmailAddress;
                cancel = true;
            }

            if (TextUtils.isEmpty(binding.etComplaints.getText().toString())) {
                binding.etComplaints.setError(ComplaintsActivity.this.getString(R.string.writeYourComplaints));
                focusView = binding.etComplaints;
                cancel = true;
            }
            if (TextUtils.isEmpty(binding.etLocation.getText().toString())) {
                binding.etLocation.setError(ComplaintsActivity.this.getString(R.string.location));
                focusView = binding.etLocation;
                cancel = true;
            }
            if (TextUtils.isEmpty(binding.etCoupon.getText().toString())) {
                binding.etCoupon.setError(ComplaintsActivity.this.getString(R.string.coupon));
                focusView = binding.etCoupon;
                cancel = true;
            }
            if (TextUtils.isEmpty(binding.etBrand.getText().toString())) {
                binding.etBrand.setError(ComplaintsActivity.this.getString(R.string.brand));
                focusView = binding.etBrand;
                cancel = true;
            }


            if (cancel) {

            } else {
                login();
            }
        });

        binding.ivBack.setOnClickListener(v -> {
            finish();
            Bungee.split(ComplaintsActivity.this);
        });
    }

    private void login() {
        showFlipDialog();
        complaintsViewModel.sendHelp(binding.etBrand.getText().toString(),binding.etEmailAddress.getText().toString(),binding.etLocation.getText().toString(),
                binding.etComplaints.getText().toString(),binding.etCoupon.getText().toString())
                .observe(ComplaintsActivity.this,new Observer<GeneralResponse>() {
                    @Override
                    public void onChanged(GeneralResponse userModel) {
                        if (userModel != null) {
                            if (userModel.getValue()) {
                                dismissFlipDialog();
                                Log.e("happenedHere","200");
                                Intent intent = new Intent(ComplaintsActivity.this, HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(intent);
                                Bungee.split(ComplaintsActivity.this);
                                makeSuccessToast(ComplaintsActivity.this,userModel.getData());
                            } else {
                                Log.e("happenedHere","wrong");
                                dismissFlipDialog();
                                makeErrorToast(ComplaintsActivity.this,userModel.getData());
                            }
                        } else {
                            dismissFlipDialog();
                            Log.e("happenedHere","500");
                            errorToast(ComplaintsActivity.this,getString(R.string.somethingWentWrong));
                        }
                    }
                });
    }


}