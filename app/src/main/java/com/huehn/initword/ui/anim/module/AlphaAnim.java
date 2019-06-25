package com.huehn.initword.ui.anim.module;

import android.view.View;
import android.view.animation.AlphaAnimation;

import com.huehn.initword.ui.anim.BaseNormalAnimation;

public class AlphaAnim extends BaseNormalAnimation<AlphaAnim.AlphaBuilder, AlphaAnimation> {


    public AlphaAnim(AlphaBuilder alphaBuilder) {
        super(alphaBuilder);
    }

    @Override
    public AlphaAnimation createAnim(AlphaBuilder alphaBuilder) {
        if (alphaBuilder == null){
            return null;
        }
        AlphaAnimation alphaAnimation = new AlphaAnimation(alphaBuilder.beginAlpha, alphaBuilder.endAlpha);
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
    public void onStart(View view) {

    }

    @Override
    public void onStop(View view) {

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
