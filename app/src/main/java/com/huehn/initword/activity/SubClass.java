package com.huehn.initword.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.huehn.initword.R;
import com.huehn.initword.basecomponent.base.BaseActivity;
import com.huehn.initword.core.utils.Log.LogManager;

public class SubClass extends SuperClass {

    private TextView subText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_layout);
        initView();
        setListener();
    }

    private void initView(){
        subText = findViewById(R.id.sub_text);
        LogManager.d("huehn override sub initView");
    }

    private void setListener(){
        LogManager.d("huehn override sub setListener");
        subText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogManager.d("huehn override sub");
            }
        });
    }

}
