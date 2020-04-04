package com.huehn.initword.ui.view.viewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;

/**
 * @author huehn
 * 3d滑动首页
 */
public class GreatViewPager extends ViewPager implements IPagerCallback{

    private GreatViewPagerAdapter greatViewPagerAdapter;

    public GreatViewPager(@NonNull Context context) {
        super(context);
    }



}
