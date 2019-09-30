package com.huehn.initword.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.huehn.initword.core.utils.Log.LogManager;

public class ViewPagerForDrawer extends ViewPager {

    private IPageChange iPageChange;
    private int touchLimitX;
    private int touchLimitY;

    public ViewPagerForDrawer(@NonNull Context context) {
        this(context, null);
    }

    public ViewPagerForDrawer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    private void initView(){
        addListener();
    }

    private void addListener(){
        this.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setiPageChange(IPageChange iPageChange) {
        this.iPageChange = iPageChange;
    }

    public void setTouchLimitXY(int touchLimitX, int touchLimitY){
        this.touchLimitX = touchLimitX;
        this.touchLimitY = touchLimitY;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                if (getCurrentItem() == getChildCount() - 1){
//                    if (ev.getRawX() > touchLimitX && ev.getRawY() > touchLimitY){
//                        if (iPageChange != null){
//                            iPageChange.requestDrawerLayout(getCurrentItem());
//                        }
//                        LogManager.d("huehn dispatchTouchEvent getCurrentItem() : " + getCurrentItem() + "      getChildCount : " + getChildCount());
//                        return true;
//                    }
//                }
//                break;
//        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {


        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        float x = 0f;

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                x = ev.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (getCurrentItem() == getChildCount() - 1){
                    if (iPageChange != null){
                        iPageChange.requestDrawerLayout(getCurrentItem(), ev.getRawX() - x);
                    }
                    LogManager.d("huehn dispatchTouchEvent getCurrentItem() : " + getCurrentItem() + "      getChildCount : " + getChildCount());
                }
                break;
        }

        return super.onTouchEvent(ev);
    }

    public interface IPageChange{
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
        void onPageSelected(int position);
        void onPageScrollStateChanged(int state);
        void requestDrawerLayout(int position, float distance);
    }
}
