package com.huehn.initword.service.serviceConnection;

import android.content.ComponentName;

import com.huehn.initword.core.utils.Log.LogManager;
import com.huehn.initword.service.binder.MainThreadServiceBinder;
import com.huehn.initword.service.service.MainThreadService;
import com.huehn.initword.service.base.BaseServiceConnection;


public class MainServiceConnection extends BaseServiceConnection<MainThreadServiceBinder> {

    @Override
    public void onChildServiceConnected(ComponentName name, MainThreadServiceBinder service) {
        if (service != null){
            service.getService().toast();
        }
    }

    @Override
    public void onChildServiceDisconnected(ComponentName name) {
        LogManager.d("huehn BaseService onChildServiceDisconnected name : " + name);
    }

}
