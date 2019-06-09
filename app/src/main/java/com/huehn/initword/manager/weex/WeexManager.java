package com.huehn.initword.manager.weex;


import android.app.Application;

//import com.alibaba.android.bindingx.plugin.weex.BindingX;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.huehn.initword.manager.weex.component.WXComponentSyncTest;
import com.huehn.initword.manager.weex.component.WXMask;
import com.huehn.initword.manager.weex.component.WXParallax;
import com.huehn.initword.manager.weex.module.GeolocationModule;
import com.huehn.initword.manager.weex.module.MyModule;
import com.huehn.initword.manager.weex.module.RenderModule;
import com.huehn.initword.manager.weex.module.SyncTestModule;
import com.huehn.initword.manager.weex.module.WXEventModule;
import com.huehn.initword.manager.weex.module.WXTitleBar;
import com.huehn.initword.manager.weex.module.WXWsonTestModule;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.common.WXException;
import com.taobao.weex.performance.WXAnalyzerDataTransfer;

public class WeexManager {

    public static void init(Application context){
//        InitConfig config = new InitConfig.Builder()
//                //图片库接口
//                .setImgAdapter(new ImageAdapter())
//                //网络库接口
//                .setHttpAdapter(new InterceptWXHttpAdapter())
//                .build();
//        WXSDKEngine.initialize(context, config);

        WXBridgeManager.updateGlobalConfig("wson_on");
        WXEnvironment.setOpenDebugLog(true);
        WXEnvironment.setApkDebugable(true);
        WXSDKEngine.addCustomOptions("appName", "WXSample");
        WXSDKEngine.addCustomOptions("appGroup", "WXApp");
        WXSDKEngine.initialize(context,
                new InitConfig.Builder()
                        //.setImgAdapter(new FrescoImageAdapter())// use fresco adapter
                        .setImgAdapter(new ImageAdapter())
                        .setDrawableLoader(new PicassoBasedDrawableLoader(context))
                        .setWebSocketAdapterFactory(new DefaultWebSocketAdapterFactory())
                        .setJSExceptionAdapter(new JSExceptionAdapter())
                        .setHttpAdapter(new InterceptWXHttpAdapter())
                        .setApmGenerater(new ApmGenerator())
                        .build()
        );
        WXSDKManager.getInstance().addWXAnalyzer(new WXAnalyzerDemoListener());
        WXAnalyzerDataTransfer.isOpenPerformance = false;

        WXSDKManager.getInstance().setAccessibilityRoleAdapter(new DefaultAccessibilityRoleAdapter());

        try {
            Fresco.initialize(context);
            WXSDKEngine.registerComponent("synccomponent", WXComponentSyncTest.class);
            WXSDKEngine.registerComponent(WXParallax.PARALLAX, WXParallax.class);

            WXSDKEngine.registerModule("render", RenderModule.class);
            WXSDKEngine.registerModule("event", WXEventModule.class);
            WXSDKEngine.registerModule("syncTest", SyncTestModule.class);

            WXSDKEngine.registerComponent("mask", WXMask.class);
            WXSDKEngine.registerModule("myModule", MyModule.class);
            WXSDKEngine.registerModule("geolocation", GeolocationModule.class);

            WXSDKEngine.registerModule("titleBar", WXTitleBar.class);

            WXSDKEngine.registerModule("wsonTest", WXWsonTestModule.class);

//            BindingX.register();

            /**
             * override default image tag
             * WXSDKEngine.registerComponent("image", FrescoImageComponent.class);
             */

            //Typeface nativeFont = Typeface.createFromAsset(getAssets(), "font/native_font.ttf");
            //WXEnvironment.setGlobalFontFamily("bolezhusun", nativeFont);

        } catch (WXException e) {
            e.printStackTrace();
        }
    }


    /**
     *@param connectable debug server is connectable or not.
     *               if true, sdk will try to connect remote debug server when init WXBridge.
     *
     * @param debuggable enable remote debugger. valid only if host not to be "DEBUG_SERVER_HOST".
     *               true, you can launch a remote debugger and inspector both.
     *               false, you can  just launch a inspector.
     * @param host the debug server host, must not be "DEBUG_SERVER_HOST", a ip address or domain will be OK.
     *             for example "127.0.0.1".
     */
    private void initDebugEnvironment(boolean connectable, boolean debuggable, String host) {
        if (!"DEBUG_SERVER_HOST".equals(host)) {
            WXEnvironment.sDebugServerConnectable = connectable;
            WXEnvironment.sRemoteDebugMode = debuggable;
            WXEnvironment.sRemoteDebugProxyUrl = "ws://" + host + ":8088/debugProxy/native";
        }
    }

}
