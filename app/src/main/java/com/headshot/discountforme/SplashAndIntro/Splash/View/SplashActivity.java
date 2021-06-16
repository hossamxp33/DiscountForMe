package com.headshot.discountforme.SplashAndIntro.Splash.View;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.headshot.discountforme.Main.Activities.Home.View.HomeActivity;
import com.headshot.discountforme.R;
import com.headshot.discountforme.SplashAndIntro.Intro.View.IntroActivity;
import com.headshot.discountforme.Utils.ParentClass;
import com.headshot.discountforme.Utils.SharedPrefManager;

import spencerstudios.com.bungeelib.Bungee;

public class SplashActivity extends ParentClass {
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        sharedPrefManager = new SharedPrefManager(this);
        go();
    }

    private void myCode() {
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.INTERNET) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CALL_PHONE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(this).setTitle(getString(R.string.note)).setMessage(getString(R.string.need_permissions))
                        .setPositiveButton(getString(R.string.ok),new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface,int i) {
                                startInstalledAppDetailsActivity(SplashActivity.this);
                            }
                        }).show();

            } else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE,Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
            return;
        }

        go();
    }

    private void go() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sharedPrefManager.isFirstTime()) {
                    Intent intent = new Intent(SplashActivity.this,IntroActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    Bungee.split(SplashActivity.this);


                } else {
//                    if (sharedPrefManager.getLoginStatus()) {
                    Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    Bungee.split(SplashActivity.this);


//                    } else {
//                        Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                        startActivity(intent);
//                        Bungee.split(SplashActivity.this);
//                    }
                }
            }
        },3000);
    }

    public static void startInstalledAppDetailsActivity(Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(i);
    }


    @Override
    protected void onResume() {
        super.onResume();
//        myCode();
        setDefaultLang(getLang(this),this);
    }
}