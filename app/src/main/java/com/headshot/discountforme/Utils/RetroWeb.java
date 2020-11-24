package com.headshot.discountforme.Utils;







import com.headshot.discountforme.AppController;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroWeb {
    static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build();
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {

/*
                    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);*/

            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(HeaderInterceptor()).readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();


            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;

    }

    private static Interceptor HeaderInterceptor() {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Request request = chain.request();

                request = request.newBuilder()
                        .addHeader("Accept-Language", ParentClass.getLang(AppController.getContext()))
                        .build();


                okhttp3.Response response = chain.proceed(request);
                return response;
            }
        };
    }
}
