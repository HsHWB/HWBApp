package com.huehn.initword.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.huehn.initword.R;
import com.huehn.initword.basecomponent.base.BaseActivity;
import com.huehn.initword.core.utils.Log.LogManager;

public class SuperClass extends BaseActivity {

    private TextView superText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_layout);
        initView();
        setListener();
    }

    private void initView(){
        superText = findViewById(R.id.text);
        LogManager.d("huehn override supper initView");
    }

    private void setListener(){
        LogManager.d("huehn override supper setListener");
        superText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogManager.d("huehn override super");
            }
        });
    }

}
