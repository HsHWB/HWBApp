package com.facebook.login;

import android.os.Parcel;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsConstants;
import com.huehn.initword.core.utils.Log.LogManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class LoginMyClient extends LoginClient {

    private LoginClient originLoginClient;

    public LoginMyClient(Fragment fragment, LoginClient originLoginClient) {
        super(fragment);
        this.originLoginClient = originLoginClient;
    }

    public LoginMyClient(Parcel source) {
        super(source);
    }


    void startOrContinueAuth(Request request) {
        LogManager.d("huehn fb LoginMyClient startOrContinueAuth in");
        if (!getInProgress()) {
            authorize(request);
        }
    }

    void authorize(Request request) {
        if (request == null) {
            return;
        }

        if (pendingRequest != null) {
            throw new FacebookException("Attempted to authorize while a request is pending.");
        }
        LogManager.d("huehn fb LoginMyClient authorize in");
        if (AccessToken.isCurrentAccessTokenActive() && !checkInternetPermission()) {
            // We're going to need INTERNET permission later and don't have it, so fail early.
            return;
        }
        pendingRequest = request;
        handlersToTry = getHandlersToTry(request);
        tryNextHandler();
    }

    protected LoginMethodHandler [] getHandlersToTry(Request request) {
        ArrayList<LoginMethodHandler> handlers = new ArrayList<LoginMethodHandler>();

        final LoginBehavior behavior = request.getLoginBehavior();

        if (behavior.allowsGetTokenAuth()) {
            handlers.add(new GetTokenLoginMethodHandler(this));
        }

        if (behavior.allowsKatanaAuth()) {
            handlers.add(new KatanaProxyLoginMethodHandler(this));
        }

        if (behavior.allowsFacebookLiteAuth()) {
            handlers.add(new FacebookLiteLoginMethodHandler(this));
        }

        if (behavior.allowsCustomTabAuth()) {
            handlers.add(new CustomTabLoginMethodHandler(this));
        }

        //替换这个webview的handler
        if (behavior.allowsWebViewAuth()) {
            handlers.add(new WebViewLoginMyMethodHandler(this));
        }

        if (behavior.allowsDeviceAuth()) {
            handlers.add(new DeviceAuthMethodHandler(this));
        }

        LoginMethodHandler [] result = new LoginMethodHandler[handlers.size()];
        handlers.toArray(result);
        return result;
    }

    boolean tryCurrentHandler() {
        LogManager.d("huehn fb LoginMyClient tryCurrentHandler in");
        LoginMethodHandler handler = getCurrentHandler();
        LogManager.d("huehn fb LoginMyClient tryCurrentHandler handler : " + handler);

//        if (handler instanceof WebViewLoginMethodHandler){
//            getWebDialogToHook((WebViewLoginMethodHandler) handler);
//        }

        if (handler.needsInternetPermission() && !checkInternetPermission()) {
            addMyLoggingExtra(
                    LoginLogger.EVENT_EXTRAS_MISSING_INTERNET_PERMISSION,
                    AppEventsConstants.EVENT_PARAM_VALUE_YES,
                    false
            );
            return false;
        }

        boolean tried = handler.tryAuthorize(pendingRequest);
        LoginLogger loginLogger = getMyLogger();
        if (loginLogger == null){
            LogManager.d("huehn fb LoginMyClient tryCurrentHandler loginLogger null");
            return tried;
        }
        if (tried) {
            getMyLogger().logAuthorizationMethodStart(pendingRequest.getAuthId(),
                    handler.getNameForLogging());
        } else {
            // We didn't try it, so we don't get any other completion
            // notification -- log that we skipped it.
            getMyLogger().logAuthorizationMethodNotTried(pendingRequest.getAuthId(),
                    handler.getNameForLogging());
            addMyLoggingExtra(
                    LoginLogger.EVENT_EXTRAS_NOT_TRIED,
                    handler.getNameForLogging(),
                    true
            );
        }

        LogManager.d("huehn fb LoginMyClient tryCurrentHandler tried : " + tried);
        return tried;
    }

    /**
     * 拿到webDialog并且反射之后替换成自己的webDialog
     */
    private void getWebDialogToHook(WebViewLoginMethodHandler webViewLoginMethodHandler){
        LogManager.d("huehn fb LoginMyClient getWebDialogToHook in");



    }

    private LoginLogger getMyLogger(){
        try {
            Method method = LoginClient.class.getDeclaredMethod("getLogger");
            method.setAccessible(true);
            LoginLogger loginLogger = (LoginLogger) method.invoke(this);
            LogManager.d("huehn fb LoginMyClient getMyLogger : " + loginLogger);
            return loginLogger;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void addMyLoggingExtra(String key, String value, boolean accumulate){
        try {
            Method addMyLoginMethod = LoginClient.class.getDeclaredMethod("addLoggingExtra", String.class, String.class, Boolean.class);

            addMyLoginMethod.invoke(originLoginClient, key, value, accumulate);
            LogManager.d("huehn fb LoginMyClient addMyLoggingExtra");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
