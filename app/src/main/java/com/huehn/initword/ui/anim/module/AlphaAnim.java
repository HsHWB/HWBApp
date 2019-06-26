package com.huehn.initword.ui.anim.module;

import android.view.View;
import android.view.animation.AlphaAnimation;

import com.huehn.initword.ui.anim.BaseNormalAnimation;

public class AlphaAnim extends BaseNormalAnimation<AlphaAnim.AlphaBuilder, AlphaAnimation> {

    private AlphaAnimation alphaAnimation;
    private AlphaAnim.AlphaBuilder alphaBuilder;
    public AlphaAnim(AlphaBuilder alphaBuilder) {
        super(alphaBuilder);
        this.alphaBuilder = alphaBuilder;
    }

    @Override
    public AlphaAnimation createAnim(AlphaBuilder alphaBuilder) {
        if (alphaBuilder == null){
            return null;
        }
        alphaAnimation = new AlphaAnimation(alphaBuilder.beginAlpha, alphaBuilder.endAlpha);
        if (alphaBuilder.getAnimationListener() != null) {
            alphaAnimation.setAnimationListener(alphaBuilder.getAnimationListener());
        }
        if (alphaAnimation.getInterpolator() != null){
            alphaAnimation.setInterpolator(alphaAnimation.getInterpolator());
        }
        alphaAnimation.setFillAfter(alphaBuilder.isFillAfter());
        alphaAnimation.setDuration(alphaBuilder.duration);
        return alphaAnimation;
    }

    @Override
    public AlphaAnimation getAnimation() {
        return alphaAnimation;
    }

    @Override
    public void onStart(View view) {
        if (view != null && alphaAnimation != null){
            view.startAnimation(alphaAnimation);
        }
    }

    @Override
    public void onStop(View view) {
        if (view != null && alphaAnimation != null ){
            alphaAnimation.cancel();
        }
    }


    public static class AlphaBuilder extends BaseNormalAnimation.Builder<AlphaAnim, AlphaBuilder>{

        public float beginAlpha;
        public float endAlpha;

        public float getBeginAlpha() {
            return beginAlpha;
        }

        public AlphaBuilder setBeginAlpha(float beginAlpha) {
            this.beginAlpha = beginAlpha;
            return this;
        }

        public float getEndAlpha() {
            return endAlpha;
        }

        public AlphaBuilder setEndAlpha(float endAlpha) {
            this.endAlpha = endAlpha;
            return this;
        }

        @Override
        public AlphaAnim create() {
            return new AlphaAnim(this);
        }
    }
}
