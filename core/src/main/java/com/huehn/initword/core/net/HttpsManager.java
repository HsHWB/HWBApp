package com.huehn.initword.core.net;


import com.huehn.initword.core.net.service.SecuritiesService;

import retrofit2.Retrofit;

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

    public SecuritiesService getDemo(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SecuritiesService service = retrofit.create(SecuritiesService.class);

        Call<Goods> call = service.getGood();
        call.enqueue(new Callback<Goods>() {
            @Override
            public void onResponse(Call<Goods> call, Response<Goods> response) {
                Goods goods = response.body();
                Log.d("sxl", goods != null ? goods.toString() :"null");
            }

            @Override
            public void onFailure(Call<Goods> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}
