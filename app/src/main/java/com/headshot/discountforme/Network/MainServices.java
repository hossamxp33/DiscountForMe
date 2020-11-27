package com.headshot.discountforme.Network;

import com.headshot.discountforme.Model.CategoriesModel.CategoriesModel;
import com.headshot.discountforme.Model.GeneralResponse.GeneralResponse;
import com.headshot.discountforme.Model.HomeModel.HomeModel;
import com.headshot.discountforme.Model.UserModel.UserModel;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MainServices {
    @GET(MainUrl.brands)
    Observable<CategoriesModel> brands(
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

    @GET(MainUrl.home)
    Observable<HomeModel> home(
            @Header("Authorization") String Authorization,
            @Path("id") String id,
            @Query("page") int page
    );

    @GET(MainUrl.use_coupon)
    Observable<GeneralResponse> use_coupon(
            @Header("Authorization") String Authorization,
            @Path("id") String id);

    @FormUrlEncoded
    @POST(MainUrl.coupon_review)
    Observable<GeneralResponse> coupon_review(
            @Header("Authorization") String Authorization,
            @Field("coupon_id") String coupon_id,
            @Field("status") String status);
}
