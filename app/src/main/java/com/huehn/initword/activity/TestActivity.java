package com.huehn.initword.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;

import android.text.TextUtils;
import android.view.View;

import com.huehn.initword.R;
import com.huehn.initword.basecomponent.base.BaseActivity;
import com.huehn.initword.core.utils.Log.LogManager;
import com.huehn.initword.manager.design.SingleInstanceManager;
import com.huehn.initword.module.weex.TestInnerClass;

public class TestActivity extends Activity {

    private TestInnerClass  testInnerClass;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogManager.d("huehn TestActivity 1");
        setContentView(R.layout.weex_activity);
        //        SingleInstanceManager.getInstance(this);
        
        testInnerClass = new TestInnerClass();
        testInnerClass.run(this);
        testInnerClass.setActivity(this);
    }


    @Override
    public void finish() {
        super.finish();
        setResult(1);
    }
}
