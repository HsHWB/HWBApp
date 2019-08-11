package com.facebook.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.internal.FacebookDialogFragment;
import com.facebook.internal.FacebookWebFallbackDialog;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.WebDialog;


public class FacebookDialogMyFragment extends FacebookDialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            Toast.makeText(getContext(), "进入onCreate，catch异常", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
