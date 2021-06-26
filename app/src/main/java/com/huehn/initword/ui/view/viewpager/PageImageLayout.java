package com.huehn.initword.ui.view.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.huehn.initword.R;

public class PageImageLayout extends LinearLayout implements IItemImageView {
    public PageImageLayout(Context context) {
        super(context);
        initView();
    }

    public PageImageLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PageImageLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        View.inflate(getContext(), R.layout.adpater_page_item_image_layout, this);
    }

    @Override
    public void setData(ItemData itemData){

    }

    @Override
    public String getImageUrl() {
        return null;
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public int getCardType() {
        return IType.STREAM_TYPE;
    }
}
