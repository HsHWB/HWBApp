package com.huehn.initword.basecomponent.base;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.SparseArray;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.huehn.initword.basecomponent.bean.permission.PermissionResult;
import com.huehn.initword.core.utils.Exception.AppException;
import com.huehn.initword.core.utils.Rxjava.RxJavaUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.HashSet;

import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public abstract class BaseActivity extends AppCompatActivity {

    protected CompositeDisposable compositeDisposable;
    /**
     * 满足下面两个条件我们可以使用SparseArray代替HashMap：
     *
     * 数据量不大，最好在千级以内
     * key必须为int类型，这中情况下的HashMap可以用SparseArray代替：
     */
    private SparseArray<PermissionObservable> requestPermissionCodeArray;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState,
                         @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    /**
     * 给网络请求添加到队列里面，方便销毁时统一去除监听
     * @param disposable
     */
    public void addDisposable(Disposable disposable){
        if (compositeDisposable == null){
            compositeDisposable = new CompositeDisposable();
        }

        if (RxJavaUtils.isUnDisposed(disposable)){
            compositeDisposable = new CompositeDisposable();
        }

        if (disposable != null && !disposable.isDisposed()){
            compositeDisposable.add(disposable);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /**
     * 申请权限
     * @param requestCode
     * @param permissionId
     */
    protected Observable<PermissionResult> requestPermissions(final int requestCode, final String[] permissionId){
        if (permissionId == null || permissionId.length == 0){
            throw AppException.permissionIdNull();
        }
        if (requestPermissionCodeArray == null){
            requestPermissionCodeArray = new SparseArray<>();
        }
        if (requestPermissionCodeArray.indexOfKey(requestCode) >= 0){
            throw AppException.permissionIdHasExits();
        }
        final PermissionObservable permissionObservable = new PermissionObservable();
        Observable<PermissionResult> observable = Observable.create(new ObservableOnSubscribe<PermissionResult>() {
            @Override
            public void subscribe(ObservableEmitter<PermissionResult> emitter) throws Exception {
                ActivityCompat.requestPermissions(BaseActivity.this, permissionId, requestCode);
                permissionObservable.setObservableEmitter(emitter);
            }
        });

        permissionObservable.setObservable(observable);
        requestPermissionCodeArray.put(requestCode, permissionObservable);
        return observable;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestPermissionCodeArray != null
                && requestPermissionCodeArray.indexOfKey(requestCode) >= 0
                && requestPermissionCodeArray.get(requestCode) != null){
            PermissionObservable permissionObservable = requestPermissionCodeArray.get(requestCode);
            PermissionResult permissionResult = new PermissionResult();
            permissionResult.setRequestCode(requestCode);
            permissionResult.setPermissionString(permissions);
            permissionResult.setShouldShowRequestPermissionRationale(false);
            permissionResult.setGranted(true);
            for (int i = 0; i < grantResults.length; i++){
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                    permissionResult.setGranted(false);
                    break;
                }
            }
            if (permissionObservable.getObservableEmitter() != null) {
                permissionObservable.getObservableEmitter().onNext(permissionResult);
            }
            if (permissionObservable.getObservableEmitter() != null) {
                permissionObservable.getObservableEmitter().onComplete();
            }
        }
    }


    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onDestroy() {
        RxJavaUtils.close(compositeDisposable);
        if (compositeDisposable != null){
            compositeDisposable.clear();
            compositeDisposable = null;
        }
        super.onDestroy();
    }
}
