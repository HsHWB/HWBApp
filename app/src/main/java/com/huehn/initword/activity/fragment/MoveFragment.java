package com.huehn.initword.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.huehn.initword.R;
import com.huehn.initword.basecomponent.base.BaseFragment;
import com.huehn.initword.ui.view.movescale.MoveScaleView;

public class MoveFragment extends BaseFragment {

    private MoveScaleView moveScaleView;

    public static MoveFragment getInstance(){
        Bundle bundle = new Bundle();
        MoveFragment moveFragment = new MoveFragment();
        moveFragment.setArguments(bundle);
        return moveFragment;
    }

    @Override
    public void getBundle(Bundle bundle) {

    }

    @Override
    public void initView(View view) {
        moveScaleView = view.findViewById(R.id.move_view);
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_move;
    }
}
