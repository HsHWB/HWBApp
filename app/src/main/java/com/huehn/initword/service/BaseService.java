package com.huehn.initword.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.huehn.initword.core.utils.Log.LogManager;
import com.huehn.initword.core.utils.SystemUtils.AppUtils;

public abstract class BaseService<T extends BaseService, R extends BaseBinder> extends Service {

    private T service;
    private R binder;
    @Override
    public IBinder onBind(Intent intent) {
        binder = getBinder();
        LogManager.d("huehn BaseService onBind");
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogManager.d("huehn BaseService onCreate getNowProcessName : " + AppUtils.getNowProcessName());

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogManager.d("huehn BaseService onStartCommand intent : " + intent + "      startId : " + startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        LogManager.d("huehn BaseService onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogManager.d("huehn BaseService onUnbind intent : " + intent);
        return super.onUnbind(intent);
    }

    public abstract T getService();
    public abstract R getBinder();
}
