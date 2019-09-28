package com.huehn.initword.service.base;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;

import com.huehn.initword.core.utils.Log.LogManager;

/**
 * T可能是个IBaseBinder
 * T也有可能是个aidl的IBinder
 * @param <T>
 */
public abstract class BaseServiceConnection<T> implements ServiceConnection, IAidlInterface<T>{

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        LogManager.d("huehn BaseService onChildServiceConnected name : " + name);
        if (service instanceof BaseBinder){
            onChildServiceConnected(name, (T) service);//强转成一个已知的binder T。就能拿到service了
        }else if (isAIDLBinder()){
            onChildServiceConnected(name, getAIDLService(service));
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        onChildServiceDisconnected(name);
    }

    public abstract void onChildServiceConnected(ComponentName name, T service);
    public abstract void onChildServiceDisconnected(ComponentName name);

    @Override
    public boolean isAIDLBinder() {
        return false;
    }

    @Override
    public T getAIDLService(IBinder iBinder) {
        return null;
    }
}
