package com.huehn.initword.ui.view.viewpager;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class PageStreamLayout extends LinearLayout implements IItemStreamView {
    public PageStreamLayout(Context context) {
        super(context);
    }

    public PageStreamLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PageStreamLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getCardType() {
        return IType.IMAGE_TYPE;
    }

    @Override
    public String getStreamUrl() {
        return null;
    }


    @Override
    public void setData(ItemData itemData) {

    }
}
