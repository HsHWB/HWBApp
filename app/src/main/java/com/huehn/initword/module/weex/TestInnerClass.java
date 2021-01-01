package com.huehn.initword.module.weex;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.huehn.initword.bean.TestData;
import com.huehn.initword.core.app.App;
import com.huehn.initword.core.utils.Log.LogManager;
import com.huehn.initword.manager.design.SingleInstanceManager;

public class TestInnerClass {

    private int a = 1;

    private TestData testData;

    private Activity activity;
    private Thread thread;
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    private int getA() {
        return a;
    }

    public void run(Context context){
        InnerClass innerClass = new InnerClass();
//        SingleInstanceManager.getInstance().setInnerClass(innerClass);
//                innerClass.run();
        Thread thread = new Thread(innerClass);
        thread.start();
//                run2();
        TextView textView = new TextView(App.getApp());
        final int[] b = {1};
        int c = 1;
        TestData testData = new TestData("a", "1");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b[0]++;
                LogManager.d(c);
                LogManager.d(testData.getAge());
                testData.setAge("2");
            }
        });
    }

//    public void run2(){
//        class InnerClass2 implements Runnable{
//            @Override
//            public void run() {
//
//            }
//        }
//
//        Thread thread = new Thread(){
//            @Override
//            public void run() {
//            }
//        };
//    }
//
    public class InnerClass implements Runnable{

        private Context context;

        public InnerClass() {
        }

        public InnerClass(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            System.out.println(getA());
            while (true) {
                try {
                    Thread.sleep(2000);
                    LogManager.d("huehn a : " + (a++));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
     }

//     public static class InnerClassStatic{
//         public InnerClassStatic() {
//         }
//     }

}
