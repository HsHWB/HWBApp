package com.huehn.initword;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.system.ErrnoException;
import android.system.StructStat;

import com.huehn.initword.core.app.App;
import com.huehn.initword.core.utils.HookUtils;
import com.huehn.initword.core.utils.PluginLoader;
import com.huehn.initword.core.utils.hook.HookManager;
import com.huehn.initword.manager.ImagerLoaderManager;
import com.huehn.initword.manager.UMManager;
import com.huehn.initword.manager.weex.WeexManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import leakcanary.LeakCanary;

import static android.system.OsConstants.S_ISDIR;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        App.getInstance().setApp(this);
        UMManager.init(this);
        ImagerLoaderManager.init(this);
        WeexManager.init(this);
//        LeakCanary.
//        PluginLoader.INSTANCE.loadPlugin("small", this);
//        HookUtils.INSTANCE.hookStartActivityIntent();
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

    private static List<File> splitPaths(String searchPath, boolean directoriesOnly) {
        List<File> result = new ArrayList<>();

        if (searchPath != null) {
            for (String path : searchPath.split(File.pathSeparator)) {

                result.add(new File(path));
            }
        }

        return result;
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
