package com.headshot.discountforme.Authentication.ForgetPasswordStepOne.View;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.headshot.discountforme.Authentication.ForgetPasswordStepOne.ViewModel.ForgetPasswordStepOneViewModel;
import com.headshot.discountforme.Authentication.ForgetPasswordStepTwo.View.ForgetPasswordStepTwo;
import com.headshot.discountforme.Model.UserModel.UserModel;
import com.headshot.discountforme.R;
import com.headshot.discountforme.Utils.ParentClass;
import com.headshot.discountforme.databinding.ActivityForgetPasswordStepOneBinding;

import spencerstudios.com.bungeelib.Bungee;

public class ForgetPasswordStepOneActivity extends ParentClass {
    ActivityForgetPasswordStepOneBinding binding;
    ForgetPasswordStepOneViewModel forgetPasswordStepOneViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetPasswordStepOneBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initUi();
        handleDirections();
    }

    private void handleDirections() {
        if (ParentClass.getLang(this).equals("ar")) {
            binding.etEmailAddress.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            binding.ivBack.setImageResource(R.mipmap.back_ar);

        } else {
            binding.etEmailAddress.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            binding.ivBack.setImageResource(R.mipmap.back);
        }
    }

    private void initUi() {
        dismiss_keyboard();
        forgetPasswordStepOneViewModel = new ViewModelProvider(this).get(ForgetPasswordStepOneViewModel.class);
        binding.tvEnter.setOnClickListener(v -> {
            boolean cancel = false;
            View focusView = null;

            if (TextUtils.isEmpty(binding.etEmailAddress.getText().toString())) {
                binding.etEmailAddress.setError(ForgetPasswordStepOneActivity.this.getString(R.string.emailAddress));
                focusView = binding.etEmailAddress;
                cancel = true;
            }


            if (cancel) {

            } else {
                login();
            }
        });
        binding.ivBack.setOnClickListener(v -> {
            finish();
            Bungee.split(ForgetPasswordStepOneActivity.this);
        });

    }

    private void login() {
        showFlipDialog();
        forgetPasswordStepOneViewModel.forgetPassword(binding.etEmailAddress.getText().toString())
                .observe(ForgetPasswordStepOneActivity.this,new Observer<UserModel>() {
                    @Override
                    public void onChanged(UserModel userModel) {
                        if (userModel != null) {
                            if (userModel.isValue()) {
                                dismissFlipDialog();
                                Log.e("happenedHere","200");
                                Intent intent = new Intent(ForgetPasswordStepOneActivity.this,ForgetPasswordStepTwo.class);
                                intent.putExtra("email",userModel.getData().getEmail());
                                startActivity(intent);
                                Bungee.split(ForgetPasswordStepOneActivity.this);

                            } else {
                                Log.e("happenedHere","wrong");
                                dismissFlipDialog();
                                makeErrorToast(ForgetPasswordStepOneActivity.this,getString(R.string.somethingWentWrong));
                            }
                        } else {
                            dismissFlipDialog();
                            Log.e("happenedHere","500");
                            errorToast(ForgetPasswordStepOneActivity.this,getString(R.string.somethingWentWrong));
                        }
                    }
                });
    }
}