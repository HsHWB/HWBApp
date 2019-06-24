package com.huehn.initword.core.module.Log;

import android.text.TextUtils;
import android.util.Log;

import com.huehn.initword.core.BuildConfig;
import com.huehn.initword.core.utils.Log.BaseLogImpl;

import java.util.List;
import java.util.Map;

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

    @Override
    protected StringBuilder writeLog(Object object) {
        StringBuilder stringBuilder = null;

        stringBuilder = new StringBuilder();

        if (object == null){
            return stringBuilder.append("null");
        }

        if (object instanceof String){
            String content = (String) object;
            stringBuilder.append(content);
        }else if (object instanceof List){
            listToString((List) object, stringBuilder);
        }else if (object instanceof Map){
            mapToString((Map) object, stringBuilder);
        }else if (object instanceof String[]){
            stringBuilder = stringArrayToString((String[]) object);
        }
        else {
            stringBuilder = objectToString(object);
        }

        return stringBuilder;
    }

}
