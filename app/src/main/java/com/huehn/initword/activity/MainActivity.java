package com.huehn.initword.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huehn.initword.R;
import com.huehn.initword.basecomponent.base.BaseActivity;
import com.huehn.initword.basecomponent.bean.permission.PermissionRequestCode;
import com.huehn.initword.basecomponent.bean.permission.PermissionResult;
import com.huehn.initword.core.login.FbLoginMgr;
import com.huehn.initword.core.module.IOnCallBack;
import com.huehn.initword.core.net.download.FileDownLoad;
import com.huehn.initword.core.net.download.HttpConfig;
import com.huehn.initword.core.net.response.security.ShangHaiPlateListResponse;
import com.huehn.initword.core.net.service.security.SecuritiesApi;
import com.huehn.initword.core.utils.Log.LogManager;
import com.huehn.initword.core.utils.hook.HookManager;
import com.huehn.initword.service.DoSomethingModule;
import com.huehn.initword.service.DoSomethingProxy;
import com.huehn.initword.service.IDoSomething;
import com.huehn.initword.ui.anim.module.AlphaAnim;
import com.huehn.initword.ui.anim.module.AnimationSetAnim;
import com.huehn.initword.ui.anim.module.TranslateAnim;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

import io.reactivex.functions.Consumer;
public class MainActivity extends BaseActivity {

    public final static String TAG = "MainActivity";
    private TextView textView;
    private TextView weexView;
    private TextView downFileView;
    private TextView facebookLogin;
    private ImageView imageView;

    public FbLoginMgr fbLoginMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        imageView = findViewById(R.id.imageview);
        weexView = findViewById(R.id.goto_weex);
        downFileView = findViewById(R.id.goto_downfile);
        facebookLogin = findViewById(R.id.goto_facebook_login);
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

        fbLoginMgr = new FbLoginMgr();
        fbLoginMgr.doInit();

        weexView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WeexActivity.class);
                MainActivity.this.startActivity(intent);
//                DoSomethingProxy doSomethingProxy = new DoSomethingProxy(new DoSomethingModule());
//                System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
//                IDoSomething iDoSomething = (IDoSomething) Proxy.newProxyInstance(IDoSomething.class.getClassLoader(),
//                        new Class[]{IDoSomething.class},
//                        doSomethingProxy);
//                iDoSomething.doSomething("hello");
            }
        });

        downFileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileDownLoad<ImageView> fileDownLoad = new FileDownLoad.Builder()
//                        .setUrl("https://hydl.huya.com/mobile/kiwi-android/7.0.1/yygamelive-7.0.1-25091-official.apk")
//                        .setUrl("https://apk-download.nimostatic.tv/anchor/apk/NimoTVforStreamer-1.2.9-490-web.apk")
                        .setUrl("https://github.com/git-for-windows/git/releases/download/v2.22.0.windows.1/Git-2.22.0-64-bit.exe")
                        .setOnSuccessListener(new IOnCallBack() {
                            @Override
                            public void accept(Object o)  {
                                LogManager.d(TAG, "downFile success");
                            }
                        })
                        .setOnErrorListener(new IOnCallBack<Throwable>() {
                            @Override
                            public void accept(Throwable throwable)  {
                                throwable.printStackTrace();
                            }
                        })
                        .build();
                fileDownLoad.startDownLoad(HttpConfig.HttpURLType.GET.getId(), "Git-2.22.0-64-bit.exe");

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

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TranslateAnim translateAnim = new TranslateAnim.Builder()
                        .setFromX(0)
                        .setFromY(0)
                        .setToX(100)
                        .setToY(100)
                        .setDuration(2000)
                        .setFillAfter(true)
                        .setInterpolator(new OvershootInterpolator())
                        .setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                LogManager.d(TAG, "animation start");
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                LogManager.d(TAG, "animation end");
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        })
                        .create();
//                translateAnim.onStart(imageView);

                AlphaAnim alphaAnim = new AlphaAnim.AlphaBuilder()
                        .setBeginAlpha(1.0f)
                        .setEndAlpha(0.2f)
                        .setDuration(2000)
                        .setFillAfter(true)
                        .setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                LogManager.d(TAG, "animation start");
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                LogManager.d(TAG, "animation end");
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        })
                        .create();

                AnimationSetAnim animationSetAnim = new AnimationSetAnim.AnimationSetBuilder()
                        .setShareInterpolator(true)
                        .setAnimations(translateAnim.getAnimation(), alphaAnim.getAnimation())
                        .create();
                animationSetAnim.onStart(imageView);
//                alphaAnim.onStart(imageView);
//                AnimUtils.normalAnimationSet(imageView, true,
//                        translateAnim.getAnimation(), alphaAnim.getAnimation());
            }
        });


        facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbLoginMgr.doLogin(MainActivity.this);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fbLoginMgr.onActivityResult(requestCode, resultCode, data);
    }
}
