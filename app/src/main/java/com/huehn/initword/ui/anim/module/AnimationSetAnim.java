package com.huehn.initword.ui.anim.module;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;

import com.huehn.initword.ui.anim.BaseNormalAnimation;

import java.util.ArrayList;
import java.util.List;

public class AnimationSetAnim extends BaseNormalAnimation<AnimationSetAnim.AnimationSetBuilder, AnimationSet> {

    private AnimationSetBuilder animationSetBuilder;
    private AnimationSet animationSet;

    public AnimationSetAnim(AnimationSetBuilder animationSetBuilder) {
        super(animationSetBuilder);
    }

    @Override
    public AnimationSet createAnim(AnimationSetBuilder animationSetBuilder) {
        this.animationSetBuilder = animationSetBuilder;
        if (animationSetBuilder == null){
            animationSet = new AnimationSet(true);
            return animationSet;
        }
        animationSet = new AnimationSet(animationSetBuilder.isShareInterpolator());
        if (animationSetBuilder.animations != null){
            for (Animation animation : animationSetBuilder.animations){
                if (animation != null){
                    animationSet.addAnimation(animation);
                }
            }
        }
        return animationSet;
    }

    @Override
    public AnimationSet getAnimation() {
        return animationSet;
    }

    @Override
    public void onStart(View view) {
        if (view != null){
            view.startAnimation(animationSet);
        }
    }

    @Override
    public void onStop(View view) {
        if (view != null && animationSet != null){
            animationSet.cancel();
        }
    }

    public static class AnimationSetBuilder extends BaseNormalAnimation.Builder<AnimationSetAnim, AnimationSetAnim.AnimationSetBuilder>{

        private List<Animation> animations = new ArrayList<>();
        private boolean shareInterpolator = true;

        public AnimationSetBuilder setAnimations(Animation... animationArgs){
            animations.clear();
            for (Animation animation : animationArgs){
                if (animation != null){
                    animations.add(animation);
                }
            }
            return this;
        }

        public List<Animation> getAnimations() {
            return animations;
        }

        public boolean isShareInterpolator() {
            return shareInterpolator;
        }

        public AnimationSetBuilder setShareInterpolator(boolean shareInterpolator) {
            this.shareInterpolator = shareInterpolator;
            return this;
        }

        @Override
        public AnimationSetAnim create() {
            return new AnimationSetAnim(this);
        }
    }

}
