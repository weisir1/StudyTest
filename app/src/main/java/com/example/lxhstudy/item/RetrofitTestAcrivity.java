package com.example.lxhstudy.item;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.lxhstudy.R;
import com.example.lxhstudy.interfaces.OKHttpService;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitTestAcrivity extends AppCompatActivity {

    private List<Cookie> cookie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_test_acrivity);
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        return null;
                    }
                })
                .cache(new Cache(new File(getExternalCacheDir(),"ohcache"), 10*1024 * 1024))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        return chain.proceed(chain.request());
                    }
                })
                .build();
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("username", "19834431149");
//            jsonObject.put("password", "123123");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        Request request = new Request.Builder()
                .cacheControl(CacheControl.FORCE_NETWORK)
                .url("https://wanandroid.com/wxarticle/list/408/1/json").build();//原始请求

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.i("WeiSir", "onFailure: " + e.getLocalizedMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                Log.i("WeiSir", "onResponse: " + response.code());
                Log.i("WeiSir", "onResponse: " + response.body().string());
            }
        });

    /*    Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.baidu.com")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

        OKHttpService httpService = retrofit.create(OKHttpService.class);
        httpService.login("19834431149", "123123").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("WeiSir", "陈宫: " + Thread.currentThread());
                }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("WeiSir", "失败: " + Thread.currentThread());

            }
        });*/

    }
}