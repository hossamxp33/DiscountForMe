package com.headshot.discountforme.SplashAndIntro.Intro.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.headshot.discountforme.SplashAndIntro.ChooseLanguage.View.ChooseLanguageActivity;
import com.headshot.discountforme.Utils.ParentClass;
import com.headshot.discountforme.databinding.ActivityIntroBinding;

import spencerstudios.com.bungeelib.Bungee;

public class IntroActivity extends ParentClass {
    ActivityIntroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initUi();
    }

    private void initUi() {
        binding.tvEnter.setOnClickListener(v -> {
            Intent intent = new Intent(IntroActivity.this,ChooseLanguageActivity.class);
            startActivity(intent);
            Bungee.split(IntroActivity.this);
        });
    }
}