package com.huehn.initword.core.utils.Log;

import com.huehn.initword.core.module.ILog;

public class LogManager implements ILog {


    private LogManager instance;

    public LogManager getInstance() {

        if (instance == null){
            synchronized (LogManager.this){
                if (instance == null){
                    instance = new LogManager();
                }
            }
        }

        return instance;
    }

    @Override
    public void d(String tag, Object value) {

    }

    @Override
    public void i(String tag, Object value) {

    }
}
