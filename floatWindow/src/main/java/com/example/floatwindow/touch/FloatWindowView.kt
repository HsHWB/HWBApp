package com.example.floatwindow.touch

import android.view.*

class FloatWindowView : WindowTouchView {

    private var parentWindow : Window? = null
    private var parentViewGroup : ViewGroup? = null
    private var targetView : View? = null
    private var layoutParams : WindowManager.LayoutParams? = null
    private var isFloatOtherApp : Boolean = false

    constructor(parentWindow : Window?, targetView : View?, isFloatOtherApp : Boolean) {
        this.parentWindow = parentWindow
        this.targetView = targetView
        this.isFloatOtherApp = isFloatOtherApp
        super.initView()
        addListener(this)
    }

    constructor(parentViewGroup : ViewGroup, targetView : View?, isFloatOtherApp : Boolean) {
        this.parentViewGroup = parentViewGroup
        this.targetView = targetView
        this.isFloatOtherApp = isFloatOtherApp
        super.initView()
        addListener(this)
    }

    override fun target(): View? {
        return targetView
    }

    override fun onDown(event: MotionEvent?) {
    }

    override fun onScroll(event: MotionEvent?, distanceX: Float, distanceY: Float) {
        if (onlyTranslationY()) {
            translationY(distanceY, getYDirection(distanceY))
        } else if (onlyTranslationX()) {
            translationX(distanceX, getXDirection(distanceX))
        } else {
            translationXY(distanceX, distanceY, getXDirection(distanceX), getYDirection(distanceY))
        }
    }

    override fun getParentTouchWindow(): Window? {
        return parentWindow
    }

    override fun parentWindowManager(): WindowManager.LayoutParams? {
        return layoutParams
    }



    override fun onlyTranslationY(): Boolean {
        return true
    }

    override fun parent(): View? {
        return null
    }

    override fun onSingleTapUp(event: MotionEvent?) {
    }

    override fun isCanOutOfParent(): Boolean {
        return false
    }

    override fun isWindowView(): Boolean {
        return isFloatOtherApp
    }

    override fun onlyTranslationX(): Boolean {
        return false
    }

    override fun isHasAnimationWhenBackToParent(): Boolean {
        return false
    }

    fun show(x : Int, y : Int, width : Int, height : Int){
        if (isWindowView()) {
            takeIf { targetView != null && parentWindow != null }?.run {
                layoutParams = targetView?.layoutParams as WindowManager.LayoutParams?
                layoutParams?.run {
                    this.gravity = Gravity.START or Gravity.TOP
                    this.x = x
                    this.y = y
                    this.width = width
                    this.height = height
                    parentWindow?.windowManager?.addView(targetView, layoutParams)
                }
            }
        } else {
            takeIf { targetView != null && parentViewGroup != null }?.run {
                parentViewGroup?.addView(targetView)
            }
        }
    }

}