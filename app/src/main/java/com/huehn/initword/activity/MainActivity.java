package com.huehn.initword.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huehn.initword.R;
import com.huehn.initword.basecomponent.base.BaseActivity;
import com.huehn.initword.basecomponent.bean.permission.PermissionRequestCode;
import com.huehn.initword.basecomponent.bean.permission.PermissionResult;
import com.huehn.initword.core.net.response.security.ShangHaiPlateListResponse;
import com.huehn.initword.core.net.service.security.SecuritiesApi;
import com.huehn.initword.core.utils.Log.LogManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import io.reactivex.functions.Consumer;
public class MainActivity extends BaseActivity {

    public final static String TAG = "MainActivity";
    private TextView textView;
    private TextView weexView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        imageView = findViewById(R.id.imageview);
        weexView = findViewById(R.id.goto_weex);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDisposable(SecuritiesApi.getShanghaiPlateList().subscribe(new Consumer<ShangHaiPlateListResponse>() {
                    @Override
                    public void accept(ShangHaiPlateListResponse shangHaiPlateListResponse) throws Exception {

                        if (shangHaiPlateListResponse == null || shangHaiPlateListResponse.getShowapi_res_body() == null ||
                                shangHaiPlateListResponse.getShowapi_res_body().getList() == null){
                            return;
                        }

                        LogManager.w(LogManager.CORE_EXCEPTION_LOG_FILE_LOCAL, TAG, shangHaiPlateListResponse);
                        LogManager.d(TAG, shangHaiPlateListResponse);
                        ImageLoader imageLoader = ImageLoader.getInstance();
                        imageLoader.displayImage("http://pic15.nipic.com/20110628/1369025_192645024000_2.jpg", imageView);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }));
            }
        });

        weexView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WeexActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        addDisposable(requestPermissions(PermissionRequestCode.WRITE_EXTERNAL_STORAGE, new String[]{PermissionRequestCode.WRITE_EXTERNAL_STORAGE_STRING})
            .subscribe(new Consumer<PermissionResult>() {
                @Override
                public void accept(PermissionResult permissionResult) throws Exception {
                    if (permissionResult != null && permissionResult.isGranted()){
                        ImageLoader imageLoader = ImageLoader.getInstance();
                        imageLoader.displayImage("http://pic15.nipic.com/20110628/1369025_192645024000_2.jpg", imageView);
                        LogManager.d("MainActivity", permissionResult.getPermissionString());
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    throwable.printStackTrace();
                }
            }));

    }

}
