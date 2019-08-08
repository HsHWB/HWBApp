package com.huehn.initword.core.utils.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;

import com.huehn.initword.core.utils.Log.LogManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HookManager {

    /**
     * 改变某些view的点击事件
     * onClickListener定义在View的ListenerInfo类中
     * @param view
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void hookViewOnClickListener(View view) throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (view == null){
            return;
        }
        // 第一步：反射得到传入的view对象的ListenerInfo 对象
        Class<?> clazz = Class.forName(View.class.getName());
        Method getListenerInfo = clazz.getDeclaredMethod("getListenerInfo");
        getListenerInfo.setAccessible(true);//setAccessible功能是启用或禁用安全检查 ，public的方法 Accessible仍为false
        Object listenerInfo = getListenerInfo.invoke(view);

        LogManager.d("huehn -----1-----");

        // 第二步：得到原始的 OnClickListener事件方法
        Class<?> listenerInfoClass = Class.forName("android.view.View$ListenerInfo");
        Field clickListener = listenerInfoClass.getDeclaredField("mOnClickListener");
        clickListener.setAccessible(true);
        View.OnClickListener originOnClickListener = (View.OnClickListener) clickListener.get(listenerInfo);//一旦获得一个Field引用，可以使用Field.get() 和 Field.set()方法获取和设置它的值
        LogManager.d("huehn -----2-----");

        // 第三步：用 Hook代理类 替换原始的 OnClickListener
        View.OnClickListener hookOnClickListener = new HookClickLinstener(originOnClickListener);
        clickListener.set(listenerInfo, hookOnClickListener);
        LogManager.d("huehn -----3-----");

    }

    /**
     * hook activity的onCreate方法
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static void hookOnCreateActivity(Activity activity) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {

        //拿到Activity的类名字对象，所有Activity new的时候其实就是new它。
        //这里没办法直接拿ActivityThread.class。因为ActivityThread是隐藏的，需要Class.forName。然后通过Activity去拿到ActivityThread的对象
        Class<?> activityClass = Activity.class;
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        //获取到ActivityThread
        Field activityThread = activityClass.getDeclaredField("mMainThread");
        activityThread.setAccessible(true);
        Object activityThread1 = activityThread.get(activity);

        Field field = activityThreadClass.getDeclaredField("mInstrumentation");
        //mInstrumentation是个private字段
        field.setAccessible(true);
        //根据activity内mInstrumentation字段 获取Instrumentation对象
        Instrumentation instrumentation = (Instrumentation) field.get(activityThread1);
        //创建修改Instrumentation对象
        HookActivityOnCreate hookActivityOnCreate = new HookActivityOnCreate(instrumentation);
        //把修改后的Instrumentation对象丢回给mInstrumentation字段，这个字段是从activityClass拿出来的，则给回activityClass。
        field.set(activityThread1, hookActivityOnCreate);
    }
}
