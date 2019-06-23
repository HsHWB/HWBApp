package com.huehn.initword.ui.anim;

import android.view.animation.Animation;

public interface IAnimationBuilder<T> {

    Animation.AnimationListener getAnimationListener();
    T create();
    

}
