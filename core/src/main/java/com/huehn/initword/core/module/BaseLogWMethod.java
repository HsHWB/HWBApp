package com.huehn.initword.core.module;

import com.huehn.initword.core.utils.Log.BaseLogImpl;

/**
 * 对于要写文件的话，则要多开辟一个接口，这个接口存在是为了ILogMethod不显得臃肿。
 * 如果文件的write接口也卸载ILogMethod的话，那么普通的log打印也要继承这个写文件的接口。
 */
public abstract class BaseLogWMethod extends BaseLogImpl {
//    StringBuilder writeLogToFile(int fileId, Object object);


    @Override
    public void write(int level, Class stackTraceClazz, String tag, Object object) {
        super.write(level, stackTraceClazz, tag, object);
    }
}
