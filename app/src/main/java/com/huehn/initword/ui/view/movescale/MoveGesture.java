package com.huehn.initword.ui.view.movescale;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class MoveGesture implements GestureDetector.OnGestureListener {

    private MoveListener moveListener;

    public MoveGesture(MoveListener moveListener) {
        this.moveListener = moveListener;
    }

    /**
     * 按下 刚刚手指接触到触摸屏的那一刹那，就是触的那一下
     */
    @Override
    public boolean onDown(MotionEvent e) {
        if (moveListener != null){
            return moveListener.onDown(e);
        }
        return false;
    }

    /**
     * 按住 手指按在触摸屏上，它的时间范围在按下起效，在长按之前
     */
    @Override
    public void onShowPress(MotionEvent e) {
        if (moveListener != null){
            moveListener.onShowPress(e);
        }
    }

    /**
     * 抬起 手指离开触摸屏的那一刹那
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if (moveListener != null){
            return moveListener.onSingleTapUp(e);
        }
        return false;
    }

    /**
     * 滚动 手指在触摸屏上滑动
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (moveListener != null){
            return moveListener.onScroll(e1, e2, distanceX, distanceY);
        }
        return false;
    }

    /**
     * 长按 手指按在持续一段时间，并且没有松开
     */
    @Override
    public void onLongPress(MotionEvent e) {
        if (moveListener != null){
            moveListener.onLongPress(e);
        }
    }

    /**
     * 抛掷 手指在触摸屏上迅速移动，并松开的动作
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (moveListener != null){
            return moveListener.onFling(e1, e2, velocityX, velocityY);
        }
        return false;
    }

}
