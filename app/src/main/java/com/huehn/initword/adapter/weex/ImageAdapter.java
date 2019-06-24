package com.huehn.initword.adapter.weex;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.huehn.initword.core.app.App;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.dom.WXImageQuality;

public class ImageAdapter implements IWXImgLoaderAdapter {
    @Override
    public void setImage(String url, ImageView view, WXImageQuality quality, WXImageStrategy strategy) {
        Glide.with(App.getApp()).load(url).into(view);
    }
}
