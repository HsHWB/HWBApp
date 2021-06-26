package com.huehn.initword.basecomponent.manager;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.huehn.initword.basecomponent.base.BaseActivity;

public class PermissionManager {

    private Fragment fragment;
    private FragmentManager fragmentManager;
    public static final String FRAGMENT_TAG = "FRAGMENT_TAG";
    public PermissionManager(BaseActivity baseActivity) {
        if (baseActivity != null) {
            fragmentManager = baseActivity.getSupportFragmentManager();
            fragment = new Fragment();
            fragmentManager.beginTransaction().add(fragment, FRAGMENT_TAG).commitAllowingStateLoss();
        }
    }

    public void requestPermission(){

    }

    public void permissionResult(){

    }
}
