package com.huehn.initword.core.net;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * https网络请求调用和返回
 *
 * 1、提供获取http服务的总入口
 */
public class HttpsManager {

    private static volatile HttpsManager instance;
    private Retrofit retrofit;
//    private Map<String, Retrofit> retrofitMap = new HashMap<>();

    public static HttpsManager getInstance() {
        if (instance == null){
            synchronized (HttpsManager.class){
                if (instance == null){
                    instance = new HttpsManager();
                }
            }
        }
        return instance;
    }

    public Retrofit getRetrofit(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }


//    public <T> T getService(){
//
//    }
}
