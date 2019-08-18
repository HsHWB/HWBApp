package com.facebook.login;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.internal.WebDialog;
import com.facebook.share.internal.ShareConstants;
import com.facebook.share.widget.ShareDialog;

import java.lang.reflect.Constructor;
import java.util.Locale;

public class MyWebDialog extends WebDialog {
    /**
     * Constructor which can be used to display a dialog with an already-constructed URL.
     *
     * @param context the context to use to display the dialog
     * @param url     the URL of the Web Dialog to display; no validation is done on this URL, but it should
     */
    protected MyWebDialog(Context context, String url) {
        super(context, url);
    }


    /**
     * 本来是个构造方法，想想还是算了，不能指定一个super构造，用回父类构造要反射，干脆写成一个方法吧。
     * @param context
     * @param action
     * @param parameters
     * @param theme
     * @param listener
     */
    public MyWebDialog(
            Context context,
            String action,
            Bundle parameters,
            int theme,
            OnCompleteListener listener) {
            super(context, "");
//            super(context, theme == 0 ? getWebDialogTheme() : theme);
            hook4ParamsConstructor(context, action, parameters, theme, listener);
    }

    private static void hook4ParamsConstructor(Context context,
                                        String action,
                                        Bundle parameters,
                                        int theme,
                                        OnCompleteListener listener){
        try {
            /*可以使用相对路径，同一个包中可以不用带包路径*/
            Class c = WebDialog.class;

            /*以下调用带参的、私有构造函数*/
            Constructor c1=c.getDeclaredConstructor(new Class[]{
                    Context.class,
                    String.class,
                    Bundle.class,
                    int.class,
                    OnCompleteListener.class
            });
            c1.setAccessible(true);
            c1.newInstance(new Object[]{
                    context,
                    action,
                    parameters,
                    theme,
                    listener
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            Toast.makeText(getContext(), "进入了onCreate并且try catch", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static MyWebDialog newInstance(
            Context context,
            String action,
            Bundle parameters,
            int theme,
            OnCompleteListener listener) {
        initDefaultTheme(context);
        return new MyWebDialog(context, action, parameters, theme, listener);

    }

    public static class Builder {
        private Context context;
        private String applicationId;
        private String action;
        private int theme;
        private OnCompleteListener listener;
        private Bundle parameters;
        private AccessToken accessToken;

        /**
         * Constructor that builds a dialog using either the current access token, or the
         * application id specified in the application/meta-data.
         *
         * @param context the Context within which the dialog will be shown.
         * @param action the portion of the dialog URL following www.facebook.com/dialog/.
         *               See https://developers.facebook.com/docs/reference/dialogs/ for details.
         * @param parameters a Bundle containing parameters to pass as part of the URL.
         */
        public Builder(Context context, String action, Bundle parameters) {
            accessToken = AccessToken.getCurrentAccessToken();
            if (!AccessToken.isCurrentAccessTokenActive()) {
                String applicationId = Utility.getMetadataApplicationId(context);
                if (applicationId != null) {
                    this.applicationId = applicationId;
                } else {
                    throw new FacebookException("Attempted to create a builder without a valid" +
                            " access token or a valid default Application ID.");
                }
            }

            finishInit(context, action, parameters);
        }

        /**
         * Constructor that builds a dialog without an authenticated user.
         *
         * @param context the Context within which the dialog will be shown.
         * @param applicationId the application ID to be included in the dialog URL.
         * @param action the portion of the dialog URL following www.facebook.com/dialog/.
         *               See https://developers.facebook.com/docs/reference/dialogs/ for details.
         * @param parameters a Bundle containing parameters to pass as part of the URL.
         */
        public Builder(Context context, String applicationId, String action, Bundle parameters) {
            if (applicationId == null) {
                applicationId = Utility.getMetadataApplicationId(context);
            }
            Validate.notNullOrEmpty(applicationId, "applicationId");
            this.applicationId = applicationId;

            finishInit(context, action, parameters);
        }

        /**
         * Sets a theme identifier which will be passed to the underlying Dialog.
         *
         * @param theme a theme identifier which will be passed to the Dialog class
         * @return the builder
         */
        public MyWebDialog.Builder setTheme(int theme) {
            this.theme = theme;
            return this;
        }

        /**
         * Sets the listener which will be notified when the dialog finishes.
         *
         * @param listener the listener to notify, or null if no notification is desired
         * @return the builder
         */
        public MyWebDialog.Builder setOnCompleteListener(OnCompleteListener listener) {
            this.listener = listener;
            return this;
        }

        /**
         * Constructs a WebDialog using the parameters provided. The dialog is not shown,
         * but is ready to be shown by calling Dialog.show().
         *
         * @return the WebDialog
         */
        public WebDialog build() {
            if (accessToken != null) {
                parameters.putString(
                        ServerProtocol.DIALOG_PARAM_APP_ID,
                        accessToken.getApplicationId());
                parameters.putString(
                        ServerProtocol.DIALOG_PARAM_ACCESS_TOKEN,
                        accessToken.getToken());
            } else {
                parameters.putString(ServerProtocol.DIALOG_PARAM_APP_ID, applicationId);
            }

            return WebDialog.newInstance(context, action, parameters, theme, listener);
        }

        public String getApplicationId() {
            return applicationId;
        }

        public Context getContext() {
            return context;
        }

        public int getTheme() {
            return theme;
        }

        public Bundle getParameters() {
            return parameters;
        }

        public WebDialog.OnCompleteListener getListener() {
            return listener;
        }

        private void finishInit(Context context, String action, Bundle parameters) {
            this.context = context;
            this.action = action;
            if (parameters != null) {
                this.parameters = parameters;
            } else {
                this.parameters = new Bundle();
            }
        }
    }
}
