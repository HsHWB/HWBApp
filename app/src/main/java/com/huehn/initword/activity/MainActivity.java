package com.huehn.initword.activity;

import android.Manifest;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.content.pm.ResolveInfo;

import android.util.Printer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huehn.initword.BuildConfig;
import com.huehn.initword.R;
import com.huehn.initword.basecomponent.base.BaseActivity;
import com.huehn.initword.basecomponent.bean.permission.PermissionRequestCode;
import com.huehn.initword.basecomponent.bean.permission.PermissionResult;
import com.huehn.initword.bean.PersonData;
import com.huehn.initword.bean.PersonDetail;
import com.huehn.initword.bean.SubTestData;
import com.huehn.initword.bean.TestData;
import com.huehn.initword.component.activity.ClipActivity;
import com.huehn.initword.component.activity.ClipImageActivity;
import com.huehn.initword.component.activity.clip.FileUtil;
import com.huehn.initword.core.app.App;
import com.huehn.initword.core.login.FbLoginMgr;
import com.huehn.initword.core.module.IOnCallBack;
import com.huehn.initword.core.net.download.FileDownLoad;
import com.huehn.initword.core.net.download.HttpConfig;
import com.huehn.initword.core.net.response.security.ShangHaiPlateListResponse;
import com.huehn.initword.core.net.service.security.SecuritiesApi;
import com.huehn.initword.core.utils.HookUtils;
import com.huehn.initword.core.utils.Log.LogManager;
import com.huehn.initword.core.utils.PluginLoader;
import com.huehn.initword.core.utils.hook.HookManager;
import com.huehn.initword.manager.design.SingleInstanceManager;
import com.huehn.initword.service.DoSomethingModule;
import com.huehn.initword.service.DoSomethingProxy;
import com.huehn.initword.service.IDoSomething;
import com.huehn.initword.ui.anim.module.AlphaAnim;
import com.huehn.initword.ui.anim.module.AnimationSetAnim;
import com.huehn.initword.ui.anim.module.TranslateAnim;
import com.huehn.initword.ui.dialog.BottomDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MainActivity extends BaseActivity {

    public final static String TAG = "MainActivity";
    @BindView(R.id.text)
    public TextView textView;
    @BindView(R.id.goto_weex)
    public TextView weexView;
    @BindView(R.id.plugin)
    public TextView pluginView;
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
    @BindView(R.id.goto_service_activity)
    public TextView serviceText;
    @BindView(R.id.goto_remote_activity)
    public TextView remoteText;
    @BindView(R.id.goto_drawelayout_activity)
    public TextView draweLayoutText;
    @BindView(R.id.goto_viewdrag_activity)
    public TextView viewdragText;
    @BindView(R.id.goto_kotlin)
    public TextView kotlinText;
    @BindView(R.id.goto_class_override)
    public TextView overrideClass;
    @BindView(R.id.imageview2)
    public ImageView headImg;
    @BindView(R.id.goto_rxjava)
    public TextView gotoRxjava;
    @BindView(R.id.goto_view)
    public TextView goToView;
    public FbLoginMgr fbLoginMgr;
    private File tempFile;
    private LinearLayout mainLayout;
    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    //请求访问外部存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("color", 0xf5ffa200);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mainLayout.setBackgroundColor(savedInstanceState.getInt("color"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainLayout = findViewById(R.id.main_layout);
        mainLayout.setBackgroundColor(0x6633B5E5);
        fbLoginMgr = new FbLoginMgr();
        fbLoginMgr.doInit();
        System.out.println(this);

//        Looper.getMainLooper().setMessageLogging(new Printer() {
//            @Override
//            public void println(String x) {
//                System.out.println("looper x : " + x);
//            }
//        });

        //        textView.setText();
//        imageView.setImageDrawable(getDrawable(R.drawable.br_add_facebook));
//        imageView.setRotationY(180);
//        textView.setBackground(getDrawable(R.drawable.br_add_facebook));
//        textView.getBackground().setAutoMirrored(true);
        //        addDisposable(requestPermissions(PermissionRequestCode.WRITE_EXTERNAL_STORAGE, new String[]{PermissionRequestCode.WRITE_EXTERNAL_STORAGE_STRING})
//            .subscribe(new Consumer<PermissionResult>() {
//                @Override
//                public void accept(PermissionResult permissionResult) throws Exception {
//                    if (permissionResult != null && permissionResult.isGranted()){
//                        ImageLoader imageLoader = ImageLoader.getInstance();
//                        imageLoader.displayImage("http://pic15.nipic.com/20110628/1369025_192645024000_2.jpg", imageView);
//                        LogManager.d("MainActivity", permissionResult.getPermissionString());
//                    }
//                }
//            }, new Consumer<Throwable>() {
//                @Override
//                public void accept(Throwable throwable) throws Exception {
//                    throwable.printStackTrace();
//                }
//            }));

        List<String> stringList = new ArrayList<>();
        List<List<String>> list = new ArrayList<>();
        getValue(list);
    }

    public <R, T extends R> void getValue(List<? extends List<T>> list){

    }

    @OnClick({R.id.text, R.id.goto_weex, R.id.goto_downfile, R.id.goto_facebook_login, R.id.imageview
        , R.id.goto_two_side_view, R.id.goto_corner_web_view, R.id.goto_remote_activity, R.id.goto_service_activity
        , R.id.goto_drawelayout_activity, R.id.goto_kotlin, R.id.goto_class_override, R.id.goto_rxjava, R.id.goto_view
        , R.id.plugin})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.text:
                mainLayout.setBackgroundColor(0x00000000);
                clickText();
//                int i = 0;
//                while (i < 10){
//                    try {
//                        System.out.println("循环卡顿中");
//                        Thread.sleep(2000);
//                        i++;
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
                break;
            case R.id.goto_rxjava:
                gotoRxJava();
                break;
            case R.id.goto_view:
                goToView();
                break;
            case R.id.plugin:
                goToPlugin();
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
            case R.id.goto_service_activity:
                goToServiceActivity();
                break;
            case R.id.goto_remote_activity:
                goToRemoteActivity();
                break;
            case R.id.goto_drawelayout_activity:
                goToDrawerActivity();
                break;
            case R.id.goto_viewdrag_activity:
                break;
            case R.id.goto_kotlin:
                goToKotlinActivity();
                break;
            case R.id.goto_class_override:
                goToOverrideClass();
                break;
        }
    }

    private void goToCornerWebView(){
        Intent intent = new Intent(MainActivity.this, CornerWebViewActivity.class);
        MainActivity.this.startActivity(intent);
    }

    private void goToPlugin(){
//        PluginLoader.INSTANCE.loadPlugin("small", this);
        try {
//            Class<?> tClass = Class.forName("com.example.small.utils.Constant");
//            Method constantsMethod = tClass.getMethod("getConstant", Context.class);
////            Class<?> tClass = Class.forName("com.example.smallproject2.MainActivity");
////            Method constantsMethod = tClass.getMethod("onCreate", null);
//            constantsMethod.setAccessible(true);
//            Object constant = tClass.newInstance();
//            constantsMethod.invoke(constant, this);
            PluginLoader.INSTANCE.loadPlugin("small", this);
            HookUtils.INSTANCE.hookStartActivityIntent();
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.example.smallproject2", "com.example.smallproject2.MainActivity"));
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToTwoSideSeekBar(){
        Intent intent = new Intent(MainActivity.this, TwoSideScrollActivity.class);
        MainActivity.this.startActivity(intent);
    }
    public static boolean isIntentUsable(Intent intent) {
        PackageManager pm = App.getApp().getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (activities != null && activities.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    private void clickText(){
        Intent intent = new Intent(MainActivity.this, TestActivity.class);
        MainActivity.this.startActivityForResult(intent, 1);
//        //权限判断
//        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            //申请WRITE_EXTERNAL_STORAGE权限
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
//        } else {
//            //跳转到调用系统相机
//            gotoCamera();
//        }
//        gotoCamera();

//        String storePath = Environment.getExternalStorageDirectory().toString();
//        String filePath = storePath + "/screenshot.png";
//        File file = new File(filePath);
//        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
//        shareIntent.setPackage("com.instagram.android");
//        shareIntent.setType("image/*");
//        Uri imageUri = FileProvider.getUriForFile(
//                MainActivity.this,
//                "com.huehn.initword.fileprovider", //(use your app signature + ".provider" )
//                file);
//        Bitmap bitmap2 = BitmapFactory.decodeFile(imageUri.toString());
////        shareIntent.setDataAndType(imageUri, "image/*");
//        shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
////        shareIntent.putExtra(Intent.EXTRA_TEXT, content + "\n" + linkUrl);
//        if (isIntentUsable(shareIntent)) {
//            this.startActivity(shareIntent);
//            //不点击复制链接或者更多的时候，发送分享事件
//        } else {
//        }
//        addDisposable(SecuritiesApi.getShanghaiPlateList().subscribe(new Consumer<ShangHaiPlateListResponse>() {
//            @Override
//            public void accept(ShangHaiPlateListResponse shangHaiPlateListResponse) throws Exception {
//
//                if (shangHaiPlateListResponse == null || shangHaiPlateListResponse.getShowapi_res_body() == null ||
//                        shangHaiPlateListResponse.getShowapi_res_body().getList() == null){
//                    return;
//                }
//
//                LogManager.w(LogManager.CORE_EXCEPTION_LOG_FILE_LOCAL, TAG, shangHaiPlateListResponse);
//                LogManager.d(TAG, shangHaiPlateListResponse);
//                ImageLoader imageLoader = ImageLoader.getInstance();
//                imageLoader.displayImage("http://pic15.nipic.com/20110628/1369025_192645024000_2.jpg", imageView);
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                throwable.printStackTrace();
//            }
//        }));
    }

    private void gotoRxJava(){
        Intent intent = new Intent(MainActivity.this, RxJavaTestActivity.class);
        MainActivity.this.startActivity(intent);
        Observable.create((new ObservableOnSubscribe<PersonData>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<PersonData> emitter) throws Exception {

            }
        })).map(new Function<PersonData, PersonDetail>() {
            @Override
            public PersonDetail apply(@NonNull PersonData personData) throws Exception {
                return null;
            }
        }).subscribe(new Consumer<PersonDetail>() {
            @Override
            public void accept(PersonDetail personDetail) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }

    private void goToView(){
        Intent intent = new Intent(MainActivity.this, ScrollingActivity.class);
        MainActivity.this.startActivity(intent);
    }

    private void goToWeex(){
        Intent intent = new Intent(MainActivity.this, MoveViewActivity.class);
        MainActivity.this.startActivity(intent);
//        BottomDialog bottomDialog = new BottomDialog();
//        bottomDialog.show(getSupportFragmentManager(), BottomDialog.class.getSimpleName());
//        Intent intent = new Intent(MainActivity.this, WeexActivity.class);
//        MainActivity.this.startActivity(intent);
//        DoSomethingProxy doSomethingProxy = new DoSomethingProxy(new DoSomethingModule());
//        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
//        IDoSomething iDoSomething = (IDoSomething) Proxy.newProxyInstance(IDoSomething.class.getClassLoader(),
//                new Class[]{IDoSomething.class},
//                doSomethingProxy);
//        iDoSomething.doSomething("hello");
//
//        TestData testData = new TestData("test", "27");
//        try {
//            Field field = TestData.class.getDeclaredField("subTestData");
//            field.setAccessible(true);
//            SubTestData subTestData = (SubTestData) field.get(testData);
//            LogManager.d("huehn name : " + subTestData.getName());
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
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

    private void goToServiceActivity(){
        Intent intent = new Intent(MainActivity.this, ServiceActivity.class);
        MainActivity.this.startActivity(intent);
    }

    private void goToRemoteActivity(){
        Intent intent = new Intent(MainActivity.this, RemoteProcessActivity.class);
        MainActivity.this.startActivity(intent);
    }

    private void goToDrawerActivity(){
        Intent intent = new Intent(MainActivity.this, DrawerViewPagerActivity.class);
        MainActivity.this.startActivity(intent);
    }

    private void goToKotlinActivity(){
        Intent intent = new Intent(MainActivity.this, MainKotlinActivity.class);
        MainActivity.this.startActivity(intent);
    }

    private void goToOverrideClass(){
//        Intent intent = new Intent(MainActivity.this, ClipActivity.class);
//        MainActivity.this.startActivity(intent);
//        gotoCamera();
        if (SingleInstanceManager.getInstance() != null) {
            LogManager.d(SingleInstanceManager.getInstance().getContext());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fbLoginMgr.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {
            case REQUEST_CAPTURE: //调用系统相机返回
                if (resultCode == RESULT_OK) {
                    gotoClipActivity(Uri.fromFile(tempFile));
                }
                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    gotoClipActivity(uri);
                }
                break;
            case REQUEST_CROP_PHOTO:  //剪切图片返回
                if (resultCode == RESULT_OK) {
                    final Uri uri = intent.getData();
                    if (uri == null) {
                        return;
                    }
                    String cropImagePath = FileUtil.getRealFilePathFromUri(getApplicationContext(), uri);
                    Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                    imageView.setImageBitmap(bitMap);
//                    if (type == 1) {
//                        headImage1.setImageBitmap(bitMap);
//                    } else {
//                        headImage2.setImageBitmap(bitMap);
//                    }
                    //此处后面可以将bitMap转为二进制上传后台网络
                    //......

                }
                break;

            case 1:
//                imageView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        System.gc();
//                    }
//                }, 2000);
                break;
        }

    }
    /**
     * 打开截图界面
     */
    public void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, ClipImageActivity.class);
//        intent.putExtra("type", type);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CROP_PHOTO);
    }

    /**
     * 外部存储权限申请返回
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoCamera();
            }
        } else if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
//                gotoPhoto();
            }
        }
    }

    /**
     * 跳转到照相机
     */
    private void gotoCamera() {
        Log.d("evan", "*****************打开相机********************");
        try {
            //创建拍照存储的图片文件
            tempFile = new File(FileUtil.checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"), System.currentTimeMillis() + ".jpg");

            //跳转到调用系统相机
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //设置7.0中共享文件，分享路径定义在xml/file_paths.xml
                intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".fileProvider", tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
            }
            startActivityForResult(intent, REQUEST_CAPTURE);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void finish() {
        fbLoginMgr.logout();
        super.finish();
    }
}
