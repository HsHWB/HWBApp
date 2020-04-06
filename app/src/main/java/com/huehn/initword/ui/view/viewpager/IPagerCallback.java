package com.huehn.initword.ui.view.viewpager;

public interface IPagerCallback {

    void onPageScrolled(int i, float v, int i1);

    void onPageSelected(IItemView itemView, int i);

    void onPageScrollStateChanged(int i);

}
