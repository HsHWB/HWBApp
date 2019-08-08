package com.huehn.initword.core.utils.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.widget.Toast;

import com.huehn.initword.core.utils.Log.LogManager;
import com.nostra13.universalimageloader.utils.L;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HookActivityOnCreate extends Instrumentation {

    //ActivityThread中原始的对象，保存起来
    private Instrumentation originalInstrumentation;

    public HookActivityOnCreate(Instrumentation originalInstrumentation) {
        this.originalInstrumentation = originalInstrumentation;
    }

    public void callActivityOnCreate(Activity activity, Bundle icicle,
                                                     PersistableBundle persistentState){

        Toast.makeText(activity, "hello", Toast.LENGTH_SHORT).show();
        LogManager.d("huehn callActivityOnCreate1 onCreate activity name : " + activity.getClass().getName());

        try {
            Method callActivityMethod = Instrumentation.class.getDeclaredMethod("callActivityOnCreate", Activity.class, Bundle.class, PersistableBundle.class);
            callActivityMethod.setAccessible(true);
            callActivityMethod.invoke(originalInstrumentation, activity, icicle, persistentState);
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.d("huehn callActivityOnCreate1 onCreate e : " + e);
        }
    }

    public void callActivityOnCreate(Activity activity, Bundle icicle){

        Toast.makeText(activity, "hello", Toast.LENGTH_SHORT).show();
        LogManager.d("huehn callActivityOnCreate2 onCreate activity name : " + activity.getClass().getName());

        try {
            Method callActivityMethod = Instrumentation.class.getMethod("callActivityOnCreate", Activity.class, Bundle.class);
            callActivityMethod.setAccessible(true);
            callActivityMethod.invoke(originalInstrumentation, activity, icicle);
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.d("huehn callActivityOnCreate2 onCreate e : " + e);
        }
    }
}
