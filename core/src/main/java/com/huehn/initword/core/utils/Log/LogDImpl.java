package com.huehn.initword.core.utils.Log;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.huehn.initword.core.BuildConfig;

import java.util.List;
import java.util.Map;

public class LogDImpl extends LogImpl {

    public LogDImpl() {

    }

    @Override
    public void write(int level, String stackTrace, String tag, Object object) {
        StringBuilder stringBuilder = null;
        if (TextUtils.isEmpty(tag)){
            tag = "";
        }

        stringBuilder = new StringBuilder();

        if (object == null){
            Log.d(tag, "null");
            return;
        }

        if (object instanceof String){
            String content = (String) object;
            stringBuilder.append(content);
        }else if (object instanceof List){
            listToString((List) object, stringBuilder);
        }else if (object instanceof Map){
            mapToString((Map) object, stringBuilder);
        }else {
            stringBuilder = objectToString(object);
        }


        if (BuildConfig.DEBUG) {
            Log.v(tag, stackTrace + stringBuilder.toString());
        }

    }
}
