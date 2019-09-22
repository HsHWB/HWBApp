package com.huehn.initword.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.huehn.initword.core.utils.Log.LogManager;

public class CornerHtml5WebView extends WebView {

    private int x;
    private int y;
    private Paint paint;
    private Path path;
    private Paint paint2;
    public final static int DEFAULT_RADIUS = 50;
    private int radius = DEFAULT_RADIUS;

    public final static int TOP_PADING = 10;
    public final static int BOTTOM_PADING = 10;
    public final static int LEFT_PADING = 5;
    public final static int RIGHT_PADING = 5;
    public final static int WIDTH = 200;
    public final static int HEIGHT = 50;
    public CornerHtml5WebView(Context context) {
        super(context);
        init();
    }

    public CornerHtml5WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CornerHtml5WebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint2 = new Paint();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(WIDTH, HEIGHT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.drawColor(Color.WHITE);
        x = this.getScrollX();//webView当前滑动的x位置
        y = this.getScrollY();//webview当前滑动的y位置
        LogManager.d("huehn onDraw x : " + x + "  y : " + y);
        path = new Path();
        path.addPath(drawLeftTop(path));
        path.addPath(drawRightTop(path));
        path.addPath(drawBottom(path));
        path.close();
//        canvas.clipPath(path);
        canvas.drawPath(path, paint);
        canvas.restore();
    }


    private Path drawLeftTop(Path path){
        path.moveTo(x + radius + LEFT_PADING, y + TOP_PADING);
        RectF rectF = new RectF();
        rectF.left = x + LEFT_PADING;
        rectF.right = radius + RIGHT_PADING;
        rectF.bottom = radius;
        rectF.top = y + TOP_PADING;
        path.addArc(rectF, -180, 90);
        path.lineTo(WIDTH - radius - RIGHT_PADING, y + TOP_PADING);
        LogManager.d("huehn onDraw drawLeftTop rectF top : " + rectF.top + "   right : " + rectF.right + "  bottom : " + rectF.bottom + "  left : " + rectF.left);
        return path;
    }

    private Path drawRightTop(Path path){
        path.moveTo(WIDTH - radius - RIGHT_PADING, y + TOP_PADING);
        RectF rectF = new RectF();
        rectF.left = WIDTH - radius - RIGHT_PADING;
        rectF.right = WIDTH - RIGHT_PADING;
        rectF.bottom = radius;
        rectF.top = y + TOP_PADING;
        path.addArc(rectF, -90, 90);
        LogManager.d("huehn onDraw drawRightTop rectF top : " + rectF.top + "   right : " + rectF.right + "  bottom : " + rectF.bottom + "  left : " + rectF.left);
        return path;
    }

    private Path drawBottom(Path path){
        path.moveTo(WIDTH - RIGHT_PADING, radius + TOP_PADING);
        path.lineTo(WIDTH, HEIGHT);
        path.lineTo(x, HEIGHT);
        path.lineTo(x, y);

        LogManager.d("huehn onDraw drawBottom rectF WIDTH : " + WIDTH + "   HEIGHT : " + HEIGHT + "  x : " + x + "  y : " + y);
        return path;
    }


}

