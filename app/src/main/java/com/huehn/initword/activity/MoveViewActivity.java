package com.huehn.initword.activity;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.huehn.initword.R;
import com.huehn.initword.activity.fragment.MoveFragment;
import com.huehn.initword.activity.fragment.RecyclerViewFragment;
import com.huehn.initword.basecomponent.base.BaseActivity;
import com.huehn.initword.basecomponent.base.BaseFragment;

public class MoveViewActivity extends BaseActivity {

    private FrameLayout moveLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_view);
        initView();
    }

    private void initView(){
        moveLayout = findViewById(R.id.move_frame_layout);
//        addFragment(R.id.move_frame_layout, MoveFragment.getInstance(), MoveFragment.class.getSimpleName());
        addFragment(R.id.move_frame_layout, RecyclerViewFragment.Companion.getInstance(),
                RecyclerViewFragment.class.getSimpleName());
    }

    private void addFragment(int repaceId, BaseFragment baseFragment, String tag){
        if (getSupportFragmentManager() != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(repaceId, baseFragment, tag);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }
}
