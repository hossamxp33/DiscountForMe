package com.headshot.discountforme.Authentication.ForgetPasswordStepThree.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.headshot.discountforme.Authentication.ForgetPasswordStepThree.ViewModel.ForgetPasswordStepThreeViewModel;
import com.headshot.discountforme.Main.Activities.Home.View.HomeActivity;
import com.headshot.discountforme.Model.UserModel.UserModel;
import com.headshot.discountforme.R;
import com.headshot.discountforme.Utils.Constants;
import com.headshot.discountforme.Utils.ParentClass;
import com.headshot.discountforme.databinding.ActivityForgetPasswordStepThreeBinding;

import spencerstudios.com.bungeelib.Bungee;

public class ForgetPasswordStepThreeActivity extends ParentClass {
    ActivityForgetPasswordStepThreeBinding binding;
    ForgetPasswordStepThreeViewModel forgetPasswordStepThreeViewModel;
    String deviceToken = "tokenToBeChanged";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetPasswordStepThreeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initUi();
        handleDirections();
    }

    private void handleDirections() {
        if (ParentClass.getLang(this).equals("ar")) {
            binding.etPassword.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            binding.ivBack.setImageResource(R.mipmap.back_ar);
        } else {
            binding.etPassword.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            binding.ivBack.setImageResource(R.mipmap.back);
        }
    }

    private void initUi() {
        dismiss_keyboard();
        forgetPasswordStepThreeViewModel = new ViewModelProvider(this).get(ForgetPasswordStepThreeViewModel.class);
        SharedPreferences prefs = getSharedPreferences(Constants.mobileToken,MODE_PRIVATE);
        if (!prefs.getString("m_token","").equals("")) {
            deviceToken = prefs.getString("m_token","");
        }
        Log.e("deviceToken",deviceToken + "GOOD");
        binding.tvEnter.setOnClickListener(v -> {
            boolean cancel = false;
            View focusView = null;


            if (TextUtils.isEmpty(binding.etPassword.getText().toString())) {
                binding.etPassword.setError(ForgetPasswordStepThreeActivity.this.getString(R.string.password));
                focusView = binding.etPassword;
                cancel = true;
            }

            if (cancel) {

            } else {
                login();
            }
        });

        binding.ivBack.setOnClickListener(v -> {
            finish();
            Bungee.split(ForgetPasswordStepThreeActivity.this);
        });
    }

    private void login() {
        showFlipDialog();
        forgetPasswordStepThreeViewModel.changePassword(getIntent().getStringExtra("email"),binding.etPassword.getText().toString(),deviceToken)
                .observe(ForgetPasswordStepThreeActivity.this,new Observer<UserModel>() {
                    @Override
                    public void onChanged(UserModel userModel) {
                        if (userModel != null) {
                            if (userModel.isValue()) {
                                dismissFlipDialog();
                                Log.e("happenedHere","200");
                                sharedPrefManager.setLoginStatus(true);
                                sharedPrefManager.setUserDate(userModel.getData());
                                Intent intent = new Intent(ForgetPasswordStepThreeActivity.this, HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(intent);
                                Bungee.split(ForgetPasswordStepThreeActivity.this);

                            } else {
                                Log.e("happenedHere","wrong");
                                dismissFlipDialog();
                                makeErrorToast(ForgetPasswordStepThreeActivity.this,getString(R.string.somethingWentWrong));
                            }
                        } else {
                            dismissFlipDialog();
                            Log.e("happenedHere","500");
                            errorToast(ForgetPasswordStepThreeActivity.this,getString(R.string.somethingWentWrong));
                        }
                    }
                });
    }

}