package com.huehn.initword.ui.view.viewpager;

import android.view.View;

public interface IItemView {

    //获取是图片type还是视频type
    int getCardType();

    void setData(ItemData itemData);

    View getView();

    @interface IType{
        int IMAGE_TYPE = 1;
        int STREAM_TYPE = 2;
    }
}
