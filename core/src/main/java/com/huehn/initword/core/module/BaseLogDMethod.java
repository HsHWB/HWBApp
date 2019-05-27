package com.huehn.initword.core.module;

import android.text.TextUtils;
import android.util.Log;

import com.huehn.initword.core.BuildConfig;
import com.huehn.initword.core.utils.Log.BaseLogImpl;

/**
 * 对于普通打印的接口
 */
public abstract class BaseLogDMethod extends BaseLogImpl {

    @Override
    public void write(int level, Class stackTraceClazz, String tag, Object object) {
        StringBuilder stringBuilder = writeLog(object);

        if (TextUtils.isEmpty(tag)){
            tag = "";
        }

        if (BuildConfig.DEBUG) {
            Log.v(tag, printTargetStack(stackTraceClazz) + stringBuilder.toString());
        }
    }
}
