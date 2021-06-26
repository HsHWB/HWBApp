package com.huehn.initword.ui.view.movescale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;

import com.huehn.initword.R;
import com.huehn.initword.core.utils.Log.LogManager;

@SuppressLint("AppCompatCustomView")
public class ScaleView extends ImageView {

    private GestureDetectorCompat gestureDetectorCompat;
    private MoveGesture moveGesture;
    private MoveListener moveListener;
    private MoveScaleView moveScaleView;
    private long curTime = 0;


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
        initListener();
        setBackgroundColor(getResources().getColor(R.color.color_ff6600));

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
                if (System.currentTimeMillis() - curTime < 80){
                    return true;
                }
                curTime = System.currentTimeMillis();
                if (moveScaleView != null){
                    moveScaleView.scaleByParams(e1, e2);
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
}
