package com.headshot.discountforme.Authentication.Register.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.headshot.discountforme.Authentication.Login.View.LoginActivity;
import com.headshot.discountforme.Authentication.Register.ViewModel.RegisterViewModel;
import com.headshot.discountforme.Main.Activities.Home.View.HomeActivity;
import com.headshot.discountforme.Model.UserModel.UserModel;
import com.headshot.discountforme.R;
import com.headshot.discountforme.Utils.Constants;
import com.headshot.discountforme.Utils.ParentClass;
import com.headshot.discountforme.databinding.ActivityRegisterBinding;

import spencerstudios.com.bungeelib.Bungee;

public class RegisterActivity extends ParentClass {
    ActivityRegisterBinding binding;
    RegisterViewModel registerViewModel;
    String deviceToken = "tokenToBeChanged";
    String socialToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initUi();
        handleDirections();
    }

    private void handleDirections() {
        if (ParentClass.getLang(this).equals("ar")) {
            binding.etEmailAddress.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            binding.etUserName.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            binding.etPassword.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);

        } else {
            binding.etEmailAddress.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            binding.etUserName.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            binding.etPassword.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
        }
    }

    private void initUi() {
        dismiss_keyboard();
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        SharedPreferences prefs = getSharedPreferences(Constants.mobileToken,MODE_PRIVATE);
        if (!prefs.getString("m_token","").equals("")) {
            deviceToken = prefs.getString("m_token","");
        }
        Log.e("deviceToken",deviceToken + "GOOD");
        binding.tvEnter.setOnClickListener(v -> {
            boolean cancel = false;
            View focusView = null;

            if (TextUtils.isEmpty(binding.etEmailAddress.getText().toString())) {
                binding.etEmailAddress.setError(RegisterActivity.this.getString(R.string.emailAddress));
                focusView = binding.etEmailAddress;
                cancel = true;
            }
            if (TextUtils.isEmpty(binding.etUserName.getText().toString())) {
                binding.etUserName.setError(RegisterActivity.this.getString(R.string.userName));
                focusView = binding.etUserName;
                cancel = true;
            }

            if (TextUtils.isEmpty(binding.etPassword.getText().toString())) {
                binding.etPassword.setError(RegisterActivity.this.getString(R.string.password));
                focusView = binding.etPassword;
                cancel = true;
            }

            if (cancel) {

            } else {
                login();
            }
        });

        binding.rlSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(intent);
            Bungee.split(RegisterActivity.this);
        });

        binding.tvSkip.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
            startActivity(intent);
            Bungee.split(RegisterActivity.this);
        });

    }

    private void login() {
        showFlipDialog();
        registerViewModel.register(binding.etUserName.getText().toString(),binding.etEmailAddress.getText().toString(),binding.etPassword.getText().toString(),
                binding.etPassword.getText().toString(),deviceToken,socialToken)
                .observe(RegisterActivity.this,new Observer<UserModel>() {
                    @Override
                    public void onChanged(UserModel userModel) {
                        if (userModel != null) {
                            if (userModel.isValue()) {
                                dismissFlipDialog();
                                Log.e("happenedHere","200");
                                sharedPrefManager.setLoginStatus(true);
                                sharedPrefManager.setUserDate(userModel.getData());
                                Intent intent = new Intent(RegisterActivity.this,HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(intent);
                                Bungee.split(RegisterActivity.this);

                            } else {
                                Log.e("happenedHere","wrong");
                                dismissFlipDialog();
                                makeErrorToast(RegisterActivity.this,userModel.getMsg());
                            }
                        } else {
                            dismissFlipDialog();
                            Log.e("happenedHere","500");
                            errorToast(RegisterActivity.this,getString(R.string.somethingWentWrong));
                        }
                    }
                });
    }

}