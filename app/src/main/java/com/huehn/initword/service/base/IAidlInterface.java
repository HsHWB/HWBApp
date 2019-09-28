package com.huehn.initword.service.base;

import android.os.IBinder;

public interface IAidlInterface<T> {

    boolean isAIDLBinder();
    T getAIDLService(IBinder iBinder);

}
