package com.huehn.initword;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import com.huehn.initword.core.app.App;
import com.huehn.initword.core.utils.hook.HookManager;
import com.huehn.initword.manager.ImagerLoaderManager;
import com.huehn.initword.manager.UMManager;
import com.huehn.initword.manager.weex.WeexManager;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        App.getInstance().setApp(this);
        UMManager.init(this);
        ImagerLoaderManager.init(this);
        WeexManager.init(this);
        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });


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
