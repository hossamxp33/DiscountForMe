package com.headshot.discountforme.Authentication.ForgetPasswordStepTwo.View;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.headshot.discountforme.Authentication.ForgetPasswordStepThree.View.ForgetPasswordStepThreeActivity;
import com.headshot.discountforme.Authentication.ForgetPasswordStepTwo.ViewModel.ForgetPasswordStepTwoViewModel;
import com.headshot.discountforme.Model.UserModel.UserModel;
import com.headshot.discountforme.R;
import com.headshot.discountforme.Utils.ParentClass;
import com.headshot.discountforme.databinding.ActivityForgetPasswordStepTwoBinding;

import spencerstudios.com.bungeelib.Bungee;

public class ForgetPasswordStepTwo extends ParentClass {
    ActivityForgetPasswordStepTwoBinding binding;
    ForgetPasswordStepTwoViewModel forgetPasswordStepTwoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetPasswordStepTwoBinding.inflate(getLayoutInflater());
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
        forgetPasswordStepTwoViewModel = new ViewModelProvider(this).get(ForgetPasswordStepTwoViewModel.class);
        binding.tvEnter.setOnClickListener(v -> {
            boolean cancel = false;
            View focusView = null;

            if (TextUtils.isEmpty(binding.etEmailAddress.getText().toString())) {
                binding.etEmailAddress.setError(ForgetPasswordStepTwo.this.getString(R.string.verificationCode));
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
            Bungee.split(ForgetPasswordStepTwo.this);
        });

    }

    private void login() {
        showFlipDialog();
        forgetPasswordStepTwoViewModel.forgetPassword(getIntent().getStringExtra("email"),binding.etEmailAddress.getText().toString())
                .observe(ForgetPasswordStepTwo.this,new Observer<UserModel>() {
                    @Override
                    public void onChanged(UserModel userModel) {
                        if (userModel != null) {
                            if (userModel.isValue()) {
                                dismissFlipDialog();
                                Log.e("happenedHere","200");
                                Intent intent = new Intent(ForgetPasswordStepTwo.this,ForgetPasswordStepThreeActivity.class);
                                intent.putExtra("email",userModel.getData().getEmail());
                                startActivity(intent);
                                Bungee.split(ForgetPasswordStepTwo.this);

                            } else {
                                Log.e("happenedHere","wrong");
                                dismissFlipDialog();
                                makeErrorToast(ForgetPasswordStepTwo.this,getString(R.string.somethingWentWrong));
                            }
                        } else {
                            dismissFlipDialog();
                            Log.e("happenedHere","500");
                            errorToast(ForgetPasswordStepTwo.this,getString(R.string.somethingWentWrong));
                        }
                    }
                });
    }
}