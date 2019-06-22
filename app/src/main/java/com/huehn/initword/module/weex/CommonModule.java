package com.huehn.initword.module.weex;

import android.widget.Toast;

import com.huehn.initword.core.app.App;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;

/**
 * weex用的自定义module
 *
 */
public class CommonModule extends WXModule {

    @JSMethod(uiThread = true)
    public void toast(String message) {
        Toast.makeText(App.getApp(), "12" + message, Toast.LENGTH_SHORT).show();
    }

}
