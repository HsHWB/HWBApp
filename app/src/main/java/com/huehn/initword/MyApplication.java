package com.huehn.initword;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
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
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
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
        initImageLoader();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    private void initImageLoader(){
        int screenWidth = ViewUtils.getScreenWidth();
        int screenHeigth = ViewUtils.getScreenHeight();
        //获取缓存文件，如果有sd卡或者外置储存，并且有权限的话，优先把路径建立在sd卡或者外置储存。否则建立在内置储存
//        File cacheDir = StorageUtils.getCacheDirectory(this);
        //自定义缓存目录，目录建立的路径同上面
        File cacheDir = getOwnCacheDirectory(this, "myimage/Cache");
        LogManager.d("MyApplication", cacheDir.getAbsolutePath());
        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(screenWidth, screenHeigth)
                .diskCacheExtraOptions(screenWidth, screenHeigth, null)
                .threadPoolSize(10)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LRULimitedMemoryCache(2 * 1024 * 1024))//自定义内存的缓存策略
                .memoryCacheSizePercentage(13)// default
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .diskCacheFileCount(20)//缓存的文件数量
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())// default
                .imageDownloader(new BaseImageDownloader(this))// default
                .imageDecoder(new BaseImageDecoder(true))// default
                .defaultDisplayImageOptions(getDisplayOptions())// default
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(imageLoaderConfiguration);
    }
    private DisplayImageOptions getDisplayOptions() {
        DisplayImageOptions options;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher_background)// 设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_launcher_background)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_launcher_background) // 设置图片加载/解码过程中错误时候显示的图片

                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中

                .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
                // .delayBeforeLoading(int delayInMillis)//int
                // delayInMillis为你设置的下载前的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // .preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                .displayer(new RoundedBitmapDisplayer(20))// 是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))// 是否图片加载好后渐入的动画时间
                .build();// 构建完成
        return options;
    }

    public static File getOwnCacheDirectory(Context context, String cacheDir) {
        File appCacheDir = null;
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {
            appCacheDir = new File(Environment.getExternalStorageDirectory(), cacheDir);
        }
        if (appCacheDir == null || (!appCacheDir.exists() && !appCacheDir.mkdirs())) {
            appCacheDir = context.getCacheDir();
        }
        return appCacheDir;
    }
    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";
    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }
}
