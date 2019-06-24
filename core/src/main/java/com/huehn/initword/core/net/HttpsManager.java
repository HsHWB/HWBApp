package com.huehn.initword.core.net;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.internal.tls.CertificateChainCleaner;
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
            OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder();
            X509TrustManager x509TrustManager = new RequestTrustX509Manager();
            clientBuilder.sslSocketFactory(sslSocketFactory(x509TrustManager), x509TrustManager);
            retrofit = new Retrofit.Builder()
                    .client(clientBuilder.build())
                    .baseUrl("https://www.google.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static SSLSocketFactory sslSocketFactory(X509TrustManager x509Manager) {

        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{x509Manager}, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public <T> T getService(){
//
//    }
}
