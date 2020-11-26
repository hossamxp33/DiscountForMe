package com.headshot.discountforme.Network;

import com.headshot.discountforme.Model.CategoriesModel.CategoriesModel;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MainServices {
    @GET(MainUrl.categories)
    Observable<CategoriesModel> categories(
    );

}
