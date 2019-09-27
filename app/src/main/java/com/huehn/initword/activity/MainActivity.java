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
import com.huehn.initword.bean.SubTestData;
import com.huehn.initword.bean.TestData;
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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
public class MainActivity extends BaseActivity {

    public final static String TAG = "MainActivity";
    @BindView(R.id.text)
    public TextView textView;
    @BindView(R.id.goto_weex)
    public TextView weexView;
    @BindView(R.id.goto_downfile)
    public TextView downFileView;
    @BindView(R.id.goto_facebook_login)
    public TextView facebookLogin;
    @BindView(R.id.imageview)
    public ImageView imageView;
    @BindView(R.id.goto_two_side_view)
    public TextView twoSideSeekBar;
    @BindView(R.id.goto_corner_web_view)
    public TextView cornerWebView;
    @BindView(R.id.goto_remote_activity)
    public TextView remoteText;
    public FbLoginMgr fbLoginMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fbLoginMgr = new FbLoginMgr();
        fbLoginMgr.doInit();


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

    @OnClick({R.id.text, R.id.goto_weex, R.id.goto_downfile, R.id.goto_facebook_login, R.id.imageview
        , R.id.goto_two_side_view, R.id.goto_corner_web_view, R.id.goto_remote_activity})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.text:
                clickText();
                break;
            case R.id.goto_weex:
                goToWeex();
                break;
            case R.id.goto_downfile:
                goToDownFile();
                break;
            case R.id.goto_facebook_login:
                goToFacebookLogin();
                break;
            case R.id.imageview:
                clickImage();
                break;
            case R.id.goto_two_side_view:
                goToTwoSideSeekBar();
                break;
            case R.id.goto_corner_web_view:
                goToCornerWebView();
                break;
            case R.id.goto_remote_activity:
                goToRemoteActivity();
                break;
        }
    }

    private void goToCornerWebView(){
        Intent intent = new Intent(MainActivity.this, CornerWebViewActivity.class);
        MainActivity.this.startActivity(intent);
    }

    private void goToTwoSideSeekBar(){
        Intent intent = new Intent(MainActivity.this, TwoSideScrollActivity.class);
        MainActivity.this.startActivity(intent);
    }

    private void clickText(){
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

    private void goToWeex(){
        Intent intent = new Intent(MainActivity.this, WeexActivity.class);
        MainActivity.this.startActivity(intent);
        DoSomethingProxy doSomethingProxy = new DoSomethingProxy(new DoSomethingModule());
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        IDoSomething iDoSomething = (IDoSomething) Proxy.newProxyInstance(IDoSomething.class.getClassLoader(),
                new Class[]{IDoSomething.class},
                doSomethingProxy);
        iDoSomething.doSomething("hello");

        TestData testData = new TestData("test", "27");
        try {
            Field field = TestData.class.getDeclaredField("subTestData");
            field.setAccessible(true);
            SubTestData subTestData = (SubTestData) field.get(testData);
            LogManager.d("huehn name : " + subTestData.getName());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void goToDownFile(){
        //https://dl.google.com/dl/android/studio/install/3.5.0.21/android-studio-ide-191.5791312-windows.exe
        FileDownLoad<ImageView> fileDownLoad = new FileDownLoad.Builder()
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

    private void goToFacebookLogin(){
        fbLoginMgr.doLogin(MainActivity.this);
    }

    private void clickImage(){
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

    private void goToRemoteActivity(){
        Intent intent = new Intent(MainActivity.this, RemoteProcessActivity.class);
        MainActivity.this.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fbLoginMgr.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void finish() {
        fbLoginMgr.logout();
        super.finish();
    }
}
