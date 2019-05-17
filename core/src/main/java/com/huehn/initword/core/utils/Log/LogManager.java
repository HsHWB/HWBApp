package com.huehn.initword.core.utils.Log;

import com.huehn.initword.core.module.ILog;


public class LogManager implements ILog {

    private static LogManager instance;
    private LogDImpl logD;

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
        StackTraceElement targetStackTraceElement = logD.getTargetStackTraceElement(LogManager.class);
        logD.write(0, "(" + targetStackTraceElement.getFileName() + ":"
                + targetStackTraceElement.getLineNumber() + ") ", tag, value);
    }

    @Override
    public void i(String tag, Object value) {

    }
}
