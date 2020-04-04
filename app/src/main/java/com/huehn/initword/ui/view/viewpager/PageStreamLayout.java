package com.huehn.initword.ui.view.viewpager;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.View;
import android.widget.LinearLayout;

import com.huehn.initword.R;

public class PageStreamLayout extends LinearLayout implements IItemStreamView {

    private TextureView textureView;

    public PageStreamLayout(Context context) {
        super(context);
        initView();
    }

    public PageStreamLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PageStreamLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        View.inflate(getContext(), R.layout.adpater_page_item_stream_layout, this);
        textureView = findViewById(R.id.texture);
//        textureView.
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

    @Override
    public View getView() {
        return this;
    }
}
