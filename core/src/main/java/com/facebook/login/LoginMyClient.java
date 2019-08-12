package com.facebook.login;

import android.os.Parcel;
import android.support.v4.app.Fragment;

import com.facebook.appevents.AppEventsConstants;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LoginMyClient extends LoginClient {

    private LoginClient originLoginClient;

    public LoginMyClient(Fragment fragment, LoginClient originLoginClient) {
        super(fragment);
        this.originLoginClient = originLoginClient;
    }

    public LoginMyClient(Parcel source) {
        super(source);
    }

    boolean tryCurrentHandler() {
        LoginMethodHandler handler = getCurrentHandler();
        if (handler.needsInternetPermission() && !checkInternetPermission()) {
            addLoggingExtra(
                    LoginLogger.EVENT_EXTRAS_MISSING_INTERNET_PERMISSION,
                    AppEventsConstants.EVENT_PARAM_VALUE_YES,
                    false
            );
            return false;
        }

        boolean tried = handler.tryAuthorize(pendingRequest);
        LoginLogger loginLogger = getMyLogger();
        if (loginLogger == null){
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
            addLoggingExtra(
                    LoginLogger.EVENT_EXTRAS_NOT_TRIED,
                    handler.getNameForLogging(),
                    true
            );
        }

        return tried;
    }

    private LoginLogger getMyLogger(){
        try {
            Method method = LoginClient.class.getDeclaredMethod("getLogger");
            LoginLogger loginLogger = (LoginLogger) method.invoke(originLoginClient);
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


    private void addMyLoggingExtra(){

    }
}
