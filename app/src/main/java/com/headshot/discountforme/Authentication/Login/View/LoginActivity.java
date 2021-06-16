package com.headshot.discountforme.Authentication.Login.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.headshot.discountforme.Authentication.ForgetPasswordStepOne.View.ForgetPasswordStepOneActivity;
import com.headshot.discountforme.Authentication.Login.ViewModel.LoginViewModel;
import com.headshot.discountforme.Authentication.Register.View.RegisterActivity;
import com.headshot.discountforme.Main.Activities.Home.View.HomeActivity;
import com.headshot.discountforme.Model.UserModel.UserModel;
import com.headshot.discountforme.R;
import com.headshot.discountforme.Utils.Constants;
import com.headshot.discountforme.Utils.ParentClass;
import com.headshot.discountforme.databinding.ActivityLoginBinding;

import spencerstudios.com.bungeelib.Bungee;

public class LoginActivity extends ParentClass {
    ActivityLoginBinding binding;
    LoginViewModel loginViewModel;
    String deviceToken = "tokenToBeChanged";
    String socialToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initUi();
        handleDirections();
    }

    private void handleDirections() {
        if (ParentClass.getLang(this).equals("ar")) {
            binding.etEmailAddress.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            binding.etPassword.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);

        } else {
            binding.etEmailAddress.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            binding.etPassword.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
        }
    }

    private void initUi() {
        dismiss_keyboard();
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        SharedPreferences prefs = getSharedPreferences(Constants.mobileToken,MODE_PRIVATE);
        if (!prefs.getString("m_token","").equals("")) {
            deviceToken = prefs.getString("m_token","");
        }
        Log.e("deviceToken",deviceToken + "GOOD");
        binding.tvEnter.setOnClickListener(v -> {
            boolean cancel = false;
            View focusView = null;

            if (TextUtils.isEmpty(binding.etEmailAddress.getText().toString())) {
                binding.etEmailAddress.setError(LoginActivity.this.getString(R.string.emailAddress));
                focusView = binding.etEmailAddress;
                cancel = true;
            }

            if (TextUtils.isEmpty(binding.etPassword.getText().toString())) {
                binding.etPassword.setError(LoginActivity.this.getString(R.string.password));
                focusView = binding.etPassword;
                cancel = true;
            }

            if (cancel) {

            } else {
                login();
            }
        });

        binding.rlSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
            Bungee.split(LoginActivity.this);
        });
        binding.tvForgetPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this,ForgetPasswordStepOneActivity.class);
            startActivity(intent);
            Bungee.split(LoginActivity.this);
        });

        binding.tvSkip.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            Bungee.split(LoginActivity.this);
        });
    }

    private void login() {
        showFlipDialog();
        loginViewModel.login(binding.etEmailAddress.getText().toString(),binding.etPassword.getText().toString(),deviceToken,socialToken)
                .observe(LoginActivity.this,new Observer<UserModel>() {
                    @Override
                    public void onChanged(UserModel userModel) {
                        if (userModel != null) {
                            if (userModel.isValue()) {
                                dismissFlipDialog();
                                Log.e("happenedHere","200");
                                sharedPrefManager.setLoginStatus(true);
                                sharedPrefManager.setUserDate(userModel.getData());
                                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(intent);
                                Bungee.split(LoginActivity.this);

                            } else {
                                Log.e("happenedHere","wrong");
                                dismissFlipDialog();
                                makeErrorToast(LoginActivity.this,userModel.getMsg());
                            }
                        } else {
                            dismissFlipDialog();
                            Log.e("happenedHere","500");
                            errorToast(LoginActivity.this,getString(R.string.somethingWentWrong));
                        }
                    }
                });
    }

}