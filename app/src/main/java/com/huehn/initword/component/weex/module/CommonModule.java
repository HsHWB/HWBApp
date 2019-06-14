package com.huehn.initword.component.weex.module;

import android.widget.Toast;

import com.huehn.initword.core.app.App;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;

/**
 * Created by liuzhao on 2017/10/23.
 *
 * 展示自定义Module使用
 *
 */
public class CommonModule extends WXModule {

    @JSMethod(uiThread = true)
    public void toast(String message) {
        Toast.makeText(App.getApp(), "12" + message, Toast.LENGTH_SHORT).show();
    }

}
