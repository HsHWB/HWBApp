package com.example.floatwindow.touch

import android.view.MotionEvent
import android.view.View

/**
 */
abstract class BaseWindowTouch {

    companion object {
            const val TAG = "BaseTouch"
    }

//    private var gestureDetector : GestureDetector? = null //这个api不准，view不能用layout或者params改变位置，改变位置后这个api会正负值抖动，应该是它一直更新view的最新位置。
    private var touchListenerList : MutableList<ITouchListener>? = null
    private var onTouchListener : View.OnTouchListener? = null
    private var isMoving = false
    private var touchStartX : Float = 0f
    private var touchStartY : Float = 0f
    private var touchCurrentX : Float = 0f
    private var touchCurrentY : Float = 0f

    public fun initListener() {
        initGesture()
        resetListenerList()
    }

    private fun initGesture() {

        onTouchListener = object : View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (v == null || event == null) {
                    return false
                }
                var action = event.action
                when(action) {
                    MotionEvent.ACTION_DOWN -> {
                        isMoving = false
                        touchStartX = event.rawX
                        touchStartY = event.rawY
                    }

                    MotionEvent.ACTION_MOVE -> {
                        isMoving = true
                        touchCurrentX = event.rawX
                        touchCurrentY = event.rawY
                        touchListenerList?.run {
                            forEach {
                                it.onScroll(event, touchCurrentX - touchStartX, touchCurrentY - touchStartY)
                            }
                        }
                        touchStartX = touchCurrentX
                        touchStartY = touchCurrentY
                    }

                    MotionEvent.ACTION_UP -> {
                        isMoving = false
                    }
                }
                return true
            }
        }

        targetView()?.run {
            this.setOnTouchListener(onTouchListener)
        }
    }

    private fun resetListenerList(){
        if (touchListenerList == null) {
            touchListenerList = mutableListOf()
        }
        touchListenerList?.clear()
    }

    public fun addListener(listener : ITouchListener) {
        touchListenerList?.run {
            if (this.contains(listener)){
                return
            }
            this.add(listener)
        }
    }

    public fun destory(){
        touchListenerList?.clear()
    }

    public abstract fun targetView() : View?
    public abstract fun destoryView()
}