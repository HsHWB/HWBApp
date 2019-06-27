package com.huehn.initword.service;

import com.huehn.initword.core.utils.Log.LogManager;

public class DoSomethingModule implements IDoSomething{
    @Override
    public String doSomething(String arg) {
        LogManager.d(arg);
        return arg;
    }
}
