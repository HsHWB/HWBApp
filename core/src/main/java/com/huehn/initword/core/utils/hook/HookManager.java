package com.huehn.initword.core.utils.hook;

import android.view.View;

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

        // 第二步：得到原始的 OnClickListener事件方法
        Class<?> listenerInfoClass = Class.forName("android.view.View$ListenerInfo");
        Field clickListener = listenerInfoClass.getDeclaredField("mOnClickListener");
        clickListener.setAccessible(true);
        View.OnClickListener originOnClickListener = (View.OnClickListener) clickListener.get(getListenerInfo);
        // 第三步：用 Hook代理类 替换原始的 OnClickListener
        View.OnClickListener hookOnClickListener = new HookClickLinstener(originOnClickListener);
        clickListener.set(listenerInfo, hookOnClickListener);
    }

}
