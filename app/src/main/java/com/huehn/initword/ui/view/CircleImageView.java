package com.huehn.initword.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

/**
 * weex用的自定义view
 */

public class CircleImageView extends WXComponent<ImageView> {


    public CircleImageView(WXSDKInstance instance, WXVContainer parent, BasicComponentData basicComponentData) {
        super(instance, parent, basicComponentData);
    }

    @Override
    protected ImageView initComponentHostView(@NonNull Context context) {
        ImageView view = new ImageView(context);
        return view;
    }

    @WXComponentProp(name = "setSrc")
    public void setImage(String url) {
        Glide.with(getContext()).load(url).into((ImageView) getHostView());
    }
}
