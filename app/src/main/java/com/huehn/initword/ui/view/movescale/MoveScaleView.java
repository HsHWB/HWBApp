package com.huehn.initword.ui.view.movescale;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.huehn.initword.R;
import com.huehn.initword.core.app.App;
import com.huehn.initword.core.utils.Log.LogManager;
import com.huehn.initword.core.utils.SystemUtils.ViewUtils;

import static android.widget.RelativeLayout.CENTER_IN_PARENT;

public class MoveScaleView extends RelativeLayout {

    private MoveImageView backImg;
    private ScaleView scaleView;
    private Drawable drawable;
    private Drawable scaleDrawable;

    private int backHeight;
    private int backWidth;

    private ScaleData scaleData = new ScaleData(1f, 1f);
    private ScaleData saveScaleData = new ScaleData(1f, 1f);
    /**
     * 本次缩放有其他控件跟随，因此不用矩阵缩放imageview内容。而是直接缩放imageview本身大小
     */
//    //图片缩放、移动操作矩阵
//    private Matrix matrix = new Matrix();
//    //图片原来已经缩放、移动过的操作矩阵
//    private Matrix savedMatrix = new Matrix();
//    //用于存放矩阵的9个值
//    private final float[] matrixValues = new float[9];


    private int parentWidth;
    private int parentHeight;
    private int rightEndWidth = DEFAULT_RIGHT_END_WIDTH;
    private int rightEndHeight = DEFAULT_RIGHT_END_WIDTH;
    private final int backImgId = 0x001;
    private static float MAX_SCALE = 2.0f;//最大放大倍数，具体数值在初始化之后，获得的初始化比例再乘2
    private final static int DEFAULT_RIGHT_END_WIDTH = ViewUtils.dip2px(25);//放大缩小按钮的默认大小
    private final static int DEFAULT_VIEW_WIDTH = ViewUtils.dip2px(100);//背景的默认大小

    public MoveScaleView(Context context) {
        super(context);
        init(null);
    }

    public MoveScaleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MoveScaleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        initAttrs(attrs);
        addView();
//        initListener();
    }

    public void setParentSize(int parentHeight, int parentWidth){
        this.parentHeight = parentHeight;
        this.parentWidth = parentWidth;
    }

    private void initAttrs(AttributeSet attrs){
        if (attrs != null){
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.br_move_view);
            if (typedArray != null) {
                if (typedArray.hasValue(R.styleable.br_move_view_br_drawable)) {
                    drawable = typedArray.getDrawable(R.styleable.br_move_view_br_drawable);
                }
                if (typedArray.hasValue(R.styleable.br_move_view_br_scale_drawable)){
                    scaleDrawable = typedArray.getDrawable(R.styleable.br_move_view_br_scale_drawable);
                }
                typedArray.recycle();
            }
        }
    }

    public void down(){
        if (backImg != null) {
            backHeight = backImg.getMeasuredHeight();
            backWidth = backImg.getMeasuredWidth();
        }
    }

    /**
     * 移动
     * @param e1
     * @param e2
     */
    public void move(MotionEvent e1, MotionEvent e2){
        if (e1 != null && e2 != null){
//            scaleByParams(e1, e2);
            this.getMatrix();
            float downX = e1.getX();
            float currentX = e2.getX();
            float downY = e1.getY();
            float currentY = e2.getY();

            int diffX = (int) (getLeft() + currentX - downX);//可直接用的左边距
            int diffy = (int) (getTop() + currentY - downY);//可直接用的上边距

            if (diffX < 0){
                diffX = 0;
            }else if (diffX > (parentWidth - backWidth)){
                diffX = parentWidth - backWidth;
            }

            if (diffy < 0){
                diffy = 0;
            }else if (diffy > (parentHeight - backHeight)){
                diffy = parentHeight - backHeight;
            }

            this.setRelativeViewLocation(this, diffX, diffy, diffX + backWidth, diffy + backHeight);
        }
    }

    public void scaleByParams(MotionEvent e1, MotionEvent e2){
        if (e1 != null && e2 != null){
            float downX = e1.getX();
            float currentX = e2.getX();
            float downY = e1.getY();
            float currentY = e2.getY();

            float diffY = currentY - downY;
            float diffX = currentX - downX;
            double diff = Math.sqrt(diffY * diffY + diffX * diffX);

            if ((diffY > 0 && diffX < 0) || (diffX < 0 && diffY > 0)){//方向有左上，右上，左下，右下。忽略右上和左下。
                return;
            }

            if (diffY > 0 && diffX > 0){//如果方向是右下，则取正值
                diff = Math.abs(diff);
            }else {//如果方向是左上，则取负值
                diff = -Math.abs(diff);
            }

            if (Math.abs(diff) < 10){//直角三角形斜边长度
                return;
            }

            float scale = getScale();
            LogManager.d("huehn scaleByParams scale: " + scale + "      getScaleX : " + backImg.getScaleX() + "      getScaleY : " + backImg.getScaleY());
            scaleData.set(saveScaleData);
            if (diff >= 0) {
                if (scale >= MAX_SCALE){
                    LogManager.d("huehn scaleByMatrix too big or too small: " + getScale());
                    return;
                }
                scaleData.postScale(scaleData.getScaleX() * 1.1f, scaleData.getScaleY() * 1.1f);
            }else {
                if (scale <= MAX_SCALE / 2){
                    LogManager.d("huehn scaleByMatrix too big or too small: " + getScale());
                    return;
                }
                scaleData.postScale(scaleData.getScaleX() * 0.9f, scaleData.getScaleY() * 0.9f);
            }
            saveScaleData.set(scaleData);
            backImg.scale(scaleData);
        }
    }

    /**
     * 这个方法去设置view的位置，能解决bringToFront()时位置重置的问题。
     * 从源码能看到，bringToFront()最终会requestLayout()，然后会重新调viewGroup的onLayout。onLayout会重新把xml里面的配置（layoutParams）拿出来跑一边就调view的layout()，里面就用了layoutParems。
     * 因此此处move的时候，直接调layout()没有问题，但是bringToFront会重置位置。因此在bringToFront之前，更改一下当前的layoutParams。
     * @param view
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    private void setRelativeViewLocation(View view, int left, int top, int right, int bottom) {
        if (view == null){
            return;
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        if (params != null){
            LogManager.d("huehn setRelativeViewLocation left : " + left);
            params.addRule(CENTER_IN_PARENT, 0);
            params.setMargins(left, top, 0, 0);
            view.setLayoutParams(params);
        }
    }

    /**
     * 获得当前的缩放比例
     */
    public final float getScale() {
//        matrix.getValues(matrixValues);
//        return matrixValues[Matrix.MSCALE_X];
        return scaleData.getScaleX();
    }

    private void addView(){
        if (drawable == null){
            return;
        }

        backImg = new MoveImageView(getContext());
        backImg.setId(View.generateViewId());
        backImg.setScaleParentView(this);
        scaleView = new ScaleView(getContext());

        ViewGroup.LayoutParams backImageParams = new ViewGroup.LayoutParams(DEFAULT_VIEW_WIDTH, DEFAULT_VIEW_WIDTH);
        LogManager.d("huehn addView height : " + backImageParams.height + "      width : " + backImageParams.width);
        addView(backImg, backImageParams);
        backImg.setScaleType(ImageView.ScaleType.FIT_XY);
        backImg.setImageDrawable(drawable);

        if (scaleDrawable != null) {
            LogManager.d("huehn addView scaleDrawable");
            RelativeLayout.LayoutParams scaleViewParams = new RelativeLayout.LayoutParams(rightEndWidth, rightEndHeight);
            scaleViewParams.addRule(RelativeLayout.ALIGN_BOTTOM, backImg.getId());
            scaleViewParams.addRule(RelativeLayout.ALIGN_END, backImg.getId());
            addView(scaleView, scaleViewParams);
            scaleView.setScaleParentView(this);
            scaleView.setImageDrawable(scaleDrawable);
        }

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
            //如果是wrap_content，要得到控件需要多大的尺寸
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
     * 矩阵缩放
     * 此方法用在imageview的真实内容
     * 本次应该不能用这个方法。应该直接缩放imageview大小。
     */
//    private void scaleByMatrix(MotionEvent e1, MotionEvent e2){
//
//        if (e1 != null && e2 != null){
//            float downX = e1.getX();
//            float currentX = e2.getX();
//            float downY = e1.getY();
//            float currentY = e2.getY();
//
//            float diffY = currentY - downY;
//            if (Math.abs(diffY) < 10){
//                return;
//            }
//            float scale = getScale();
//            matrix.set(savedMatrix);
//            if (diffY >= 0) {
//                if (scale >= MAX_SCALE){
//                    LogManager.d("huehn scaleByMatrix too big or too small: " + getScale());
//                    return;
//                }
//                matrix.postScale(backImg.getScaleX() * 1.1f, backImg.getScaleY() * 1.1f);
//            }else {
//                if (scale <= MAX_SCALE / 2){
//                    LogManager.d("huehn scaleByMatrix too big or too small: " + getScale());
//                    return;
//                }
//                matrix.postScale(backImg.getScaleX() * 0.9f, backImg.getScaleY() * 0.9f);
//            }
//            savedMatrix.set(matrix);
//            backImg.setImageMatrix(matrix);
//        }
//    }


    public class ScaleData{
        private float scaleX = 1f;
        private float scaleY = 1f;

        public ScaleData(float scaleX, float scaleY) {
            this.scaleX = scaleX;
            this.scaleY = scaleY;
        }

        public float getScaleX() {
            return scaleX;
        }

        public void setScaleX(float scaleX) {
            this.scaleX = scaleX;
        }

        public float getScaleY() {
            return scaleY;
        }

        public void setScaleY(float scaleY) {
            this.scaleY = scaleY;
        }

        public void postScale(float scaleX, float scaleY){
            this.scaleX = scaleX;
            this.scaleY = scaleY;
        }

        public void set(ScaleData scaleData){
            if (scaleData != null){
                scaleX = scaleData.getScaleX();
                scaleY = scaleData.getScaleY();
            }
        }
    }
}
