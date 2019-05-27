package com.huehn.initword.core.utils.Log;

import android.text.TextUtils;
import android.util.Log;

import com.huehn.initword.core.BuildConfig;
import com.huehn.initword.core.module.BaseLogDMethod;

import java.util.List;
import java.util.Map;

public class LogDImpl extends BaseLogDMethod {

    public LogDImpl() {

    }

    @Override
    public StringBuilder writeLog(Object object) {
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
        }else {
            stringBuilder = objectToString(object);
        }

        return stringBuilder;


    }
}
