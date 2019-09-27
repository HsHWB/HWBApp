package com.huehn.initword.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.huehn.initword.core.utils.Log.LogManager;

public abstract class BaseServiceConnection<T extends BaseBinder> implements ServiceConnection {

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        LogManager.d("huehn BaseService onChildServiceConnected name : " + name);
        if (service instanceof BaseBinder){
            onChildServiceConnected(name, (T) service);
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        onChildServiceDisconnected(name);
    }

    public abstract void onChildServiceConnected(ComponentName name, T service);
    public abstract void onChildServiceDisconnected(ComponentName name);

}
