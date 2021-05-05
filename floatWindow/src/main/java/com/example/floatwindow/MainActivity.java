package com.example.floatwindow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.floatwindow.touch.FloatWindowView;
import com.huehn.initword.core.utils.SystemUtils.ViewUtils;

public class MainActivity extends AppCompatActivity {

    private TextView floatText;
    private FloatWindowView floatWindowView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView(){
        floatText = findViewById(R.id.float_text);
        floatText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (floatWindowView != null) {
                    floatWindowView.show(0, 0, ViewUtils.dip2px(MainActivity.this, 100),
                            ViewUtils.dip2px(MainActivity.this, 100));
                }
            }
        });
    }

    private void initData(){
        imageView = new ImageView(this);
        imageView.setBackground(getDrawable(R.mipmap.ic_launcher));
//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewUtils.dip2px(MainActivity.this, 100), ViewUtils.dip2px(MainActivity.this, 100));

        floatWindowView = new FloatWindowView(getWindow(), imageView);

    }
}