package com.huehn.initword.manager;

import android.content.Context;

import com.huehn.initword.BuildConfig;
import com.huehn.initword.core.app.BaseConstant;
import com.umeng.commonsdk.UMConfigure;

public class UMManager {

    public static void init(Context context){
         /*
            注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，也需要在App代码中调用初始化接口
            （如需要使用AndroidManifest.xml中配置好的appkey和channel值，UMConfigure.init调用中appkey和channel参数请置为null）。
        */
        UMConfigure.init(context, BaseConstant.UMENG_APPKEY, "HWBAPP_CHANNEL", UMConfigure.DEVICE_TYPE_PHONE, null);
//        if (BuildConfig.DEBUG){
//            UMConfigure.setLogEnabled(true);
//        }else {
//            UMConfigure.setLogEnabled(false);
//        }
    }

}
