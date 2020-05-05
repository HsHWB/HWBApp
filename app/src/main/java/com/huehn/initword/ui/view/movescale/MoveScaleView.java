package com.huehn.initword.ui.view.movescale;

import android.content.Context;
import android.graphics.Matrix;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.huehn.initword.R;
import com.huehn.initword.core.app.App;
import com.huehn.initword.core.utils.Log.LogManager;
import com.huehn.initword.core.utils.SystemUtils.ViewUtils;

public class MoveScaleView extends ViewGroup {

    private ImageView backImg;
    private ScaleView scaleView;
    private GestureDetectorCompat gestureDetectorCompat;
    private MoveGesture moveGesture;
    private MoveListener moveListener;

    //图片缩放、移动操作矩阵
    private Matrix matrix = new Matrix();
    //图片原来已经缩放、移动过的操作矩阵
    private Matrix savedMatrix = new Matrix();
    //用于存放矩阵的9个值
    private final float[] matrixValues = new float[9];

    private int rightEndWidth = DEFAULT_RIGHT_END_WIDTH;
    private int rightEndHeight = DEFAULT_RIGHT_END_WIDTH;
    private static float MAX_SCALE = 2.0f;//最大放大倍数，具体数值在初始化之后，获得的初始化比例再乘2
    private final static int DEFAULT_RIGHT_END_WIDTH = ViewUtils.dip2px(20);
    private final static int DEFAULT_VIEW_WIDTH = ViewUtils.dip2px(100);

    public MoveScaleView(Context context) {
        super(context);
        init();
    }

    public MoveScaleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MoveScaleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        addView();
        moveListener = new MoveListener(){
            @Override
            protected boolean onDown(MotionEvent e) {
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
                move(e1, e2);
                return true;
            }
        };
        moveGesture = new MoveGesture(moveListener);
        gestureDetectorCompat = new GestureDetectorCompat(this.getContext(), moveGesture);
        gestureDetectorCompat.setIsLongpressEnabled(false);
    }

    private void move(MotionEvent e1, MotionEvent e2){
        if (e1 != null && e2 != null){
            scaleByMatrix(e1, e2);
            this.getMatrix();
            float downX = e1.getX();
            float currentX = e2.getX();
            float downY = e1.getY();
            float currentY = e2.getY();

            int diffX = (int) (getLeft() + currentX - downX);
            int diffy = (int) (getTop() + currentY - downY);
            this.layout(diffX, diffy, diffX + getMeasuredWidth(), diffy + getMeasuredHeight());
        }
    }

    private void scaleByMatrix(MotionEvent e1, MotionEvent e2){

        if (e1 != null && e2 != null){
            float downX = e1.getX();
            float currentX = e2.getX();
            float downY = e1.getY();
            float currentY = e2.getY();

            float diffY = currentY - downY;
            if (Math.abs(diffY) < 10){
                return;
            }
            float scale = getScale();
            matrix.set(savedMatrix);
            if (diffY >= 0) {
                if (scale >= MAX_SCALE){
                    LogManager.d("huehn scaleByMatrix too big or too small: " + getScale());
                    return;
                }
                matrix.postScale(backImg.getScaleX() * 1.1f, backImg.getScaleY() * 1.1f);
            }else {
                if (scale <= MAX_SCALE / 2){
                    LogManager.d("huehn scaleByMatrix too big or too small: " + getScale());
                    return;
                }
                matrix.postScale(backImg.getScaleX() * 0.9f, backImg.getScaleY() * 0.9f);
            }
            savedMatrix.set(matrix);
            backImg.setImageMatrix(matrix);
        }
    }

    /**
     * 获得当前的缩放比例
     */
    public final float getScale() {
        matrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    private void addView(){

        backImg = new ImageView(getContext());
        scaleView = new ScaleView(getContext());

        ViewGroup.LayoutParams backImageParams = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(backImg, backImageParams);
        backImg.setScaleType(ImageView.ScaleType.MATRIX);
        backImg.setImageDrawable(App.getApp().getDrawable(R.drawable.br_finish_subscriber));
        Matrix matrix = backImg.getMatrix();
        if (matrix != null){
            float scale = DEFAULT_VIEW_WIDTH * 1.0f / DEFAULT_RIGHT_END_WIDTH;
            matrix.postScale(scale, scale);
            MAX_SCALE = scale * 2;
            backImg.setImageMatrix(matrix);
            savedMatrix.set(matrix);
        }

        ViewGroup.LayoutParams scaleViewParams = new ViewGroup.LayoutParams(rightEndWidth, rightEndHeight);
        addView(scaleView, scaleViewParams);
        scaleView.setImageDrawable(App.getApp().getDrawable(R.drawable.br_dialog_close_icon));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);   //获取宽的模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec); //获取高的模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);   //获取宽的尺寸
        int heightSize = MeasureSpec.getSize(heightMeasureSpec); //获取高的尺寸
        int width;
        int height ;
        if (widthMode == MeasureSpec.EXACTLY) {
            //如果match_parent或者具体的值，直接赋值
            width = widthSize;
        } else {
            //如果是wrap_content，我们要得到控件需要多大的尺寸
            float textWidth = 2 * DEFAULT_VIEW_WIDTH;
            //控件的宽度就是文本的宽度加上两边的内边距。内边距就是padding值，在构造方法执行完就被赋值
            width = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            LogManager.d("huehn onMeasure width : " + width);
        }
        //高度跟宽度处理方式一样
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            float textHeight = 2 * DEFAULT_VIEW_WIDTH;
            height = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            LogManager.d("huehn onMeasure height " + height);
        }
        //保存测量宽度和测量高度
        setMeasuredDimension(width, height);

    }

    /**
      * @param changed  该参数指出当前ViewGroup的尺寸或者位置是否发生了改变
      * @param l,t,r,b  当前ViewGroup相对于父控件的坐标位置，注意 ，一定是相对于父控件。
      * 函数的参数l,t,r,b，也是由该VieGroup的父控件传过来的
    */
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int mViewGroupWidth = getMeasuredWidth(); //当前ViewGroup的总宽度
        int mViewGroupHeight = getMeasuredHeight();

        int childCount = getChildCount();//子控件的数量

        if (childCount <= 0){
            return;
        }

        for (int i = 0; i < childCount; i++){
            View view = this.getChildAt(i);
            if (view == null){
                continue;
            }
            if (view instanceof ImageView && !(view instanceof ScaleView)){
                view.layout(0, 0, mViewGroupWidth, mViewGroupHeight);
            }
            if (view instanceof ScaleView){
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams == null){
                    continue;
                }
                int left = mViewGroupWidth - rightEndWidth;
                int top = mViewGroupHeight - rightEndHeight;
                if (left < 0 || top < 0){
                    continue;
                }
                view.layout(left, top, mViewGroupWidth, mViewGroupHeight);
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetectorCompat != null){
            return gestureDetectorCompat.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }
}
