package com.headshot.discountforme.Main.Activities.DealDetails.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.headshot.discountforme.R;
import com.headshot.discountforme.Utils.ParentClass;
import com.headshot.discountforme.databinding.ActivityDealDetailsBinding;

public class DealDetailsActivity extends ParentClass {
    ActivityDealDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDealDetailsBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        initUi();
        initEventDriven();
        handleDirection();

    }

    private void initUi() {

    }

    private void initEventDriven() {

    }

    private void handleDirection() {

    }
}