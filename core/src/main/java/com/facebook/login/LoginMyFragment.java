package com.facebook.login;

import android.os.Bundle;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class LoginMyFragment extends LoginFragment{

    private LoginClient originLoginClient;
    private LoginMyClient loginMyClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LoginClient loginClient = getOriginLoginClient();
        if (loginClient != null){
            loginMyClient = new LoginMyClient(this, loginClient);
        }else {
            loginMyClient = (LoginMyClient) new LoginClient(this);

        }
        loginMyClient.setOnCompletedListener(new LoginClient.OnCompletedListener() {
            @Override
            public void onCompleted(LoginClient.Result outcome) {
                onLoginClientCompleted(outcome);
            }
        });

        updateLoginClient();

    }

    /**
     * 把自己的loginClient代替原来的loginClient
     */
    private void updateLoginClient(){
        if (loginMyClient != null){
            try {
                Field loginClientField = LoginFragment.class.getDeclaredField("loginClient");
                loginClientField.setAccessible(true);
                originLoginClient = (LoginClient) loginClientField.get(this);
                loginClientField.set(originLoginClient, loginMyClient);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private LoginClient getOriginLoginClient(){
        try {
            Field loginClientField = LoginFragment.class.getDeclaredField("loginClient");
            loginClientField.setAccessible(true);
            originLoginClient = (LoginClient) loginClientField.get(this);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return originLoginClient;
    }

    private void onLoginClientCompleted(LoginClient.Result outcome){
        try {
            Method method = LoginClient.class.getDeclaredMethod("onLoginClientCompleted", LoginClient.Result.class);
            method.invoke(loginMyClient, outcome);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
