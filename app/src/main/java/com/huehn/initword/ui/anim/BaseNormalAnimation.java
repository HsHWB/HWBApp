package com.huehn.initword.ui.anim;

import android.app.AlertDialog;
import android.view.animation.Animation;

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
    public abstract static class Builder<T extends BaseNormalAnimation> implements IAnimationBuilder<T>{

    }

}
