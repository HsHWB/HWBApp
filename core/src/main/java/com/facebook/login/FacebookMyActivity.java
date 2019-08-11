package com.facebook.login;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.facebook.FacebookActivity;
import com.facebook.internal.FacebookDialogFragment;
import com.facebook.share.internal.DeviceShareDialogFragment;
import com.facebook.share.model.ShareContent;

public class FacebookMyActivity extends FacebookActivity {

    protected Fragment getFragment() {
        Intent intent = getIntent();
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag("SingleFragment");
        Toast.makeText(getBaseContext(), "activity", Toast.LENGTH_SHORT).show();
        if (fragment == null) {
            if (FacebookDialogFragment.TAG.equals(intent.getAction())) {
                FacebookDialogFragment dialogFragment = new FacebookDialogFragment();
                dialogFragment.setRetainInstance(true);
                dialogFragment.show(manager, "SingleFragment");

                fragment = dialogFragment;
            } else if (DeviceShareDialogFragment.TAG.equals(intent.getAction())) {
                DeviceShareDialogFragment dialogFragment = new DeviceShareDialogFragment();
                dialogFragment.setRetainInstance(true);
                dialogFragment.setShareContent((ShareContent) intent.getParcelableExtra("content"));
                dialogFragment.show(manager, "SingleFragment");
                fragment = dialogFragment;
            } else {
                fragment = new LoginFragment();
                fragment.setRetainInstance(true);
                manager.beginTransaction()
                        .add(R.id.com_facebook_fragment_container, fragment, "SingleFragment")
                        .commit();
            }
        }
        return fragment;
    }

}
