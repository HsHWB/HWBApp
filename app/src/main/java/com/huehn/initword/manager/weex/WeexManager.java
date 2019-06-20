package com.huehn.initword.manager.weex;


import android.app.Application;

import com.huehn.initword.adapter.weex.ImageAdapter;
import com.huehn.initword.module.weex.CommonModule;
import com.huehn.initword.ui.view.CircleImageView;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;

public class WeexManager {

    public static void init(Application context) {
        InitConfig config = new InitConfig.Builder()
                //图片库接口
                .setImgAdapter(new ImageAdapter())
                .build();

        setComponent();
        setModule();
        WXSDKEngine.initialize(context, config);

    }

    public static void setComponent(){

        try {
            WXSDKEngine.registerComponent("circleImageView", CircleImageView.class);
        } catch (WXException e) {
            e.printStackTrace();
        }

    }

    public static void setModule(){
        try {
            WXSDKEngine.registerModule("commonmodule", CommonModule.class);
        } catch (WXException e) {
            e.printStackTrace();
        }
    }
}
