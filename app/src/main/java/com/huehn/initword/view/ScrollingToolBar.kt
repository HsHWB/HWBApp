package com.huehn.initword.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.Toolbar
import com.huehn.initword.R

class ScrollingToolBar: Toolbar {
    private lateinit var mContext: Context

    constructor(context: Context): this(context, null)

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context) {
        this.mContext = context
        initView()
    }

    fun initView(){
        checkContext()?.run {
            var imageView1: ImageView = ImageView(this)
            var imageView2: ImageView = ImageView(this)
            var imageView3: ImageView = ImageView(this)
            var imageView4: ImageView = ImageView(this)
            var imageView5: ImageView = ImageView(this)
            this@ScrollingToolBar.addView(imageView1)
            this@ScrollingToolBar.addView(imageView2)
            this@ScrollingToolBar.addView(imageView3)
            this@ScrollingToolBar.addView(imageView4)
            this@ScrollingToolBar.addView(imageView5)
            imageView1.background = this.getDrawable(R.drawable.br_anchor_attestation)
            imageView2.background = this.getDrawable(R.drawable.br_back_press)
            imageView3.background = this.getDrawable(R.drawable.br_dialog_close_icon)
            imageView4.background = this.getDrawable(R.drawable.br_dialog_two_btn_close)
            imageView5.background = this.getDrawable(R.drawable.br_finish_subscriber)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
//        for (i in 0..this.childCount) {
//            this.getChildAt(i)?.let {
//
//            }
//        }
        super.onLayout(changed, l, t, r, b)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    fun checkContext(): Context? {
        takeIf { this::mContext.isInitialized }?.run {
            return mContext
        }?: kotlin.run {
            if (this.context != null) {
                return this.context
            }
        }
        return null
    }

}
