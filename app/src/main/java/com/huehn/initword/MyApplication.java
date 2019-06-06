package com.huehn.initword;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.huehn.initword.core.app.App;
import com.huehn.initword.manager.ImagerLoaderManager;
import com.huehn.initword.manager.UMManager;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        App.getInstance().setApp(this);
        UMManager.init(this);
        ImagerLoaderManager.init(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

}
