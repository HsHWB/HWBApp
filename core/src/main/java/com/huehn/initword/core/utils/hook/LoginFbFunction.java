package com.huehn.initword.core.utils.hook;

import com.facebook.login.LoginFbManager;
import com.facebook.login.LoginManager;
import com.huehn.initword.core.utils.Log.LogManager;

import java.lang.reflect.Field;

import io.reactivex.functions.Consumer;

public class LoginFbFunction {

    public static void hookFbLoginManager(Consumer callback){
        try {
//            Class loginManagerDelegate = Class.forName("com.facebook.login.LoginManager$ActivityStartActivityDelegate");
            LoginFbManager loginFbManager = new LoginFbManager(LoginManager.getInstance(), callback);
            Field instanceField = LoginManager.class.getDeclaredField("instance");
            instanceField.setAccessible(true);
            Object instanceObject = LoginManager.getInstance();
            instanceField.set(instanceObject, loginFbManager);
            LogManager.d("huehn fb LoginFbFunction hookFbLoginManager");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
