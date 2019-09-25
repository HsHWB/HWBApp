package com.huehn.initword.core.utils.SystemUtils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;

import com.huehn.initword.core.app.App;
import com.huehn.initword.core.utils.Log.LogManager;

/**
 * 进程名，包名等等
 */
public class AppUtils {

    /**
     * 判断是否当前为主进程
     * @return
     */
    public static boolean isMainProcess(){
        if (App.getApp() == null){
            return false;
        }

        String packageName = App.getApp().getPackageName();
        if (TextUtils.isEmpty(packageName)){
            return false;
        }
        return packageName.equals(getNowProcessName());
    }


    /**
     * 获取当前进程名
     * @return
     */
    public static String getNowProcessName(){
        if (App.getApp() == null){
            return "null";
        }
        int pid = Process.myPid();
        String processName = null;

        ActivityManager activityManager = (ActivityManager) App.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager == null){
            return "null";
        }
        for (ActivityManager.RunningAppProcessInfo processInfo : activityManager.getRunningAppProcesses()){
            LogManager.d("huehn process name : " + (processInfo != null ? processInfo.processName : "null"));
            if (processInfo != null && processInfo.pid == pid){
                processName = processInfo.processName;
//                break;
            }
        }
        return processName;
    }

    /**
     * 判断是否为主线程
     * @return
     */
    public static boolean isMainThread(){
        return Looper.getMainLooper() == Looper.myLooper();
    }

}
