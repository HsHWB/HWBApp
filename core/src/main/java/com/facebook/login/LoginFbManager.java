package com.facebook.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.FacebookActivity;
import com.facebook.FacebookSdk;
import com.facebook.internal.Validate;
import com.facebook.login.LoginManager;
import com.huehn.initword.core.utils.Log.LogManager;
import com.taobao.weex.devtools.common.LogUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import io.reactivex.functions.Consumer;

public class LoginFbManager extends LoginManager {


    private LoginManager originLoginManager;
    private Consumer callback;

    public LoginFbManager(LoginManager originLoginManager, Consumer callback) {
        this.originLoginManager = originLoginManager;
        this.callback = callback;
    }

    /*
     * Logs the user in with the requested permissions.
     * @param activity    The activity which is starting the login process.
     * @param permissions The requested permissions.
    public void logIn(Activity activity, Collection<String> permissions) {
        LoginClient.Request loginRequest = createLoginRequest(permissions);
        startLogin(new LoginManager.ActivityStartActivityDelegate(activity), loginRequest);
    }
     */

    /**
     * 此方法代替源码父类LoginManager的logIn方法
     * @param activity
     * @param permissions
     */
    public void logIn(Activity activity, Collection<String> permissions) {

        try {

            LoginClient.Request loginRequest = createLoginRequest(permissions);
//            //反射获取源码的logIn方法
//            Method loginMethod = originLoginManager.getClass().getMethod("logIn", Activity.class, Collection.class);
//            loginMethod.setAccessible(true);
//            loginMethod.invoke(originLoginManager, activity, permissions);
            //反射获取源码的私有方法startLogin方法
            //getDeclaredMethod可以获取到所有方法，而getMethod只能获取public
            Method startLoginMethod = originLoginManager.getClass().getDeclaredMethod("startLogin",  StartActivityDelegate.class,
                    LoginClient.Request.class);

            startLoginMethod.setAccessible(true);

            startLoginMethod.invoke(originLoginManager, new ActivityStartActivityDelegate(activity, callback), loginRequest);

            LogManager.d("huehn LoginFbManager logIn");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    public Intent getFacebookActivityIntent(LoginClient.Request request) {
//
//        LogManager.d("huehn LoginFbManager getFacebookActivityIntent");
//
//        Intent intent = new Intent();
//        intent.setClass(FacebookSdk.getApplicationContext(), FacebookMyActivity.class);
//        intent.setAction(request.getLoginBehavior().toString());
//        Bundle extras = new Bundle();
//        extras.putParcelable("request", request);
//        intent.putExtra("com.facebook.LoginFragment:Request", extras);
//        return intent;
//    }

    private static class ActivityStartActivityDelegate implements StartActivityDelegate {
        private final Activity activity;
        private Consumer callback;
        ActivityStartActivityDelegate(final Activity activity, Consumer callback) {
            Validate.notNull(activity, "activity");
            this.activity = activity;
            this.callback = callback;
        }

        @Override
        public void startActivityForResult(Intent intent, int requestCode) {
            activity.startActivityForResult(intent, requestCode);
            LogManager.d("huehn LoginFbManager startActivityForResult");
            try {
                callback.accept(new Object());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public Activity getActivityContext() {
            return activity;
        }
    }
}
