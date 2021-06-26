package com.huehn.initword.ui.view.viewpager;

import android.content.Context;

import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * @author huehn
 * 3d滑动首页
 */
public class GreatViewPager extends ViewPager {

    private IPagerCallback iPagerCallback;
    private GreatViewPagerAdapter greatViewPagerAdapter;

    public GreatViewPager(@NonNull Context context) {
        super(context);
        init();
    }

    public GreatViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setiPagerCallback(IPagerCallback iPagerCallback) {
        this.iPagerCallback = iPagerCallback;
    }

    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {
            checkFirstPositionOrLastPosition(i,v);
            if (GreatViewPager.this.iPagerCallback != null){
                GreatViewPager.this.iPagerCallback.onPageScrolled(i, v, i1);
            }
        }

        @Override
        public void onPageSelected(int i) {
            if (GreatViewPager.this.iPagerCallback != null && getChildAt(i) instanceof IItemView){
                GreatViewPager.this.iPagerCallback.onPageSelected((IItemView) getChildAt(i), i);
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {
            if (GreatViewPager.this.iPagerCallback != null){
                GreatViewPager.this.iPagerCallback.onPageScrollStateChanged(i);
            }
        }
    };

    public void init(){
        this.addOnPageChangeListener(onPageChangeListener);
    }

    public void setGreatViewPagerAdapter(GreatViewPagerAdapter greatViewPagerAdapter){
        if (greatViewPagerAdapter != null){
            this.greatViewPagerAdapter = greatViewPagerAdapter;
            this.setAdapter(greatViewPagerAdapter);
        }
    }

    public void checkFirstPositionOrLastPosition(int position, float offset){
//        if (greatViewPagerAdapter != null && greatViewPagerAdapter.getDataList() != null && greatViewPagerAdapter.getDataList().size() > 2 && offset == 0) {
//            if (position == 0) {
//                setCurrentItem(greatViewPagerAdapter.getDataList().size() - 2, false);
//            } else if (position == (greatViewPagerAdapter.getDataList().size() - 1)) {
//                setCurrentItem(1, false);
//            }
//        }
    }

}
