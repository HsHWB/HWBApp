package com.huehn.initword;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Environment;

import com.huehn.initword.core.app.App;
import com.huehn.initword.core.app.BaseConstant;
import com.huehn.initword.core.utils.Log.LogManager;
import com.huehn.initword.core.utils.SystemUtils.ViewUtils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.umeng.commonsdk.UMConfigure;

import java.io.File;

import static android.os.Environment.MEDIA_MOUNTED;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        App.getInstance().setApp(this);
        /*
            注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，也需要在App代码中调用初始化接口
            （如需要使用AndroidManifest.xml中配置好的appkey和channel值，UMConfigure.init调用中appkey和channel参数请置为null）。
        */
        UMConfigure.init(this, BaseConstant.UMENG_APPKEY, "HWBAPP_CHANNEL", UMConfigure.DEVICE_TYPE_PHONE, null);
        if (BuildConfig.DEBUG){
            UMConfigure.setLogEnabled(true);
        }else {
            UMConfigure.setLogEnabled(false);
        }
//        initImageLoader();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

//    private void initImageLoader(){
//        int screenWidth = ViewUtils.getScreenWidth();
//        int screenHeigth = ViewUtils.getScreenHeight();
//        //获取缓存文件，如果有sd卡或者外置储存，并且有权限的话，优先把路径建立在sd卡或者外置储存。否则建立在内置储存
////        File cacheDir = StorageUtils.getCacheDirectory(this);
//        //自定义缓存目录，目录建立的路径同上面
//        File cacheDir = getOwnCacheDirectory(this, "myimage/Cache");
//        LogManager.d("MyApplication", cacheDir.getAbsolutePath());
//        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(this)
//            .memoryCacheExtraOptions(screenWidth, screenHeigth)
//            .diskCacheExtraOptions(screenWidth, screenHeigth, null)
//            .threadPoolSize(10)//线程池内加载的数量
//            .threadPriority(Thread.NORM_PRIORITY - 2)
//            .tasksProcessingOrder(QueueProcessingType.FIFO)
//            .denyCacheImageMultipleSizesInMemory()
//            .memoryCache(new LRULimitedMemoryCache(2 * 1024 * 1024))//自定义内存的缓存策略
//            .memoryCacheSizePercentage(13)// default
//            .diskCache(new UnlimitedDiskCache(cacheDir))
//            .diskCacheFileCount(20)//缓存的文件数量
//            .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())// default
//            .imageDownloader(new BaseImageDownloader(this))// default
//            .imageDecoder(new BaseImageDecoder(true))// default
//            .defaultDisplayImageOptions(DisplayImageOptions.createSimple())// default
//            .writeDebugLogs()
//            .build();
//        ImageLoader.getInstance().init(imageLoaderConfiguration);
//    }
//
//    public static File getOwnCacheDirectory(Context context, String cacheDir) {
//        File appCacheDir = null;
//        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {
//            appCacheDir = new File(Environment.getExternalStorageDirectory(), cacheDir);
//        }
//        if (appCacheDir == null || (!appCacheDir.exists() && !appCacheDir.mkdirs())) {
//            appCacheDir = context.getCacheDir();
//        }
//        return appCacheDir;
//    }
//    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";
//    private static boolean hasExternalStoragePermission(Context context) {
//        int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
//        return perm == PackageManager.PERMISSION_GRANTED;
//    }
}
