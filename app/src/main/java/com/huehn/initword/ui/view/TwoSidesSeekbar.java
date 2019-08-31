package com.huehn.initword.ui.view;


import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class TwoSidesSeekbar extends View {

    private ISidesChangeListener iSidesChangeListener;

    public TwoSidesSeekbar(Context context) {
        super(context);
    }

    public TwoSidesSeekbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TwoSidesSeekbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void initView(){

    }

    public interface ISidesChangeListener{
        void onChange(int left, int right);
    }

}
