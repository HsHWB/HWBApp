package com.huehn.initword.service.binder;

import android.os.RemoteException;
import android.widget.Toast;

import com.huehn.initword.core.app.App;
import com.huehn.initword.core.utils.Log.LogManager;
import com.huehn.initword.core.utils.Rxjava.RxJavaUtils;
import com.huehn.initword.core.utils.SystemUtils.AppUtils;
import com.huehn.initword.service.aidl.IRemoteBinder;
import com.huehn.initword.service.base.BaseService;
import com.huehn.initword.service.base.IBaseBinder;
import com.huehn.initword.service.service.MainThreadAIDLService;

public class MainAIDLBinder extends IRemoteBinder.Stub implements IBaseBinder<MainThreadAIDLService>{

    private MainThreadAIDLService mainThreadAIDLService;

    public MainAIDLBinder(MainThreadAIDLService mainThreadAIDLService) {
        this.mainThreadAIDLService = mainThreadAIDLService;
    }

    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }

    @Override
    public void toast() throws RemoteException {

        LogManager.d("huehn MainAIDLBinder isMainThread : " + AppUtils.isMainProcess());
        RxJavaUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                mainThreadAIDLService.toast();
            }
        });
    }

    @Override
    public MainThreadAIDLService getService() {
        return mainThreadAIDLService;
    }
}
