package com.example.floatwindow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.floatwindow.touch.FloatWindowView;
import com.huehn.initword.core.utils.SystemUtils.ViewUtils;

public class MainActivity extends AppCompatActivity {

    private TextView floatText;
    private TextView clickText;
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
        clickText = findViewById(R.id.click_text);
        floatText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
                if (floatWindowView != null) {
                    floatWindowView.show(0, 0, ViewUtils.dip2px(MainActivity.this, 100),
                            ViewUtils.dip2px(MainActivity.this, 100));
                }
            }
        });
        clickText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData(){
        imageView = new ImageView(this);
        imageView.setBackground(getDrawable(R.mipmap.ic_launcher));
        //WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        //WindowManager.LayoutParams.FIRST_SUB_WINDOW
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        layoutParams.width = ViewUtils.dip2px(MainActivity.this, 100);
        layoutParams.height = ViewUtils.dip2px(MainActivity.this, 100);
        layoutParams.flags = layoutParams.flags
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        imageView.setLayoutParams(layoutParams);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "image", Toast.LENGTH_SHORT).show();
            }
        });
        getWindow().setBackgroundDrawableResource(R.color.color_ffccefff);
        floatWindowView = new FloatWindowView(getWindow(), imageView);

    }
}