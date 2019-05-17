package com.huehn.initword;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.huehn.initword.core.app.App;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        App.getInstance().setApp(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
