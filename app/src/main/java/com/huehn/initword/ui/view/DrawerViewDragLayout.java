package com.huehn.initword.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.huehn.initword.core.utils.Log.LogManager;

public class DrawerViewDragLayout extends RelativeLayout {

    private ViewDragHelper viewDragHelper;
    private ViewDragHelper.Callback callback;

    public DrawerViewDragLayout(Context context) {
        this(context, null);
    }

    public DrawerViewDragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawerViewDragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView(){
        callback = new ViewDragHelper.Callback() {

            //tryCaptureView如果返回ture则表示可以捕获该view，你可以根据传入的第一个view参数决定哪些可以捕获
            @Override
            public boolean tryCaptureView(@NonNull View view, int i) {
                LogManager.d("huehn initView tryCaptureView view : " + view);
                return true;
            }

            //可以在该方法中对child移动的边界进行控制，left , top 分别为即将移动到的位置，比如横向的情况下，我希望只在ViewGroup的内部移动，
            // 即：最小>=paddingleft，最大<=ViewGroup.getWidth()-paddingright-child.getWidth
            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
//                final int leftBound = getPaddingLeft();
//                final int rightBound = getWidth() - child.getWidth() - leftBound;
//
//                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
                LogManager.d("huehn initView clampViewPositionHorizontal left : " + left);
                return left;
            }

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                LogManager.d("huehn initView clampViewPositionVertical top : " + top);
                return top;
            }
        };

        viewDragHelper = ViewDragHelper.create(this, callback);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }
}
