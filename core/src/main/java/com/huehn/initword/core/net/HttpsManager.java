package com.huehn.initword.core.net;


import android.util.Log;

import com.huehn.initword.core.net.response.ShangHaiPlateListResponse;
import com.huehn.initword.core.net.service.security.SecuritiesService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * https网络请求调用和返回
 *
 *1、提供获取http服务的总入口
 */
public class HttpsManager {

    private static volatile HttpsManager instance;


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

    public void getDemo(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://route.showapi.com/131-58")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SecuritiesService service = retrofit.create(SecuritiesService.class);


        Observable<ShangHaiPlateListResponse> shangHaiPlateListResponseObservable = service.getShangHaiPlateList();
        shangHaiPlateListResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ShangHaiPlateListResponse>() {
                    @Override
                    public void accept(ShangHaiPlateListResponse shangHaiPlateListResponse) throws Exception {
                        System.out.println(shangHaiPlateListResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
//        Call<Goods> call = service.getGood();
//        call.enqueue(new Callback<Goods>() {
//            @Override
//            public void onResponse(Call<Goods> call, Response<Goods> response) {
//                Goods goods = response.body();
//                Log.d("sxl", goods != null ? goods.toString() :"null");
//            }
//
//            @Override
//            public void onFailure(Call<Goods> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });

    }

//    public <T> T getService(){
//
//    }
}
