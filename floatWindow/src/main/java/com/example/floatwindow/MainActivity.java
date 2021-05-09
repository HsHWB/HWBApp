package com.example.floatwindow;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.floatwindow.touch.FloatWindowView;
import com.huehn.initword.basecomponent.base.BaseActivity;
import com.huehn.initword.basecomponent.bean.permission.PermissionResult;
import com.huehn.initword.core.utils.Log.LogManager;
import com.huehn.initword.core.utils.SystemUtils.ViewUtils;

import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity {

    private ViewGroup rootView;
    private TextView floatText;
    private TextView floatAppText;
    private TextView clickText;
    private FloatWindowView floatWindowView;
    private ImageView imageView;

    public static final int REQUEST_APP_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView(){
        rootView = findViewById(R.id.root_view);
        floatText = findViewById(R.id.float_text);
        floatAppText = findViewById(R.id.float_app_text);
        clickText = findViewById(R.id.click_text);
        floatText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFloatInApp();
                if (floatWindowView != null) {
                    floatWindowView.show(0, 0, ViewUtils.dip2px(MainActivity.this, 100),
                            ViewUtils.dip2px(MainActivity.this, 100));
                }
            }
        });

        floatAppText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFloatOtherApp();
                checkPermission();
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
    }

    /**
     * 应用内悬浮窗
     */
    private void initFloatInApp(){
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewUtils.dip2px(MainActivity.this, 100),
                ViewUtils.dip2px(MainActivity.this, 100));
        imageView.setLayoutParams(layoutParams);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "image", Toast.LENGTH_SHORT).show();
            }
        });
        getWindow().setBackgroundDrawableResource(R.color.color_ffccefff);
        floatWindowView = new FloatWindowView(rootView, imageView, false);
    }

    private void checkPermission(){
        requestPermissions(REQUEST_APP_REQUEST, new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW});
    }

    private void showFloat(){
        if (floatWindowView != null) {
            floatWindowView.show(0, 0, ViewUtils.dip2px(MainActivity.this, 100),
                    ViewUtils.dip2px(MainActivity.this, 100));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LogManager.d("MainActivity", "huehn MainActivity MainActivity onRequestPermissionsResult : request : " + requestCode +
                "     result" + (grantResults[0] == PackageManager.PERMISSION_GRANTED));
        if (requestCode == REQUEST_APP_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showFloat();
        }
    }

    /**
     * 应用外悬浮窗
     */
    private void initFloatOtherApp(){
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
        floatWindowView = new FloatWindowView(getWindow(), imageView, true);
    }

}