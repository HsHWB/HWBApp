package com.huehn.initword.core.utils.Log;

import android.text.TextUtils;
import android.util.Log;

import com.huehn.initword.core.module.ILogMethod;

import java.util.List;
import java.util.Map;

public class LogDImpl extends LogImpl {
    @Override
    public void write(String tag, Object object) {
        StringBuilder stringBuilder = null;
        if (TextUtils.isEmpty(tag)){
            tag = "";
        }
        stringBuilder = new StringBuilder();
        if (object == null){
            Log.d(tag, "null");

        }else if (object instanceof String){
            String content = (String) object;
            stringBuilder.append(content);
        }else if (object instanceof List){
            listToString((List) object, stringBuilder);
        }else if (object instanceof Map){
        }


        Log.d(tag, stringBuilder.toString());
    }
}
