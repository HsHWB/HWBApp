package com.huehn.initword.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;


public class FreeMicGridView extends GridView {
    private static final String TAG = "FreeMicGridView";

    public FreeMicGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public FreeMicGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FreeMicGridView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return false;

            case MotionEvent.ACTION_MOVE:
                return false;

            case MotionEvent.ACTION_UP:

                return true;
            default:
                break;

        }

        return super.onTouchEvent(ev);
    }


}
