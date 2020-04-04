package com.huehn.initword.ui.view.viewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * @author huehn
 * 3d滑动首页
 */
public class GreatViewPager extends ViewPager implements IPagerCallback{


    public GreatViewPager(@NonNull Context context) {
        super(context);
    }

    public GreatViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


}
