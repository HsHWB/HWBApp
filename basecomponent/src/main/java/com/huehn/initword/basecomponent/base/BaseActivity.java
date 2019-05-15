package com.huehn.initword.basecomponent.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.huehn.initword.core.utils.RxJavaUtils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class BaseActivity extends AppCompatActivity {

    protected CompositeDisposable compositeDisposable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
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
