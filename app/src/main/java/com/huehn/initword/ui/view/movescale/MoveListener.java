package com.huehn.initword.ui.view.movescale;

import android.view.MotionEvent;

public class MoveListener{

    protected boolean onDown(MotionEvent e) {
        return false;
    }

    protected void onShowPress(MotionEvent e) {

    }

    protected boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    protected boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    protected void onLongPress(MotionEvent e) {

    }

    protected boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
