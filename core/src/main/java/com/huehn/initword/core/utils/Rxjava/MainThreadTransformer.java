package com.huehn.initword.core.utils.Rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by huehn  on 2019/5/15.
 * 这里自动切换rxjava线程，ObservableTransformer<T, R>这个接口原本用于替换改变的，
 * 这里因为不需要变化，直接ObservableTransformer<T, T>，
 */

public class MainThreadTransformer<T> implements ObservableTransformer<T, T> {
    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> MainThreadTransformer<T> create(){
        return new MainThreadTransformer<T>();
    }
}
