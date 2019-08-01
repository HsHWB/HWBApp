package com.huehn.initword.core.utils.hook;

import android.view.View;
import android.widget.Toast;

/**
 * 改变原来代码的点击事件
 */
public class HookClickLinstener implements View.OnClickListener {

    private View.OnClickListener originListener;

    public HookClickLinstener(View.OnClickListener originListener) {
        this.originListener = originListener;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), "hook listener", Toast.LENGTH_SHORT).show();
        if (originListener != null){
            originListener.onClick(v);
        }
    }
}
