package com.huehn.initword.service.serviceConnection;

import android.content.ComponentName;
import android.os.IBinder;
import android.os.RemoteException;

import com.huehn.initword.service.aidl.IRemoteBinder;
import com.huehn.initword.service.base.BaseServiceConnection;
import com.huehn.initword.service.base.IBaseBinder;
import com.huehn.initword.service.binder.MainAIDLBinder;

import java.nio.file.Path;

public class MainAIDLServiceConnection extends BaseServiceConnection<IRemoteBinder> {


    @Override
    public void onChildServiceConnected(ComponentName name, IRemoteBinder service) {
        try {
            service.toast();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onChildServiceDisconnected(ComponentName name) {

    }

    @Override
    public boolean isAIDLBinder() {
        return true;
    }

    @Override
    public IRemoteBinder getAIDLService(IBinder iBinder) {
        return IRemoteBinder.Stub.asInterface(iBinder);
    }
}
