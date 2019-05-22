package com.huehn.initword.core.utils.Log;

import com.huehn.initword.core.module.ILog;


public class LogManager implements ILog {

    private static LogManager instance;
    private LogDImpl logD;
    private LogWImpl logW;

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
        logD = new LogDImpl();
    }

    @Override
    public void d(String tag, Object value) {
        logD.write(0, LogManager.class, tag, value);
    }

    @Override
    public void i(String tag, Object value) {

    }

    @Override
    public void w(String tag, String subPath, Object value) {
        logW.writeLog()
    }
}
