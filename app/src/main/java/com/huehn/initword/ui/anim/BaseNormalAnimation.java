package com.huehn.initword.ui.anim;

import android.app.AlertDialog;
import android.view.animation.Animation;
import android.view.animation.Interpolator;

import com.huehn.initword.ui.anim.module.TranslateAnim;

/**
 * Android的基本View动画的基类，对应着TranslateAnimation,ScaleAnimation,RotateAnimation,AlphaAnimation
 * 不包括帧动画
 */
public abstract class BaseNormalAnimation<T extends IAnimationBuilder, R extends Animation> implements IAnimation{

    private T t;
    private int duration = 2000;
    private Animation.AnimationListener animationListener = null;

    public BaseNormalAnimation(T t) {
        createAnim(t);
    }

    /**
     * 创建一个类型为T的builder，T必须是IAnimationBuilder
     * 返回一个Animation对象R
     * @param t
     * @return
     */
    public abstract R createAnim(T t);


    /**
     * 这个builder返回会返回一个anim对象T，这个对象继承了BaseNormalAnimation
     * @param <T>
     */
    public abstract static class Builder<T extends BaseNormalAnimation, R extends Builder> implements IAnimationBuilder<T>{
        //这些基础的监听和时长是通用的，放在基类
        protected Animation.AnimationListener animationListener = null;
        public int duration = 2000;
        //保持动画后的状态
        public boolean fillAfter = false;
        //插值器
        public Interpolator interpolator;

        public Builder(){
        }

        public Animation.AnimationListener getAnimationListener() {
            return animationListener;
        }

        public R setAnimationListener(Animation.AnimationListener animationListener) {
            this.animationListener = animationListener;
            return (R) this;
        }

        public int getDuration() {
            return duration;
        }

        public R setDuration(int duration) {
            this.duration = duration;
            return (R) this;
        }

        public boolean isFillAfter() {
            return fillAfter;
        }

        public R setFillAfter(boolean fillAfter) {
            this.fillAfter = fillAfter;
            return (R) this;
        }

        public Interpolator getInterpolator() {
            return interpolator;
        }

        public R setInterpolator(Interpolator interpolator) {
            this.interpolator = interpolator;
            return (R) this;
        }
    }

}
