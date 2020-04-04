package com.huehn.initword.ui.view.viewpager;

public interface IItemView {

    //获取是图片type还是视频type
    int getCardType();

    void setData(ItemData itemData);


    @interface IType{
        int IMAGE_TYPE = 1;
        int STREAM_TYPE = 2;
    }
}
