package com.huehn.initword.core.utils.Log;


import android.text.TextUtils;

import com.huehn.initword.core.module.Log.ILogMethod;

import java.util.HashMap;
import java.util.Map;

public class LogManager {

    private static LogManager instance;
    private Map<Integer, ILogMethod> logMap = new HashMap<>();

    /**
     * 写文件或者只显示log的id区别
     */
    public final static int DEFAULT_LOG = 0;
    public final static int DEFAULT_EXCEPTION_LOG_FILE_LOCAL = 1;
    public final static int APP_EXCEPTION_LOG_FILE_LOCAL = 2;
    public final static int CORE_EXCEPTION_LOG_FILE_LOCAL = 3;

    public static LogManager getInstance() {

        if (instance == null){
            synchronized (LogManager.class){
                if (instance == null){
                    instance = new LogManager();
                }
            }
        }

        return instance;
    }

    private LogManager() {
        registerLog(DEFAULT_LOG, new LogDImpl());
        registerLog(APP_EXCEPTION_LOG_FILE_LOCAL, new LogWImpl(APP_EXCEPTION_LOG_FILE_LOCAL));
        registerLog(CORE_EXCEPTION_LOG_FILE_LOCAL, new LogWImpl(CORE_EXCEPTION_LOG_FILE_LOCAL));
    }

    private void registerLog(int logType, ILogMethod iLogMethod){
        if (logMap != null && iLogMethod != null && !logMap.containsKey(logType)){
            logMap.put(logType, iLogMethod);
        }
    }

    public static void d(int logType, String tag, Object value) {
        doLog(logType, tag, value);
    }

    public static void d(Object value){
        d(DEFAULT_LOG, value);
    }

    public static void d(String tag, Object value) {
        d(DEFAULT_LOG, TextUtils.isEmpty(tag) ? "LogManager" : tag, value);
    }

    public static void d(int logType, Object value) {
        d(logType, "LogManager", value);
    }

    public static void w(int logType, String tag, Object value) {
        doLog(logType, tag, value);
    }

    public static void w(Object value) {
        w(APP_EXCEPTION_LOG_FILE_LOCAL, value);
    }

    public static void w(String tag, Object value) {
        w(APP_EXCEPTION_LOG_FILE_LOCAL, TextUtils.isEmpty(tag) ? "LogManager" : tag, value);
    }

    public static void w(int logType, Object value) {
        w(logType, "LogManager", value);
    }

    public static void i(String tag, Object value) {

    }

    public static void i(Object value) {
        i("LogManager", value);
    }

    private static void doLog(int logTypeId, String tag, Object value){
        if (getInstance().logMap != null && getInstance().logMap.containsKey(logTypeId)) {
            getInstance().logMap.get(logTypeId).write(0, LogManager.class, tag, value);
        }
    }
}
