package com.huehn.initword.core.utils

import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.support.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.huehn.initword.core.app.App
import com.huehn.initword.core.utils.Log.LogManager
import java.lang.reflect.Field
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

object HookUtils {

    //一定要给读取sd卡dex的权限
    // 1、hook intent，将插件的intent包名和类名，转成宿主工程定义的代理类
    // 2、绕过了AMS检查后，在ActivityRecord中，再次hook intent，把第1步替换的intent给换回来，去启动插件的activity

    const val ORIGIN_INTENT = "intent"

    fun hookStartActivityIntent() {
        runCatching {
            //首先hook一般hook单例或者静态变量，因为这样子比较容易拿到对象，就能拿到对象里面的值
            var activityManagerClazz : Class<*> = Class.forName("android.app.ActivityManager")
            //获取ActivityManager中的单例Sington的Field
            var iActivityManagerSingletonField : Field = activityManagerClazz.getDeclaredField("IActivityManagerSingleton")
            iActivityManagerSingletonField.isAccessible = true
            // 获取Sington对象
            var activityMangerSingletonObject : Any = iActivityManagerSingletonField.get(null)

            //获取sington对象里面对应的ActivityManager对象
            var singletonClazz : Class<*> = Class.forName("android.util.Singleton")
            var mInstanceField : Field = singletonClazz.getDeclaredField("mInstance")
            mInstanceField.isAccessible = true
            var activityManagerObject = mInstanceField.get(activityMangerSingletonObject)

            LogManager.d("huehn hookStartActivityIntent 1")

            //动态代理这个接口，然后将这个接口换到activityManagerObject的instance里面去
            var iActivityManager : Class<*> = Class.forName("android.app.IActivityManager")
            var iActivityManagerProxy = Proxy.newProxyInstance(Thread.currentThread().contextClassLoader,
                    arrayOf(iActivityManager), HookInvocationHandler(activityManagerObject))

            mInstanceField.set(activityMangerSingletonObject, iActivityManagerProxy)
            LogManager.d("huehn hookStartActivityIntent 2")
            HookMessageIntent();
        }.onFailure {
            it.printStackTrace()
        }

    }


    //android 9.0 ，截取ActivityThread的159 handler消息，截取的话，通过Handler的dispatchMessage源码能够知道， 只要callback不为空，就能够截取msg，然后我这里处理完intent，return false，让它继续执行下去就好。
    fun HookMessageIntent(){

        //获取ActivityThread的Handler
        var activityThreadClazz : Class<*> = Class.forName("android.app.ActivityThread")
        var sCurrentActivityThreadField : Field = activityThreadClazz.getDeclaredField("sCurrentActivityThread")
        var mField : Field = activityThreadClazz.getDeclaredField("mH")
        mField.isAccessible = true
        sCurrentActivityThreadField.isAccessible = true
        var activityThreadObject = sCurrentActivityThreadField.get(null)
        var handler = mField.get(activityThreadObject) as Handler

        var handlerClazz : Class<*> = Class.forName("android.os.Handler")
        var callBackField : Field = handlerClazz.getDeclaredField("mCallback")
        callBackField.isAccessible = true
        var launchActivityItemClazz: Class<*> = Class.forName("android.app.servertransaction.LaunchActivityItem")
        var intentField = launchActivityItemClazz.getDeclaredField("mIntent")
        intentField.isAccessible = true

        var clientTransactionClazz : Class<*> = Class.forName("android.app.servertransaction.ClientTransaction")
        var mActivityCallbacksField : Field = clientTransactionClazz.getDeclaredField("mActivityCallbacks")
        mActivityCallbacksField.isAccessible = true

        var callback : Handler.Callback = Handler.Callback {
            when(it.what) {
                159 -> it.run {

                    takeIf { this.obj.javaClass.name == "android.app.servertransaction.ClientTransaction" }?.let {
                        var clientTransactionObject = it.obj
                        var mActivityCallbacksObject : List<*> = mActivityCallbacksField.get(clientTransactionObject) as List<*>
                        LogManager.d("huehn callback name 1: ${it.javaClass.name}")
                        mActivityCallbacksObject?.forEach {
                            it?.takeIf {
                                LogManager.d("huehn callback name 2: ${it.javaClass.name}")
                                it.javaClass.name == "android.app.servertransaction.LaunchActivityItem"
                            }?.run {
                                var intent = intentField.get(it) as Intent
                                LogManager.d("huehn callback packageName : ${intent.component?.packageName}" + "     className : ${intent.component?.className}")
                                if (intent.component?.packageName == "com.huehn.initword"
                                        && intent.component?.className == "com.huehn.initword.activity.ProxyActivity") {
                                    LogManager.d("huehn callback 3")
                                    var originIntent = intent.getParcelableExtra<Intent>(ORIGIN_INTENT)
                                    intentField.set(it, originIntent)
                                }
                            }
                        }
                    }
                    false
                }
                else -> { false }
            }
        }

        callBackField.set(handler, callback)
        mField.set(activityThreadObject, handler)

    }

    class HookInvocationHandler(var iActivityManager: Any) : InvocationHandler {
        override fun invoke(proxy: Any?, method: Method, args: Array<Any>?): Any? {
            LogManager.d("huehn hookStartActivityIntent method : ${method?.name}")
            if (method.name == "startActivity") {
                    var index = 0
                    args?.forEach {
                        it.takeIf {
                            it is Intent && it?.component?.packageName == "com.example.smallproject2"
                        }?.let {
                            LogManager.d("huehn hookStartActivityIntent method 2 : ${method?.name}")
                            var newIntent = Intent()
                            newIntent.setComponent(ComponentName("com.huehn.initword.activity",
                                "com.huehn.initword.activity.ProxyActivity"))
                            newIntent.setClass(App.getApp(), Class.forName("com.huehn.initword.activity.ProxyActivity"))
                            newIntent.putExtra(ORIGIN_INTENT, it as Intent)
                            //把新的intent换上去
                            args[index] = newIntent
                        }
                        index++
                    }

                }
            var res = method!!.invoke(iActivityManager, *(args ?: emptyArray()))
            return res
        }

    }

}