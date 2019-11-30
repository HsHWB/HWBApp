package com.huehn.initword.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.*
import com.huehn.initword.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*
import java.lang.RuntimeException
import java.util.*

var x = 0
val y = 0

class MainKotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setListener()
    }

    @ExperimentalUnsignedTypes
    private fun setListener(){
        button1.setOnClickListener{

            it.startAnimation(getMultAnim())
            printNum(1)
            printNumFromClass(2)
            println(printSum(1, 5))
            printVar()
            maxOf(1, Long.MAX_VALUE)
            println(castLong(100))
            println(castLong("100"))
            println(castLong("a"))
        }
    }

    private fun getMultAnim() : AnimationSet {
        var animSet : AnimationSet = AnimationSet(this@MainKotlinActivity, null)
        animSet.addAnimation(getScaleAnim())
        animSet.addAnimation(getAlphaAnim())
        return animSet
    }

    private fun getScaleAnim() : ScaleAnimation{
        var anim : ScaleAnimation = ScaleAnimation(0f, 0f, 0f, 100f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f)
        anim.duration = 500
        anim.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {
                println("huehn anim start")
            }

            override fun onAnimationEnd(animation: Animation?) {
                println("huehn anim end")
            }

            override fun onAnimationRepeat(animation: Animation?) {
                println("huehn anim repeat")
            }
        })
        return anim
    }

    private fun getAlphaAnim() : AlphaAnimation{
        var anim : AlphaAnimation = AlphaAnimation(1.0f, 0.2f)
        anim.duration = 5000
        return anim
    }

    private fun printNumFromClass(num : Int) : Int{
        println(num)
        return num
    }

    private fun printSum(a : Int, b : Int) = a + b

    @ExperimentalUnsignedTypes
    fun maxOf(a : Int, b : Long) : Int{
        if (a > b){
            return a
        }else{
            return b.toUInt().toInt()
        }
    }

    private fun castLong(a : Any) : Long ? {
        if (a is Long){
            return a.toLong()
        }
        try {
            return a as Long
        }catch (exception : RuntimeException){
//            exception.printStackTrace()
        }
        return null
    }

}

fun printNum(num : Int){
    println(num)
}

fun printNum2(num : Int){
    println(num)
}

fun printVar(){
    x = 1
    println("x = $x, y = $y")
}