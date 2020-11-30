package com.headshot.discountforme.Network;

import com.headshot.discountforme.Model.CategoriesModel.CategoriesModel;
import com.headshot.discountforme.Model.FavouritesModel.FavouritesModel;
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

    @GET(MainUrl.categories)
    Observable<CategoriesModel> categories(
    );

    @GET(MainUrl.favourites)
    Observable<FavouritesModel> favourites(
            @Header("Authorization") String Authorization,
            @Query("page") int page

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

    @FormUrlEncoded
    @POST(MainUrl.filter)
    Observable<HomeModel> filter(
            @Header("Authorization") String Authorization,
            @Field("categories") String categories,
            @Field("order_by") String order_by,
            @Query("page") int page
    );

    @FormUrlEncoded
    @POST(MainUrl.search)
    Observable<HomeModel> search(
            @Header("Authorization") String Authorization,
            @Field("search") String search,
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
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST(MainUrl.forget_password)
    Observable<UserModel> forgetPassword(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST(MainUrl.check_forget_code)
    Observable<UserModel> checkCode(
            @Field("email") String email,
            @Field("code") String code
    );

    @FormUrlEncoded
    @POST(MainUrl.change_password)
    Observable<UserModel> changePassword(
            @Field("email") String email,
            @Field("password") String password,
            @Field("device_token") String device_token,
            @Field("device_type") String device_type
    );

    @FormUrlEncoded
    @POST(MainUrl.update_profile)
    Observable<UserModel> update_profile(
            @Header("Authorization") String Authorization,
            @Field("name") String name,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST(MainUrl.suggest_coupon)
    Observable<GeneralResponse> suggestCoupon(
            @Field("full_name") String full_name,
            @Field("address") String address,
            @Field("email") String email,
            @Field("whatsapp") String whatsapp,
            @Field("coupon_code") String coupon_code,
            @Field("brand") String brand
    );

    @FormUrlEncoded
    @POST(MainUrl.send_help)
    Observable<GeneralResponse> sendHelp(
            @Field("brand") String brand,
            @Field("email") String email,
            @Field("address") String address,
            @Field("message") String message,
            @Field("coupon") String coupon
    );

    @FormUrlEncoded
    @POST(MainUrl.logout)
    Observable<GeneralResponse> logout(
            @Header("Authorization") String Authorization,
            @Field("device_token") String device_token
    );

    @FormUrlEncoded
    @POST(MainUrl.favourite_coupon)
    Observable<GeneralResponse> favourite_coupon(
            @Header("Authorization") String Authorization,
            @Field("coupon_id") String coupon_id
    );
}
