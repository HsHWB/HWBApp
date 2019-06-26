package com.huehn.initword.ui.utils;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;

public class AnimUtils {

    /**
     * 普通的动画集合(平移，旋转，缩放，透明度)
     * @param shareInterpolator 是否使用同个插值器
     * @param animation
     * @return
     */
    public static AnimationSet normalAnimationSet(final View view, boolean shareInterpolator, Animation... animation){
        final AnimationSet animationSet = new AnimationSet(shareInterpolator);
        for (Animation anim : animation){
            if (anim != null){
                animationSet.addAnimation(anim);
            }
        }
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            view.post(new Runnable() {
                @Override
                public void run() {
                    view.startAnimation(animationSet);
                }
            });
        }else {
            view.startAnimation(animationSet);
        }
        return animationSet;
    }

}
