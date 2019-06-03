package com.huehn.initword.component.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huehn.initword.R;
import com.huehn.initword.basecomponent.base.BaseActivity;
import com.huehn.initword.core.net.response.security.ShangHaiPlateListResponse;
import com.huehn.initword.core.net.service.security.SecuritiesApi;
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

import java.io.File;

import io.reactivex.functions.Consumer;

import static android.os.Environment.MEDIA_MOUNTED;

public class MainActivity extends BaseActivity {

    public final static String TAG = "MainActivity";
    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        imageView = findViewById(R.id.imageview);

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
                        initImageLoader();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }));
            }
        });

<<<<<<< HEAD
        Glide.with()

=======
//        ImageLoader imageLoader = ImageLoader.getInstance();
//        imageLoader.displayImage("http://pic15.nipic.com/20110628/1369025_192645024000_2.jpg", imageView);
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
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())// default
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(imageLoaderConfiguration);
>>>>>>> 43f9218e7d479fbd376071a01e5e36ff271f3f65
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
