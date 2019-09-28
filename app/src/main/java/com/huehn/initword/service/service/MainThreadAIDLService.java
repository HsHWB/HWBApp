package com.huehn.initword.service.service;

import android.widget.Toast;

import com.huehn.initword.core.app.App;
import com.huehn.initword.service.base.BaseService;
import com.huehn.initword.service.binder.MainAIDLBinder;

public class MainThreadAIDLService extends BaseService<MainThreadAIDLService, MainAIDLBinder> {
    @Override
    public MainThreadAIDLService getService() {
        return this;
    }

    @Override
    public MainAIDLBinder getBinder() {
        return new MainAIDLBinder(this);
    }


    public void toast(){
        Toast.makeText(App.getApp(), "aidl toast", Toast.LENGTH_SHORT).show();
    }
}
