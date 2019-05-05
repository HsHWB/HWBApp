package com.huehn.initword.core.rxUtils;


import io.reactivex.Single;

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


}
