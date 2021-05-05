package com.example.floatwindow.touch

import android.view.View
import android.view.Window
import android.view.WindowManager
import com.huehn.initword.core.utils.Log.LogManager

/**
 */
abstract class BaseWindowTranslationMove : BaseWindowTouch(){

    private var parent : View? = null // 移动view的参考边界值view
    private var target : View? = null // 移动的view
    private var parentWindow : Window? = null
    private var parentWindowManagerLayout : WindowManager.LayoutParams? = null //是否通过windowManager去修改布局
    private var borderLine = arrayOf(0f, 0f, 0f, 0f) //左边界，上边界，右边界，下边界。正常来说是传进来的parent的坐标值。
    private var newSize = arrayOf(0f, 0f, 0f, 0f) //如果需要调整边界值，这里储存调整后的targetView位置
    private var isBorderLineUpdate = false
    companion object {
            const val TAG = "BaseTranslationMove"
            const val TO_UP = 1
            const val TO_DOWN = 2
            const val TO_LEFT = 3
            const val TO_RIGHT = 4
    }

    public fun initView(){
        initView(true)
    }

    private fun initView(isClearListener: Boolean) {
        if (isWindowView()) {
            if (parentWindowManagerLayout == null) {
                parentWindowManagerLayout = parentWindowManager()
            }
            getParentView(getParentTouchWindow())
        }else {
            getParentView(parent())
        }
        getTargetView(target())
        if (isClearListener) {
            super.initListener()
        }
    }

    public fun translationX(scrollX : Float, direction: Int){
        if ((parent == null && parentWindowManagerLayout == null)  || target == null) {
            initView(false)
        }
        updateBorder()
        LogManager.d("BaseTranslationMove", "huehn BaseTranslationMove : translationX : $scrollX     direction : $direction")

        translationAnimationOnlyDirection(direction, scrollX)
    }

    public fun translationY(scrollY : Float, direction: Int){
        if ((parent == null && parentWindowManagerLayout == null) || target == null) {
            initView(false)
        }
        updateBorder()
        translationAnimationOnlyDirection(direction, scrollY)
    }

    public fun translationXY(scrollX : Float, scrollY: Float, directionX: Int, directionY : Int) {
        if ((parent == null && parentWindowManagerLayout == null)  || target == null) {
            initView(false)
        }
        updateBorder()
        translationAnimationAll(scrollX, scrollY)
    }

    /**
     * 只在x或者y轴的移动
     */
    private fun translationAnimationOnlyDirection(direction : Int, scroll : Float){
        LogManager.d("BaseWindowTranslationMove", "huehn BaseWindowTranslationMove translationAnimationOnlyDirection : direction : $direction"
            + "     scroll : $scroll")
        if (isWindowView()){
            parentWindowManagerLayout?.run {
                var distance = scroll.toInt()
                when (direction) {
                    TO_DOWN, TO_UP -> {
                        this.y = this.y + distance
                    }
                    TO_LEFT, TO_RIGHT -> {
                        this.x = this.x + distance
                    }
                }
                parentWindow?.windowManager?.updateViewLayout(target, this)
            }
        } else {
            target?.run {
                when (direction) {
                    TO_DOWN, TO_UP -> {
                        var distance = scroll.toInt()
                        if (isCanOutOfParent()) {
                            this.layout(this.left, this.top + distance, this.right, this.bottom + distance)
                        } else {
                            checkIsOutOfParent(this, 0f, scroll)
                            newSize?.let {
                                this.layout(it[0].toInt(), it[1].toInt(), it[2].toInt(), it[3].toInt())
                            }
                        }
                    }
                    TO_LEFT, TO_RIGHT -> {
                        var distance = scroll.toInt()
                        if (isCanOutOfParent()) {
                            this.layout(this.left + distance, this.top, this.right + distance, this.bottom)
                        } else {
                            checkIsOutOfParent(this, scroll, 0f)
                            newSize?.let {
                                this.layout(it[0].toInt(), it[1].toInt(), it[2].toInt(), it[3].toInt())
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * x, y轴都移动
     */
    private fun translationAnimationAll(scrollX : Float, scrollY : Float) {
        LogManager.d("BaseWindowTranslationMove", "huehn BaseWindowTranslationMove translationAnimationAll : scrollX : $scrollX"
                + "     scrollY : $scrollY")
        if (isWindowView()){
            parentWindowManagerLayout?.run {
                var distanceX = scrollX.toInt()
                var distanceY = scrollY.toInt()
                this.x = this.x + distanceX
                this.y = this.y + distanceY
                parentWindow?.windowManager?.updateViewLayout(target, this)
            }
        } else {
            target?.run {
                var distanceX = scrollX.toInt()
                var distanceY = scrollY.toInt()
                if (isCanOutOfParent()) {
                    this.layout(this.left + distanceX, this.top + distanceY, this.right + distanceX, this.bottom + distanceY)
                } else {
                    checkIsOutOfParent(this, scrollX, scrollY)
                    newSize?.let {
                        this.layout(it[0].toInt(), it[1].toInt(), it[2].toInt(), it[3].toInt())
                    }
                }
            }
        }
    }

    /**
     * 强制更新一下边界值
     */
    public fun forceUpdateBorder(){
        isBorderLineUpdate = false
        borderLine = arrayOf(0f, 0f, 0f, 0f)
        updateBorder()
    }

    private fun updateBorder(){
        if (isBorderLineUpdate) {
            return
        }

        if (isWindowView()) {
            parentWindowManagerLayout?.run {
                isBorderLineUpdate = true
                //窗口的绝对位置x，如果有gravity，比如gravity.left，那么就会算上边距值
                borderLine[0] = this.x.toFloat()
                borderLine[1] = this.y.toFloat()
                borderLine[2] = borderLine[0] + this.width
                borderLine[3] = borderLine[1] + this.height
            }
        } else {
            parent?.run {
                isBorderLineUpdate = true
                //相对于父布局（parent的parent）中的x，用这个x才能限制边界，
                //不然用rawX的话，就是整个屏幕了
                borderLine[0] = this.x
                borderLine[1] = this.y //相对于父布局中的y
                borderLine[2] = this.x + this.width
                borderLine[3] = this.y + this.height
                LogManager.d("BaseTranslationMove", "huehn BaseTranslationMove : updateBorder : left : ${borderLine[0]}     top : ${borderLine[1]}" +
                        "     right : ${borderLine[2]}     bottom : ${borderLine[3]}")
            }
        }
    }

    /**
     * x轴移动的方向
     */
    public fun getXDirection(distanceX: Float): Int {
        return if (distanceX > 0) {
            TO_RIGHT
        } else {
            TO_LEFT
        }
    }

    /**
     * y轴移动的方向
     */
    public fun getYDirection(directionY: Float) : Int {
        return if (directionY > 0) {
            TO_DOWN
        } else {
            TO_UP
        }
    }

    /**
     * 检查移动的targetView超出了parentView的范围
     */
    private fun checkIsOutOfParent(view : View, distanceX : Float, distanceY : Float) : Array<Float>{
        if (newSize == null) {
            newSize = arrayOf(0f, 0f, 0f, 0f)
        }

        newSize[0] = view.left.toFloat()
        newSize[1] = view.top.toFloat()
        newSize[2] = view.right.toFloat()
        newSize[3] = view.bottom.toFloat()
        LogManager.d("BaseTranslationMove", "huehn BaseTranslationMove : updateNewSize : left : ${newSize[0]}     top : ${newSize[1]}" +
                "     right : ${newSize[2]}     bottom : ${newSize[3]}")

        //判断是否只移动x或者y轴，如果不是的话，那么都加上新的距离值然后跟父布局做判断
        if (onlyTranslationX()) {
            newSize[0] += distanceX
            newSize[2] += distanceX
        } else if (onlyTranslationY()) {
            newSize[1] += distanceY
            newSize[3] += distanceY
        } else {
            newSize[0] += distanceX
            newSize[1] += distanceY
            newSize[2] += distanceX
            newSize[3] += distanceY
        }

        //超出左边，将左边置为边界值。然后右边的距离减去超出的差值
        if (view.left + distanceX < borderLine[0]) {
            newSize[2] -= newSize[0] - borderLine[0] // newSize[0] - borderLine[0] 是移动时，超出的边距值
            newSize[0] = borderLine[0]
        }

        //超出上边，将上边置为边界值，然后将下边的距离减去超出的差值
        if (view.top + distanceY < borderLine[1]) {
            newSize[3] -= newSize[1] - borderLine[1]
            newSize[1] = borderLine[1]
        }

        //超出右边，将右边置为边界值，然后将左边的距离减去超出的差值
        if (view.right + distanceX > borderLine[2]) {
            newSize[0] -= newSize[2] - borderLine[2]
            newSize[2] = borderLine[2]
        }

        //超出下边，将下边置为边界值，然后将上边的距离减去超出的差值
        if (view.bottom + distanceY > borderLine[3]) {
            newSize[1] -= newSize[3] - borderLine[3]
            newSize[3] = borderLine[3]
        }

        return newSize
    }

    override fun targetView(): View? {
        return target
    }

    private fun getTargetView(view : View?) {
        if (target == null) {
            target = view
        }
    }

    private fun getParentView(view : View?) {
        if (parent == null) {
            parent = view
        }
    }

    private fun getParentView(window: Window?) {
        if (parentWindow == null) {
            parentWindow = window
        }
    }

    /**
     * 是否是基于WindowManager的悬浮窗
     */
    public abstract fun isWindowView() : Boolean

    /**
     * 设置父布局
     */
    public abstract fun parent() : View?
    public abstract fun getParentTouchWindow() : Window?
    public abstract fun parentWindowManager() : WindowManager.LayoutParams?
    /**
     * 设置移动的view
     */
    public abstract fun target() : View?

    /**
     * 是否能移出父布局外
     */
    public abstract fun isCanOutOfParent() : Boolean

    /**
     * 如果不能移出父布局外，那么是否需要动画回弹
     */
    public abstract fun isHasAnimationWhenBackToParent() : Boolean

    /**
     * 是否只在x轴上移动
     */
    public abstract fun onlyTranslationX() : Boolean

    /**
     * 是否只在y轴上移动
     */
    public abstract fun onlyTranslationY() : Boolean

}
