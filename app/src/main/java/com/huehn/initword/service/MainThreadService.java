package com.huehn.initword.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.huehn.initword.core.utils.Log.LogManager;
import com.huehn.initword.core.utils.SystemUtils.AppUtils;

/**
 * 放在主进程的service
 */
public class MainThreadService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        LogManager.d("huehn MainThreadService onCreate getNowProcessName : " + AppUtils.getNowProcessName());

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogManager.d("huehn MainThreadService onStartCommand intent : " + intent + "      startId : " + startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        LogManager.d("huehn MainThreadService onDestroy");
        super.onDestroy();
    }


    @Override
    public IBinder onBind(Intent intent) {
        LogManager.d("huehn MainThreadService onBind intent : " + intent);
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogManager.d("huehn MainThreadService onUnbind intent : " + intent);
        return super.onUnbind(intent);
    }
}
