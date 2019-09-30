package com.huehn.initword.basecomponent.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.animation.Animation;

public class BaseFragment extends Fragment {

    public void replace(FragmentManager fragmentManager, int layoutId){
        if (fragmentManager == null || layoutId == 0){
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(layoutId, this);
        transaction.commitAllowingStateLoss();
    }

}
