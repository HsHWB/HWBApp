package com.huehn.initword.service.service;

import android.widget.ImageView;
import android.widget.Toast;

import com.huehn.initword.core.module.IOnCallBack;
import com.huehn.initword.core.net.download.FileDownLoad;
import com.huehn.initword.core.net.download.HttpConfig;
import com.huehn.initword.core.utils.Log.LogManager;
import com.huehn.initword.service.base.BaseService;
import com.huehn.initword.service.binder.MainThreadServiceBinder;

/**
 * 放在主进程的service
 */
public class MainThreadService extends BaseService<MainThreadService, MainThreadServiceBinder> {


    @Override
    public MainThreadService getService() {
        return this;
    }

    @Override
    public MainThreadServiceBinder getBinder() {
        return new MainThreadServiceBinder(this);
    }


    public void download(){
        FileDownLoad<ImageView> fileDownLoad = new FileDownLoad.Builder()
                .setUrl("https://dl.google.com/dl/android/studio/install/3.5.0.21/android-studio-ide-191.5791312-windows.exe")
                .setOnSuccessListener(new IOnCallBack() {
                    @Override
                    public void accept(Object o)  {
                        LogManager.d("huehn downFile success");
                    }
                })
                .setOnErrorListener(new IOnCallBack<Throwable>() {
                    @Override
                    public void accept(Throwable throwable)  {
                        throwable.printStackTrace();
                    }
                })
                .build();
        fileDownLoad.startDownLoad(HttpConfig.HttpURLType.GET.getId(), "android-studio.exe");
    }

    public void toast(){
        Toast.makeText(getApplicationContext(), "toast", Toast.LENGTH_SHORT).show();
    }
}
