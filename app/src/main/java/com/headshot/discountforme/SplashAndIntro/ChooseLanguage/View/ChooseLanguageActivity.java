package com.headshot.discountforme.SplashAndIntro.ChooseLanguage.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.headshot.discountforme.Main.Activities.Home.View.HomeActivity;
import com.headshot.discountforme.R;
import com.headshot.discountforme.Utils.ParentClass;
import com.headshot.discountforme.Utils.SharedPrefManager;
import com.headshot.discountforme.databinding.ActivityChooseLanguageBinding;

import spencerstudios.com.bungeelib.Bungee;

public class ChooseLanguageActivity extends ParentClass {
    ActivityChooseLanguageBinding binding;
    String type = "arabic";
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseLanguageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initUi();
    }

    private void initUi() {
        sharedPrefManager = new SharedPrefManager(ChooseLanguageActivity.this);
        binding.rlArabic.setOnClickListener(v -> {
            type = "arabic";
            binding.rlArabic.setBackgroundResource(R.drawable.drawable_button);
            binding.tvArabic.setTextColor(Color.parseColor("#f01313"));
            binding.rlEnglish.setBackgroundResource(R.drawable.drawable_button_with_red_stroke);
            binding.tvEnglish.setTextColor(Color.parseColor("#000000"));
        });
        binding.rlEnglish.setOnClickListener(v -> {
            type = "english";
            binding.rlEnglish.setBackgroundResource(R.drawable.drawable_button);
            binding.tvEnglish.setTextColor(Color.parseColor("#f01313"));
            binding.rlArabic.setBackgroundResource(R.drawable.drawable_button_with_red_stroke);
            binding.tvArabic.setTextColor(Color.parseColor("#000000"));
        });

        binding.tvEnter.setOnClickListener(v -> {
            if (type.equals("arabic")) {
                ParentClass.storeLang("ar",ChooseLanguageActivity.this);
                Intent intent = new Intent(ChooseLanguageActivity.this,HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            } else {
                ParentClass.storeLang("en",ChooseLanguageActivity.this);
                Intent intent = new Intent(ChooseLanguageActivity.this,HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
            sharedPrefManager.setFirstTime(false);
            Bungee.split(ChooseLanguageActivity.this);
        });
    }
}