package com.headshot.discountforme;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;


import com.headshot.discountforme.Utils.ParentClass;
import com.headshot.discountforme.R;

import java.util.Locale;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

import static com.headshot.discountforme.Utils.ParentClass.getLang;


/**
 * Created by cz on 19/03/2018.
 */

public class AppController extends MultiDexApplication {
    SharedPreferences sharedPreferences_language;
    private static Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        sharedPreferences_language = getSharedPreferences("language",MODE_PRIVATE);
        initLanguage();
        if (getLang(getApplicationContext()).equals("ar")) {
            ParentClass.setDefaultLang("ar",getApplicationContext());

        } else {
            ParentClass.setDefaultLang("en",getApplicationContext());
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setLocale();
    }

    private void setLocale() {
        Locale locale = new Locale(getLang(this));
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    public static Context getContext() {
        return mContext;
    } // fun of getContext

    public static void initLanguage() {
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/ElMessiri-SemiBold.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    } // fun of initLanguage


}
