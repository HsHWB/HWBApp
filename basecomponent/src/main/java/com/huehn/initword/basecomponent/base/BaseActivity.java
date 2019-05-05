package com.huehn.initword.basecomponent.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.huehn.initword.core.rxUtils.RxUtils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class BaseActivity extends AppCompatActivity {

    protected CompositeDisposable compositeDisposable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    /**
     * 
     * @param disposable
     */
    public void addDisposable(Disposable disposable){
        if (RxUtils.isUnDisposed(disposable)){
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
        RxUtils.close(compositeDisposable);
        if (compositeDisposable != null){
            compositeDisposable.clear();
            compositeDisposable = null;
        }
        super.onDestroy();
    }
}
