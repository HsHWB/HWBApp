package com.huehn.initword.ui.view.movescale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.huehn.initword.core.utils.Log.LogManager;

@SuppressLint("AppCompatCustomView")
public class MoveImageView extends ImageView {

    private int defaultHeight;
    private int defaultWidth;
    private GestureDetectorCompat gestureDetectorCompat;
    private MoveGesture moveGesture;
    private MoveListener moveListener;
    private MoveScaleView moveScaleView;

    public MoveImageView(Context context) {
        super(context);
        initView();
    }

    public MoveImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MoveImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                MoveImageView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                defaultHeight = MoveImageView.this.getMeasuredHeight();
                defaultWidth = MoveImageView.this.getMeasuredWidth();
                LogManager.d("huehn initView defaultHeight : " + defaultHeight + "      defaultWidth : " + defaultWidth);
            }
        });
        initListener();
    }

    public void setScaleParentView(MoveScaleView moveScaleView){
        this.moveScaleView = moveScaleView;
    }

    private void initListener(){
        moveListener = new MoveListener(){
            @Override
            protected boolean onDown(MotionEvent e) {
                if (moveScaleView != null) {
                    moveScaleView.bringToFront();
                    moveScaleView.down();
                }
                return true;
            }

            @Override
            protected void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                LogManager.d("huehn move onLongPress");
            }

            /**
             * 指滑动的时候执行的回调（接收到MOVE事件，且位移大于一定距离），e1,e2分别是之前DOWN事件和当前的MOVE事件，
             * distanceX和distanceY就是当前MOVE事件和上一个MOVE事件的位移量。
             */
            @Override
            protected boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                LogManager.d("huehn move onScroll distanceX : " + distanceX + "      distanceY : " + distanceY);
//                move(e1, e2);
                if (moveScaleView != null){
                    moveScaleView.move(e1, e2);
                }
                return true;
            }
        };
        moveGesture = new MoveGesture(moveListener);
        gestureDetectorCompat = new GestureDetectorCompat(this.getContext(), moveGesture);
        gestureDetectorCompat.setIsLongpressEnabled(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetectorCompat != null){
            return gestureDetectorCompat.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }


    public void scale(MoveScaleView.ScaleData scale){
        if (scale == null){
            return;
        }
        if (scale.getScaleX() < 1){
            scale.setScaleX(1f);
            scale.setScaleY(1f);
        }else if (scale.getScaleY() > 2){
            scale.setScaleX(2f);
            scale.setScaleY(2f);
        }
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = (int) (defaultHeight * scale.getScaleX());
        layoutParams.width = (int) (defaultWidth * scale.getScaleX());
        LogManager.d("huehn scale height : " + layoutParams.height + "      width : " + layoutParams.width + "      scale : " + scale.getScaleX() + "      defaultHeight : " + defaultHeight + "      defaultWidth : " + defaultWidth);
        this.setLayoutParams(layoutParams);

    }

}
