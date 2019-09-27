package com.huehn.initword.service;

import android.content.ComponentName;

import com.huehn.initword.core.utils.Log.LogManager;


public class MainServiceConnection extends BaseServiceConnection<MainThreadService.MainThreadServiceBinder> {

    @Override
    public void onChildServiceConnected(ComponentName name, MainThreadService.MainThreadServiceBinder service) {
        if (service != null){
            service.getService().toast();
        }
    }

    @Override
    public void onChildServiceDisconnected(ComponentName name) {
        LogManager.d("huehn BaseService onChildServiceDisconnected name : " + name);
    }

}
