package com.facebook.login;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.facebook.FacebookActivity;
import com.facebook.internal.FacebookDialogFragment;
import com.facebook.share.internal.DeviceShareDialogFragment;
import com.facebook.share.model.ShareContent;
import com.huehn.initword.core.R;
import com.huehn.initword.core.utils.Log.LogManager;

public class FacebookLoginActivity extends com.facebook.FacebookActivity {

    protected Fragment getFragment() {
        Intent intent = getIntent();
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag("SingleFragment");
        Toast.makeText(getBaseContext(), "activity", Toast.LENGTH_SHORT).show();
        if (fragment == null) {
            if (FacebookDialogFragment.TAG.equals(intent.getAction())) {
                LogManager.d("huehn fb FacebookLoginActivity getFragment 1");
                FacebookDialogFragment dialogFragment = new FacebookDialogFragment();
                dialogFragment.setRetainInstance(true);
                dialogFragment.show(manager, "SingleFragment");

                fragment = dialogFragment;
            }
            else if (DeviceShareDialogFragment.TAG.equals(intent.getAction())) {
                LogManager.d("huehn fb FacebookLoginActivity getFragment 2");
                DeviceShareDialogFragment dialogFragment = new DeviceShareDialogFragment();
                dialogFragment.setRetainInstance(true);
                dialogFragment.setShareContent((ShareContent) intent.getParcelableExtra("content"));
                dialogFragment.show(manager, "SingleFragment");
                fragment = dialogFragment;
            } else {
                LogManager.d("huehn fb FacebookLoginActivity getFragment 3");
                fragment = new LoginMyFragment();
                fragment.setRetainInstance(true);
                manager.beginTransaction()
                        .add(R.id.com_facebook_fragment_container, fragment, "SingleFragment")
                        .commit();
            }
        }
        return fragment;
    }

}
