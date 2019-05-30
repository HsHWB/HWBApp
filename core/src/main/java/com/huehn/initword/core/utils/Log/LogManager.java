package com.huehn.initword.core.utils.Log;


import com.huehn.initword.core.module.ILogMethod;

import java.util.HashMap;
import java.util.Map;

public class LogManager {

    private static LogManager instance;
    private Map<Integer, ILogMethod> logMap = new HashMap<>();

    public final static int D_LOG = 1;//普通的log
    public final static int W_LOG = 2;//写文件的log

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
        registerLog(D_LOG, new LogDImpl());
        registerLog(W_LOG, new LogWImpl());
    }

    private void registerLog(int logType, ILogMethod iLogMethod){
        if (logMap != null && iLogMethod != null && !logMap.containsKey(logType)){
            logMap.put(logType, iLogMethod);
        }
    }

    public static void d(String tag, Object value) {
        if (getInstance().logMap != null && getInstance().logMap.containsKey(D_LOG)) {
            getInstance().logMap.get(D_LOG).write(0, LogManager.class, tag, value);
        }
    }

    public static void d(Object value) {
        d("LogManager", value);
    }
    public static void i(String tag, Object value) {

    }

    public static void i(Object value) {
        i("LogManager", value);
    }

    /**
     * 写入log文件到本地
     * @param fileId 文件分类（决定文件名）
     * @param value 要写入的数据
     */
    public static void w(int fileId, Object value) {
        if (getInstance().logMap != null && getInstance().logMap.containsKey(W_LOG)){
//            getInstance().logMap.get(W_LOG).write(fileId, LogManager.class, value);
        }
    }

    public static void w(Object value) {
        w(DEFAULT_EXCEPTION_LOG_FILE_LOCAL, value);
    }

    private static void doLog(int logTypeId, Object value){

    }
}
