package com.huehn.initword.manager;

import android.app.Application;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;

public class WeexManager {

    public static void init(Application context){
        InitConfig config = new InitConfig.Builder()
                //图片库接口
                .setImgAdapter(new FrescoImageAdapter())
                //网络库接口
                .setHttpAdapter(new InterceptWXHttpAdapter())
                .build();
        WXSDKEngine.initialize(context, config);
    }

}
