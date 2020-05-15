package com.huehn.initword.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.huehn.initword.R;
import com.huehn.initword.basecomponent.base.BaseFragment;
import com.huehn.initword.core.utils.Log.LogManager;
import com.huehn.initword.ui.view.movescale.MoveScaleView;

public class MoveFragment extends BaseFragment {

    private RelativeLayout parentLayout;
    private MoveScaleView moveScaleView;
    private MoveScaleView moveScaleView2;
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
        parentLayout = view.findViewById(R.id.fragment_move_layout);
        moveScaleView = view.findViewById(R.id.move_view);
        moveScaleView2 = view.findViewById(R.id.move_view2);

        parentLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                parentLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                LogManager.d("huehn MoveFragment height : " + parentLayout.getMeasuredHeight() + "      width : " + parentLayout.getMeasuredWidth());
                moveScaleView.setParentSize(parentLayout.getMeasuredHeight(), parentLayout.getMeasuredWidth());
                moveScaleView2.setParentSize(parentLayout.getMeasuredHeight(), parentLayout.getMeasuredWidth());
            }
        });

    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_move;
    }
}
