package com.example.floatwindow.touch;

import android.view.MotionEvent;

/**
 *
 */
public interface ITouchListener {
    public void onDown(MotionEvent event);
    public void onScroll(MotionEvent event, float distanceX, float distanceY);
    public void onSingleTapUp(MotionEvent event);
}
