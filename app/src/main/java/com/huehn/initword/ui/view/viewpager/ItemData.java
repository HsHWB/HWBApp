package com.huehn.initword.ui.view.viewpager;

import java.io.Serializable;

/**
 * 数据
 */
public class ItemData implements Serializable, IGreatViewPagerAdapterData{

    private int cardType;
    private String imageUrl = "";
    private String streamUrl = "";

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    @Override
    public String getImageUrl() {
        return null;
    }

    @Override
    public String getStreamUrl() {
        return null;
    }

    @Override
    public int cardType() {
        return cardType;
    }
}
