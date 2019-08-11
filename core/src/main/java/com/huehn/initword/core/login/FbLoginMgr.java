package com.huehn.initword.core.login;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.internal.ImageRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.huehn.initword.core.app.App;
import com.huehn.initword.core.utils.Log.LogManager;
import com.huehn.initword.core.utils.hook.HookManager;
import com.huehn.initword.core.utils.hook.LoginFbFunction;

import org.json.JSONObject;

import java.util.Arrays;

import io.reactivex.functions.Consumer;


public class FbLoginMgr{
    private CallbackManager mCallbackManager;
    private LoginManager mFacebookLoginManager;


    public void doInit() {
        FacebookSdk.sdkInitialize(App.getApp());
        mCallbackManager = CallbackManager.Factory.create();
        mFacebookLoginManager = LoginManager.getInstance();
        LoginFbFunction.hookFbLoginManager(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                LogManager.d("huehn LoginFbFunction hookFbLoginManager callback");
                HookManager.hookActivity("FacebookActivity");
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mCallbackManager != null) {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    //通过Token id 来获取用户名
    private void fetchFaceBookUserName(final AccessToken accessToken) {
        GraphRequest userNamerequest = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            if (response.getError() != null) {
//                                doError(new RuntimeException(response.getError().getErrorMessage()));
                            } else if (response.getConnection().getResponseCode() == 200) {
                                String name = object.getString("name");
//                                Uri profileUri = ImageRequest.getProfilePictureUri(result.id, 200, 200);
//                                result.avatorUrl = profileUri.toString();
//                                result.username = name;
//                                doSuccess(result);
                            }
                        } catch (Exception e) {
//                            doError(e);
                        }
                    }
                });
        userNamerequest.executeAsync();
    }

    public void doLogin(Activity activity1) {
        try {
            Activity activity = activity1;
            if (activity != null) {

                LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile"));
                mFacebookLoginManager.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    //登入成功
                    @Override
                    public void onSuccess(com.facebook.login.LoginResult loginResult) {
//                        ThirdLoginResult result = new ThirdLoginResult();
//                        if(loginResult != null) {
//                            AccessToken accessToken = loginResult.getAccessToken();
//                            if(accessToken != null) {
//                                String id = accessToken.getUserId();
//                                result.id = id;
//                                result.token = accessToken.getToken();
//                                fetchFaceBookUserName(accessToken, result);
//                                LogUtils.d("huehn third facebook userId : " + id);
//                            }
//                        }
                    }

                    //登入取消
                    @Override
                    public void onCancel() {
//                        doError(new RuntimeException("canceled!"));
                    }

                    //登入失敗
                    @Override
                    public void onError(FacebookException exception) {
//                        doError(exception);
                    }
                });
            }
        }catch (Exception e){
            //有可能有MissingWebViewPackageException这种异常
            e.printStackTrace();
        }
    }

    public void logout() {
        try {
            if (mFacebookLoginManager != null) {
                mFacebookLoginManager.logOut();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}