package com.huehn.initword.core.net.download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import io.reactivex.functions.Consumer;

/**
 * 下载文件
 *
 *
 * begin  ->  读取初始url  ->  将url转变成URL类对象  ->  打开URL连接 url对象.openConnection()
 * ->  连接设置httpUrlConnection.setRequestMethod("GET")
 *
 *
 *
 *
 * @param <T>
 */
public class FileDownLoad<T> {

    //https://github.com/git-for-windows/git/releases/download/v2.22.0.windows.1/Git-2.22.0-64-bit.exe
    private String url;
    private T t;
    private Consumer<T> onSuccessListener;
    private Consumer<T> onErrorListener;

    private FileDownLoad(Builder builder){
        if (builder != null){
            this.url = builder.url;
            this.onSuccessListener = builder.onSuccessListener;
            this.onErrorListener = builder.onErrorListener;
        }
    }


    public void downLoadFile(){
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;

    }


    private HttpURLConnection getConnection(String url) throws IOException {
        URL url1 = new URL(url);
        //返回一个 URLConnection 对象，它表示到 URL 所引用的远程对象的连接。
        HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("Content-Type", "application/octet-stream");
    }





    public class Builder<T>{
        private String url;
        private Consumer<T> onSuccessListener;
        private Consumer<T> onErrorListener;

        public Builder() {
        }

        public Builder<T> setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder<T> setOnSuccessListener(Consumer<T> onSuccessListener) {
            this.onSuccessListener = onSuccessListener;
            return this;
        }

        public Builder<T> setOnErrorListener(Consumer<T> onErrorListener) {
            this.onErrorListener = onErrorListener;
            return this;
        }

        public FileDownLoad<T> build(){
            return new FileDownLoad<>(this);
        }
    }
}
