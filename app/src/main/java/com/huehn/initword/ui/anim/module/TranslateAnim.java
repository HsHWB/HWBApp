package com.huehn.initword.ui.anim.module;

import android.view.View;
import android.view.animation.TranslateAnimation;

import com.huehn.initword.ui.anim.BaseNormalAnimation;

public class TranslateAnim extends BaseNormalAnimation<TranslateAnim.Builder, TranslateAnimation> {

    private TranslateAnimation translateAnimation;
    private TranslateAnim.Builder iAnimationBuilder;


    public TranslateAnim(TranslateAnim.Builder iAnimationBuilder) {
        super(iAnimationBuilder);
        this.iAnimationBuilder = iAnimationBuilder;
        
        if (iAnimationBuilder.getAnimationListener() != null){
            this.translateAnimation.setAnimationListener(iAnimationBuilder.getAnimationListener());
        }
        this.translateAnimation.setFillAfter(this.iAnimationBuilder.isFillAfter());
    }

    @Override
    public void onStart(View view) {
        if (view != null && translateAnimation != null){
            view.startAnimation(translateAnimation);
        }
    }

    @Override
    public void onStop(View view) {
        if (view != null && translateAnimation != null){
            translateAnimation.cancel();
        }
    }


    @Override
    public TranslateAnimation createAnim(Builder builder) {
        if (builder != null) {
            if (builder.getFromX() >= 0 && builder.getFromY() >=0 && builder.getToX() >=0
                    && builder.getToY() >=0){
                translateAnimation = new TranslateAnimation(builder.getFromX(), builder.getFromY(),
                        builder.getToX(), builder.getToY());
            }else {
                throw new RuntimeException("参数小于0");
            }
        }else {
            translateAnimation = new TranslateAnimation(0, 0, 0, 0);
        }
        return translateAnimation;
    }


    public static class Builder extends BaseNormalAnimation.Builder<TranslateAnim, Builder>{

        public int fromX;
        public int fromY;
        public int toX;
        public int toY;

        public Builder() {
            super();
        }


        public int getFromX() {
            return fromX;
        }

        public TranslateAnim.Builder setFromX(int fromX) {
            this.fromX = fromX;
            return this;
        }

        public int getFromY() {
            return fromY;
        }

        public TranslateAnim.Builder setFromY(int fromY) {
            this.fromY = fromY;
            return this;
        }

        public int getToX() {
            return toX;
        }

        public TranslateAnim.Builder setToX(int toX) {
            this.toX = toX;
            return this;
        }

        public int getToY() {
            return toY;
        }

        public TranslateAnim.Builder setToY(int toY) {
            this.toY = toY;
            return this;
        }

        @Override
        public TranslateAnim create() {
            return new TranslateAnim(this);
        }
    }

}
