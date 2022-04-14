package com.example.lxhstudy.interfaces;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OKHttpService {
    @FormUrlEncoded
    @POST("user/login")
    Call<ResponseBody> login(@Field("username") String userName, @Field("password") String password);

    @GET("/")
    Call<ResponseBody> visit();
}
