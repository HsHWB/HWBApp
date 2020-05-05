package com.huehn.initword.core.utils.SystemUtils;

import android.app.Service;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.huehn.initword.core.BuildConfig;
import com.huehn.initword.core.app.App;
import com.umeng.commonsdk.statistics.SdkVersion;

import java.lang.reflect.InvocationTargetException;

public class ViewUtils {

    /**
     * 获取屏幕高度
     * @return
     */
    public static int getScreenHeight(){
        int heightPixels = 0;
        WindowManager windowManager = (WindowManager) App.getApp().getSystemService(Service.WINDOW_SERVICE);
        if (windowManager == null) {
            return heightPixels;
        }
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        heightPixels = metrics.heightPixels;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1){
            try {
                heightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            Point realSize = new Point();
            try {
                Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            heightPixels = realSize.y;
        }
        return heightPixels;
    }

    /**
     * 获取屏幕宽度
     * @return
     */
    public static int getScreenWidth(){
        int widthPixels = 0;
        WindowManager windowManager = (WindowManager) App.getApp().getSystemService(Service.WINDOW_SERVICE);
        if (windowManager == null){
            return widthPixels;
        }
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        widthPixels = metrics.widthPixels;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1){
            try {
                widthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            Point realSize = new Point();
            try {
                Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            widthPixels = realSize.x;
        }
        return widthPixels;
    }

    /**
     * dip转换px
     *
     * @param dipValue 要转换的dip值
     * @return
     */
    public static int dip2px(float dipValue) {
        final float scale = App.getApp().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px转换dip
     * @return
     */
    public static int px2dip(float pxValue) {
        final float scale = App.getApp().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
