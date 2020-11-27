package com.headshot.discountforme.Network;

import com.headshot.discountforme.Model.CategoriesModel.CategoriesModel;
import com.headshot.discountforme.Model.UserModel.UserModel;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MainServices {
    @GET(MainUrl.categories)
    Observable<CategoriesModel> categories(
    );

    @FormUrlEncoded
    @POST(MainUrl.register)
    Observable<UserModel> register(
            @Field("name") String name,
            @Field("email") String email,
            @Field("device_token") String device_token,
            @Field("device_type") String device_type,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation,
            @Field("social_token") String social_token
    );

    @FormUrlEncoded
    @POST(MainUrl.login)
    Observable<UserModel> login(
            @Field("email") String email,
            @Field("password") String password,
            @Field("device_token") String device_token,
            @Field("device_type") String device_type,
            @Field("social_token") String social_token
    );

}
