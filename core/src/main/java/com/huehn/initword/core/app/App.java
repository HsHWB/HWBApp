package com.huehn.initword.core.app;

import android.app.Application;

public class App {

    private Application application;
    private static App instance;

    public static App getInstance(){
        if (instance == null){
            synchronized (App.class){
                if (instance == null){
                    instance = new App();
                }
            }
        }
        return instance;
    }

    public void setApp(Application app){
        this.application = app;
    }

    public Application getApp() {
        return application;
    }
}
