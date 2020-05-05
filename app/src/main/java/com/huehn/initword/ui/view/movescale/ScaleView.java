package com.huehn.initword.ui.view.movescale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class ScaleView extends ImageView {
    public ScaleView(Context context) {
        super(context);
        init();
    }

    public ScaleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScaleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        this.setScaleType(ScaleType.MATRIX);
    }

}
