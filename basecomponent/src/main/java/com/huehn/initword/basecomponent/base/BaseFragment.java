package com.huehn.initword.basecomponent.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.huehn.initword.core.utils.Rxjava.RxJavaUtils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseFragment extends Fragment {

    protected CompositeDisposable compositeDisposable;

    public void replace(FragmentManager fragmentManager, int layoutId){
        if (fragmentManager == null || layoutId == 0){
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(layoutId, this);
        transaction.commitAllowingStateLoss();
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getBundle(getArguments());
        initView(getView());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int resId = getLayoutView();
        View view = inflater.inflate(resId, container, false);

        if (view == null) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }else {
            return view;
        }
    }


    @Override
    public void onDestroy() {
        RxJavaUtils.close(compositeDisposable);
        if (compositeDisposable != null){
            compositeDisposable.clear();
            compositeDisposable = null;
        }
        super.onDestroy();
    }


    public abstract void getBundle(Bundle bundle);
    public abstract void initView(View view);
    public abstract int getLayoutView();

}
