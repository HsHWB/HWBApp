package com.huehn.initword.manager.design;

import android.content.Context;

import com.huehn.initword.module.weex.TestInnerClass;

public class SingleInstanceManager {

    private static SingleInstanceManager instance;
    private Context context;
    private TestInnerClass.InnerClass innerClass;

    public static SingleInstanceManager getInstance(){
        if (instance == null){
            synchronized (SingleInstanceManager.class){
                if (instance == null){
                    instance = new SingleInstanceManager();
                }
            }
        }
        return instance;
    }

    public static SingleInstanceManager getInstance(Context context){
        if (instance == null){
            synchronized (SingleInstanceManager.class){
                if (instance == null){
                    instance = new SingleInstanceManager(context);
                }
            }
        }
        return instance;
    }

    private SingleInstanceManager() {

    }
    private SingleInstanceManager(Context context) {
        this.context = context;
    }

    public TestInnerClass.InnerClass getInnerClass() {
        return innerClass;
    }

    public void setInnerClass(TestInnerClass.InnerClass innerClass) {
        this.innerClass = innerClass;
    }

    public Context getContext(){
        return this.context;
    }
}
