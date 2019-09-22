package com.huehn.initword.ui.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.huehn.initword.core.utils.Log.LogManager;
import com.huehn.initword.core.utils.SystemUtils.ViewUtils;

public class TwoSidesSeekbar extends View {

    private ISidesChangeListener iSidesChangeListener;

    private static final int DEFUALT_VIEW_WIDTH = 450;
    private static final int DEFUALT_VIEW_HEIGHT = 80;
    private static final int DEFAULT_CIRCLE_WIDTH = 50;

    public TwoSidesSeekbar(Context context) {
        super(context, null);
    }

    public TwoSidesSeekbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }


    public TwoSidesSeekbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }


    private void initView(@Nullable AttributeSet attrs){
        this.setClickable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

//        LogManager.d("huehn onTouchEvent event : " + event.getAction());

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                LogManager.d("huehn onTouchEvent event : ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                LogManager.d("huehn onTouchEvent event : ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                LogManager.d("huehn onTouchEvent event : ACTION_UP");
                break;
        }

        return super.onTouchEvent(event);
    }


    /**
     * 先裁剪一下控件大小
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getDefaultSize(DEFUALT_VIEW_WIDTH,widthMeasureSpec);
        int height = getDefaultSize(DEFUALT_VIEW_HEIGHT,heightMeasureSpec);

        LogManager.d("huehn onMeasure height : " + MeasureSpec.getSize(heightMeasureSpec) +
                "   width : " + MeasureSpec.getSize(widthMeasureSpec));

        //将计算的宽和高设置进去，保存，最后一步一定要有
        setMeasuredDimension(width,height);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 从源码扣出来的
     * measureSpec不是一个简单int值
     * Utility to return a default size. Uses the supplied size if the
     * MeasureSpec imposed no constraints. Will get larger if allowed
     * by the MeasureSpec.
     *
     * @param size Default size for this view
     * @param measureSpec Constraints imposed by the parent
     * @return The size this view should be.
     */
    public static int getDefaultSize(int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                LogManager.d("huhen onMeasure specMode : UNSPECIFIED");
                break;
            case MeasureSpec.AT_MOST:///相当于我们设置为wrap_content
                LogManager.d("huhen onMeasure specMode : AT_MOST");
            case MeasureSpec.EXACTLY:////相当于我们设置为match_parent或者为一个具体的值
                result = size;
                LogManager.d("huhen onMeasure specMode : EXACTLY");
                break;
        }
        LogManager.d("huhen onMeasure result : " + result);
        return result;
    }

    /**
     * 然后在画出来
     * onLayout这个规定位置的就不管了，由调用者决定就好。
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);

    }

    /**
     * 判断当前触摸的位置是在哪里
     * @param x
     * @param y
     * @return
     */
    private int touchWhichItem(int x, int y){
        return TwoSidesSeekBarStatus.OUT_SIDES;
    }

    public TwoSidesSeekbar setISidesChangeListener(ISidesChangeListener iSidesChangeListener){
        this.iSidesChangeListener = iSidesChangeListener;
        return this;
    }

    public interface ISidesChangeListener{
        void onChange(int left, int right);
    }

}
/*

     * @param defualtSize   设置的默认大小
     * @param measureSpec   父控件传来的widthMeasureSpec，heightMeasureSpec
     * @return  结果

public int measureDimension(int defualtSize,int measureSpec){
    int result=defualtSize;
    int specMode=MeasureSpec.getMode(measureSpec);
    int specSize=MeasureSpec.getSize(measureSpec);

    //1,layout中自定义组件给出来确定的值，比如100dp
    //2,layout中自定义组件使用的是match_parent，但父控件的size已经可以确定了，比如设置的具体的值或者match_parent
    if(specMode==MeasureSpec.EXACTLY){
        result=specSize;
    }
    //layout中自定义组件使用的wrap_content
    else if(specMode==MeasureSpec.AT_MOST){
        result=Math.min(defualtSize,specSize);//建议：result不能大于specSize
    }
    //UNSPECIFIED,没有任何限制，所以可以设置任何大小
    else {
        result=defualtSize;
    }
    return result;
}


 */