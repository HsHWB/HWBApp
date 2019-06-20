package com.huehn.initword.core.net.download;

import android.text.TextUtils;
import android.widget.TextView;

import com.huehn.initword.core.module.IOnCallBack;
import com.huehn.initword.core.utils.Exception.AppException;
import com.huehn.initword.core.utils.File.FileResult;
import com.huehn.initword.core.utils.File.FileUtils;
import com.huehn.initword.core.utils.Log.LogManager;
import com.huehn.initword.core.utils.Rxjava.RxJavaUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import io.reactivex.functions.Consumer;

/**
 * 下载文件
 *
 *
 * begin
 * ->  读取初始url
 * ->  将url转变成URL类对象
 * ->  打开URL连接 url对象.openConnection()
 * ->  连接设置httpUrlConnection.setRequestMethod("GET"),httpUrlConnection.setUseCaches(true)
 * ->  建立HttpUrlConnection连接  httpUrlConnection.connect();
 * ->  读取网页内容 （字符串流） httpUrlConnection.getInputStream();
 * ->  end
 *
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
    private IOnCallBack onSuccessListener;
    private IOnCallBack<Throwable> onErrorListener;

    public final static String TAG = "FileDownLoad";

    private FileDownLoad(Builder builder){
        if (builder != null){
            this.url = builder.url;
            this.onSuccessListener = builder.onSuccessListener;
            this.onErrorListener = builder.onErrorListener;
        }
    }


    public void startDownLoad(final int typeId, final String fileName){
        RxJavaUtils.runOnIOThread(new Runnable() {
            @Override
            public void run() {
                BufferedInputStream bufferedInputStream = null;
                BufferedOutputStream bufferedOutputStream = null;
                HttpURLConnection httpURLConnection = null;
                try {
                    if (TextUtils.isEmpty(url)){
                        if (onErrorListener != null){
                            onErrorListener.accept(new RuntimeException("url is null"));
                        }
                        return;
                    }
                    if (TextUtils.isEmpty(fileName)){
                        if (onErrorListener != null){
                            onErrorListener.accept(new RuntimeException("fileName is null"));
                        }
                        return;
                    }

                    httpURLConnection = getConnection(typeId, url);
                    int contentLength = httpURLConnection.getContentLength();
                    LogManager.d(TAG, "" + contentLength);

                    if (contentLength <= 0){
                        if (onErrorListener != null){
                            onErrorListener.accept(new RuntimeException("file length is less than 0"));
                        }
                        httpURLConnection.disconnect();
                    }
                    InputStream inputStream = httpURLConnection.getInputStream();
                    //bufferedInputStream储存httpUrlConnection的inputStream输入流
                    bufferedInputStream = new BufferedInputStream(inputStream);
                    //bufferedOutputStream输出到文件
                    FileResult fileResult = FileUtils.createFile(fileName, true);
                    if (fileResult.getFile() == null){
                        if (onErrorListener != null){
                            onErrorListener.accept(AppException.fileCreateError());
                        }
                        return;
                    }

                    FileOutputStream fileOutputStream = new FileOutputStream(fileResult.getFile());
                    bufferedOutputStream = new BufferedOutputStream(fileOutputStream);


                    int b = 0;
                    byte[] byteArray = new byte[1024];
                    while ((b = bufferedInputStream.read(byteArray)) != -1){
                        bufferedOutputStream.write(byteArray, 0, b);
                    }
                    if (onSuccessListener != null){
                        onSuccessListener.accept(null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (onErrorListener != null){
                        onErrorListener.accept(e);
                    }
                }finally {
                    try {
                        if (bufferedInputStream != null){
                                bufferedInputStream.close();
                        }
                        if (bufferedOutputStream != null){
                            bufferedInputStream.close();
                        }
                        if (httpURLConnection != null){
                            httpURLConnection.disconnect();
                        }
                    } catch (Exception e) {
                            e.printStackTrace();
                    }
                }
            }
        });
    }


    private HttpURLConnection getConnection(int typeId, String url) throws IOException {
        String methodName = HttpConfig.HttpURLType.getName(typeId);
        if (methodName == null){
            throw AppException.httpUrlMethodNotFind();
        }
        URL url1 = new URL(url);
        //返回一个 URLConnection 对象，它表示到 URL 所引用的远程对象的连接。
        HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
        httpURLConnection.setRequestMethod(methodName);


        // 设定传送的内容类型是可序列化的java对象
        // (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
//        httpURLConnection.setRequestProperty("Content-type", "application/x-java-serialized-object");

        //设定传送的内容类型是文件
        httpURLConnection.setRequestProperty("Content-Type", "application/octet-stream");
        httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
        // 设置是否向httpUrlConnection输出，如果是post请求，参数要放在
        // http正文内，因此需要设为true, 默认情况下是false;
        if (methodName.equals(HttpConfig.HttpURLType.POST.name())) {
            httpURLConnection.setDoOutput(true);
        }
        // 设置是否从httpUrlConnection读入，默认情况下是true;
        httpURLConnection.setDoInput(true);
        // Post 请求不能使用缓存
        httpURLConnection.setUseCaches(false);
        httpURLConnection.connect();
        return httpURLConnection;
    }



    public static class Builder{
        private String url;
        private IOnCallBack onSuccessListener;
        private IOnCallBack<Throwable> onErrorListener;

        public Builder() {
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setOnSuccessListener(IOnCallBack onSuccessListener) {
            this.onSuccessListener = onSuccessListener;
            return this;
        }

        public Builder setOnErrorListener(IOnCallBack<Throwable> onErrorListener) {
            this.onErrorListener = onErrorListener;
            return this;
        }

        public FileDownLoad build(){
            return new FileDownLoad(this);
        }
    }
}
