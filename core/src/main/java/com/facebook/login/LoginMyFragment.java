package com.facebook.login;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huehn.initword.core.utils.Log.LogManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class LoginMyFragment extends Fragment {
    static final String RESULT_KEY = "com.facebook.LoginFragment:Result";
    static final String REQUEST_KEY = "com.facebook.LoginFragment:Request";
    static final String EXTRA_REQUEST = "request";

    private static final String TAG = "LoginFragment";
    private static final String NULL_CALLING_PKG_ERROR_MSG =
            "Cannot call LoginFragment with a null calling package. " +
                    "This can occur if the launchMode of the caller is singleInstance.";
    private static final String SAVED_LOGIN_CLIENT = "loginClient";

    private String callingPackage;
    private LoginMyClient loginClient;
    private LoginMyClient.Request request;

    private LoginClient originClient;
    private LoginClient.Request originClientRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            loginClient = savedInstanceState.getParcelable(SAVED_LOGIN_CLIENT);
            loginClient.setFragment(this);
            originClient = savedInstanceState.getParcelable(SAVED_LOGIN_CLIENT);
            originClient.setFragment(this);
        } else {
            loginClient = createLoginClient();
            originClient = new LoginClient(this);
        }

        loginClient.setOnCompletedListener(new LoginClient.OnCompletedListener() {
            @Override
            public void onCompleted(LoginClient.Result outcome) {
                onLoginClientCompleted(outcome);
            }
        });
        originClient.setOnCompletedListener(new LoginClient.OnCompletedListener() {
            @Override
            public void onCompleted(LoginClient.Result outcome) {
                onLoginClientCompleted(outcome);
            }
        });
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }

        initializeCallingPackage(activity);
        Intent intent = activity.getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra(REQUEST_KEY);
            if (bundle != null) {
                request = bundle.getParcelable(EXTRA_REQUEST);
                originClientRequest = bundle.getParcelable(EXTRA_REQUEST);
            }
        }
    }

    protected LoginMyClient createLoginClient() {
        return new LoginMyClient(this, originClient);
    }

    @Override
    public void onDestroy() {
        loginClient.cancelCurrentHandler();
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(getLayoutResId(), container, false);
        final View progressBar = view.findViewById(R.id.com_facebook_login_fragment_progress_bar);

        loginClient.setBackgroundProcessingListener(
                new LoginClient.BackgroundProcessingListener() {
                    @Override
                    public void onBackgroundProcessingStarted() {
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onBackgroundProcessingStopped() {
                        progressBar.setVisibility(View.GONE);
                    }
                });

        return view;
    }

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.com_facebook_login_fragment;
    }

    private void onLoginClientCompleted(LoginClient.Result outcome) {
        request = null;

        int resultCode = (outcome.code == LoginClient.Result.Code.CANCEL) ?
                Activity.RESULT_CANCELED : Activity.RESULT_OK;

        Bundle bundle = new Bundle();
        bundle.putParcelable(RESULT_KEY, outcome);

        Intent resultIntent = new Intent();
        resultIntent.putExtras(bundle);

        // The activity might be detached we will send a cancel result in onDetach
        if (isAdded()) {
            getActivity().setResult(resultCode, resultIntent);
            getActivity().finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // If the calling package is null, this generally means that the callee was started
        // with a launchMode of singleInstance. Unfortunately, Android does not allow a result
        // to be set when the callee is a singleInstance, so we log an error and return.
        if (callingPackage == null) {
            Log.e(TAG, NULL_CALLING_PKG_ERROR_MSG);
            getActivity().finish();
            return;
        }

        loginClient.startOrContinueAuth(request);
    }

    @Override
    public void onPause() {
        super.onPause();

        final View progressBar = getView() == null ?
                null :
                getView().findViewById(R.id.com_facebook_login_fragment_progress_bar);
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginClient.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(SAVED_LOGIN_CLIENT, loginClient);
    }

    private void initializeCallingPackage(final Activity activity) {
        ComponentName componentName = activity.getCallingActivity();
        if (componentName == null) {
            return;
        }
        callingPackage = componentName.getPackageName();
    }

    LoginClient getLoginClient() {
        return loginClient;
    }
//    private LoginFragment originLoginFragment;
//    private LoginClient originLoginClient;
//    private LoginMyClient loginMyClient;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        LogManager.d("huehn fb LoginMyFragment onCreate in");
//        LoginClient loginClient = getOriginLoginClient();
//        if (loginClient != null){
//            loginMyClient = new LoginMyClient(this, loginClient);
//            LogManager.d("huehn fb LoginMyFragment onCreate loginClient : " + loginClient);
//        }else {
//            loginMyClient = (LoginMyClient) new LoginClient(this);
//            LogManager.d("huehn fb LoginMyFragment onCreate loginClient == null");
//        }
//        loginMyClient.setOnCompletedListener(new LoginClient.OnCompletedListener() {
//            @Override
//            public void onCompleted(LoginClient.Result outcome) {
//                onLoginClientCompleted(outcome);
//            }
//        });
//
//        updateLoginClient();
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//
//
//
//    }
//
//    /**
//     * 把自己的loginClient代替原来的loginClient
//     */
//    private void updateLoginClient(){
//        if (loginMyClient != null){
//            try {
//                Field loginClientField = LoginFragment.class.getDeclaredField("loginClient");
//                loginClientField.setAccessible(true);
//                originLoginClient = (LoginClient) loginClientField.get(this);
//                loginClientField.set(this, loginMyClient);
//                LogManager.d("huehn fb LoginMyFragment updateLoginClient originLoginClient : "
//                        + originLoginClient + "   loginMyClient : " + loginMyClient);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private LoginClient getOriginLoginClient(){
//        try {
//            Field loginClientField = LoginFragment.class.getDeclaredField("loginClient");
//            loginClientField.setAccessible(true);
//            originLoginClient = (LoginClient) loginClientField.get(this);
//            LogManager.d("huehn fb LoginMyFragment getOriginLoginClient originLoginClient : "
//                    + originLoginClient);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return originLoginClient;
//    }
//
//    private void onLoginClientCompleted(LoginClient.Result outcome){
//        try {
//            Method method = LoginClient.class.getDeclaredMethod("onLoginClientCompleted", LoginClient.Result.class);
//            method.invoke(loginMyClient, outcome);
//            LogManager.d("huehn fb LoginMyFragment onLoginClientCompleted");
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }

}
