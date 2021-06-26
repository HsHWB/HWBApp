package com.facebook.login;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;

import androidx.fragment.app.FragmentActivity;

import com.facebook.FacebookException;
import com.facebook.internal.FacebookDialogFragment;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.WebDialog;
import com.huehn.initword.core.utils.Log.LogManager;

import java.lang.reflect.Field;

public class WebViewLoginMyMethodHandler extends WebViewLoginMethodHandler{

    //这些字段还是要反射父类然后用子类的值去代替父类的值得，既然用子类WebViewLoginMyMethodHandler代替了父类了，但是有些
    //父类的方法没有重写，如果sdk别的地方调用了父类这个方法，这个方法恰好有loginDlialog或者e2e的使用的话，就会用回父类的值。
    private NewWebDialog myLoginDialog;
    private String myE2e;

    private WebViewLoginMethodHandler originHandler;

    WebViewLoginMyMethodHandler(LoginClient loginClient) {
        super(loginClient);
    }

    WebViewLoginMyMethodHandler(Parcel source) {
        super(source);
    }

    @Override
    boolean tryAuthorize(final LoginClient.Request request) {
        Bundle parameters = getParameters(request);

        LogManager.d("huehn fb WebViewLoginMyMethodHandler tryAuthorize in");

        NewWebDialog.OnCompleteListener listener = new NewWebDialog.OnCompleteListener() {
            @Override
            public void onComplete(Bundle values, FacebookException error) {
                onWebDialogComplete(request, values, error);
            }
        };

        myE2e = LoginClient.getE2E();
        addLoggingExtra(ServerProtocol.DIALOG_PARAM_E2E, myE2e);

        FragmentActivity fragmentActivity = loginClient.getActivity();
        final boolean isChromeOS = Utility.isChromeOS(fragmentActivity);

        NewWebDialog.Builder builder = new AuthDialogBuilder(
                fragmentActivity,
                request.getApplicationId(),
                parameters)
                .setE2E(myE2e)
                .setIsChromeOS(isChromeOS)
                .setAuthType(request.getAuthType())
                .setOnCompleteListener(listener);
        myLoginDialog = builder.build();

        FacebookDialogMyFragment dialogFragment = new FacebookDialogMyFragment();
        dialogFragment.setRetainInstance(true);
        dialogFragment.setDialog(myLoginDialog);
        dialogFragment.show(fragmentActivity.getSupportFragmentManager(),
                FacebookDialogFragment.TAG);

        replaceParams(myLoginDialog, myE2e);
        return true;
    }

    /**
     * 父类字段拷贝到子类。如果外部调用一些子类没有覆盖的方法，而这些方法有关于dialog和e2e的值得话，会返回
     * 非子类定义的值，所以这时要把值赋予父类的对应属性。
     * @param myWebDialog
     * @param e2e
     */
    private void replaceParams(NewWebDialog myWebDialog, String e2e){
        try {
            LogManager.d("huehn fb WebViewLoginMyMethodHandler replaceParams in");
            Field webDialogField = this.getClass().getDeclaredField("webDialog");
            Field e2eField = this.getClass().getDeclaredField("e2e");

            webDialogField.set(this, myWebDialog);
            e2eField.set(this, e2e);

        } catch (NoSuchFieldException e) {
            LogManager.d("huehn fb WebViewLoginMyMethodHandler replaceParams error");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            LogManager.d("huehn fb WebViewLoginMyMethodHandler replaceParams error");
            e.printStackTrace();
        }
    }

    static class AuthDialogBuilder extends NewWebDialog.Builder {

        private static final String OAUTH_DIALOG = "oauth";
        private String e2e;
        private String authType;
        private String redirect_uri = ServerProtocol.DIALOG_REDIRECT_URI;

        public AuthDialogBuilder(Context context, String applicationId, Bundle parameters) {
            super(context, applicationId, OAUTH_DIALOG, parameters);
        }

        public WebViewLoginMyMethodHandler.AuthDialogBuilder setE2E(String e2e) {
            this.e2e = e2e;
            return this;
        }

        /**
         * @deprecated This is no longer used
         * @return the AuthDialogBuilder
         */
        public WebViewLoginMyMethodHandler.AuthDialogBuilder setIsRerequest(boolean isRerequest) {
            return this;
        }

        public WebViewLoginMyMethodHandler.AuthDialogBuilder setIsChromeOS(final boolean isChromeOS) {
            redirect_uri = isChromeOS ?
                    ServerProtocol.DIALOG_REDIRECT_CHROME_OS_URI :
                    ServerProtocol.DIALOG_REDIRECT_URI;
            return this;
        }

        public WebViewLoginMyMethodHandler.AuthDialogBuilder setAuthType(final String authType) {
            this.authType = authType;
            return this;
        }

        @Override
        public NewWebDialog build() {
            Bundle parameters = getParameters();
            parameters.putString(ServerProtocol.DIALOG_PARAM_REDIRECT_URI, redirect_uri);
            parameters.putString(ServerProtocol.DIALOG_PARAM_CLIENT_ID, getApplicationId());
            parameters.putString(ServerProtocol.DIALOG_PARAM_E2E, e2e);
            parameters.putString(
                    ServerProtocol.DIALOG_PARAM_RESPONSE_TYPE,
                    ServerProtocol.DIALOG_RESPONSE_TYPE_TOKEN_AND_SIGNED_REQUEST);
            parameters.putString(
                    ServerProtocol.DIALOG_PARAM_RETURN_SCOPES,
                    ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
            parameters.putString(
                    ServerProtocol.DIALOG_PARAM_AUTH_TYPE,
                    authType);

            return NewWebDialog.newInstance(
                    getContext(),
                    OAUTH_DIALOG,
                    parameters,
                    getTheme(),
                    getListener());
        }
    }

}
