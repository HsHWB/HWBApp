package com.huehn.initword.ui.view.viewpager;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class GreatPageTransformer implements ViewPager.PageTransformer {

    public final float MIN_SCALE = 0.9f;
    public final float MIN_TRANSPARENT = 0.6f;

    @Override
    public void transformPage(@NonNull View page, float position) {
//        LogManager.d("huehn transformPage position : " + position + "      page : " + ((IItemView)page).getView());
        float scale = Math.max(MIN_SCALE, 1 - Math.abs(position));
        float transparent = Math.max(MIN_TRANSPARENT, 1 - Math.abs(position));
        //position小于等于1的时候，代表page已经位于中心item的最左边，
        //此时设置为最小的缩放率
        if (position <= -1){
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
            page.setAlpha(MIN_TRANSPARENT);
        }else if (position < 0){//position从0变化到-1，page逐渐向左滑动
            page.setScaleX(scale);
            page.setScaleY(scale);
            page.setAlpha(transparent);
        }else if (position >= 0 && position < 1){//position从0变化到1，page逐渐向右滑动
            page.setScaleX(scale);
            page.setScaleY(scale);
            page.setAlpha(transparent);
        }else if (position >= 1){//position大于等于1的时候，代表page已经位于中心item的最右边
            page.setScaleX(scale);
            page.setScaleY(scale);
            page.setAlpha(transparent);
        }

    }
}
