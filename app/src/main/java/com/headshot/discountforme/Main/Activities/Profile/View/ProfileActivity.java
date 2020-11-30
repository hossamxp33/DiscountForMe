package com.headshot.discountforme.Main.Activities.Profile.View;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.headshot.discountforme.Main.Activities.ChangePassword.View.ChangePasswordActivity;
import com.headshot.discountforme.Main.Activities.Home.View.HomeActivity;
import com.headshot.discountforme.Main.Activities.Profile.ViewModel.ProfileViewModel;
import com.headshot.discountforme.Model.UserModel.UserModel;
import com.headshot.discountforme.R;
import com.headshot.discountforme.Utils.Constants;
import com.headshot.discountforme.Utils.ParentClass;
import com.headshot.discountforme.databinding.ActivityProfileBinding;

import spencerstudios.com.bungeelib.Bungee;

public class ProfileActivity extends ParentClass {
    ActivityProfileBinding binding;
    ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initUi();
        handleDirections();
    }

    private void handleDirections() {
        if (ParentClass.getLang(this).equals("ar")) {
            binding.etEmailAddress.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            binding.etUserName.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            binding.ivBack.setImageResource(R.mipmap.back_ar);
        } else {
            binding.etEmailAddress.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            binding.etUserName.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            binding.ivBack.setImageResource(R.mipmap.back);
        }
    }

    private void initUi() {
        dismiss_keyboard();
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding.etUserName.setText(sharedPrefManager.getUserDate().getName());
        binding.etEmailAddress.setText(sharedPrefManager.getUserDate().getEmail());
        binding.rlSave.setOnClickListener(v -> {
            boolean cancel = false;
            View focusView = null;

            if (TextUtils.isEmpty(binding.etEmailAddress.getText().toString())) {
                binding.etEmailAddress.setError(ProfileActivity.this.getString(R.string.emailAddress));
                focusView = binding.etEmailAddress;
                cancel = true;
            }

            if (TextUtils.isEmpty(binding.etUserName.getText().toString())) {
                binding.etUserName.setError(ProfileActivity.this.getString(R.string.userName));
                focusView = binding.etUserName;
                cancel = true;
            }

            if (cancel) {

            } else {
                login();
            }
        });

        binding.rlChangePassword.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this,ChangePasswordActivity.class);
            startActivity(intent);
            Bungee.split(ProfileActivity.this);
        });
        binding.ivBack.setOnClickListener(v -> {
            finish();
            Bungee.split(ProfileActivity.this);
        });
    }

    private void login() {
        showFlipDialog();
        profileViewModel.updateProfile(binding.etUserName.getText().toString(),binding.etEmailAddress.getText().toString(),
                Constants.beforApiToken + sharedPrefManager.getUserDate().getToken())
                .observe(ProfileActivity.this,new Observer<UserModel>() {
                    @Override
                    public void onChanged(UserModel userModel) {
                        if (userModel != null) {
                            if (userModel.isValue()) {
                                dismissFlipDialog();
                                Log.e("happenedHere","200");
                                sharedPrefManager.setUserDate(userModel.getData());
                                Intent intent = new Intent(ProfileActivity.this,HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(intent);
                                Bungee.split(ProfileActivity.this);
                                makeSuccessToast(ProfileActivity.this,getString(R.string.profileUpdatedSuccessfully));
                            } else {
                                Log.e("happenedHere","wrong");
                                dismissFlipDialog();
                                makeErrorToast(ProfileActivity.this,userModel.getMsg());
                            }
                        } else {
                            dismissFlipDialog();
                            Log.e("happenedHere","500");
                            errorToast(ProfileActivity.this,getString(R.string.somethingWentWrong));
                        }
                    }
                });
    }


}