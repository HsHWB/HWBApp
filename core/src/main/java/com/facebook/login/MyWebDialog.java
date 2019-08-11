package com.facebook.login;


import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.internal.WebDialog;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            Toast.makeText(getContext(), "进入了onCreate并且try catch", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
