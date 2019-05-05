package com.huehn.initword.core.rxUtils;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * RxJava的工具类
 * 免费api  https://zhuanlan.zhihu.com/p/21320392
 */
public class RxJavaUtils {

    /**
     * 关闭观察
     * @param disposable
     */
    public static void close(Disposable disposable){
        if (disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }

    /**
     * 是否还有观察
     * @param disposable
     * @return
     */
    public static boolean isUnDisposed(Disposable disposable){
        return disposable == null || disposable.isDisposed();
    }

    /**
     * 在io线程中运行
     * @param runnable
     */
    public static Disposable runOnIOThread(Runnable runnable){
        return Observable.just(runnable)
            .observeOn(Schedulers.io())
            .subscribe(new Consumer<Runnable>() {
                @Override
                public void accept(Runnable runnable) throws Exception {
                    if (runnable != null) {
                        runnable.run();
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    throwable.printStackTrace();
                }
            });
    }

    /**
     * 在一个新线程中运行  使用Schedulers.io()的好处在于它使用线程池的事实，而Schedulers.newThread()没有。
     * @param runnable
     */
    public static Disposable runOnNewThread(Runnable runnable){
        return Observable.just(runnable)
            .observeOn(Schedulers.newThread())
            .subscribe(new Consumer<Runnable>() {
                @Override
                public void accept(Runnable runnable) throws Exception {
                    if (runnable != null) {
                        runnable.run();
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    throwable.printStackTrace();
                }
            });
    }

    /**
     * 在UI线程中进行
     * @param runnable
     */
    public static Disposable runOnUIThread(Runnable runnable){
        return Observable.just(runnable)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<Runnable>() {
                @Override
                public void accept(Runnable runnable) throws Exception {
                    if (runnable != null) {
                        runnable.run();
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    throwable.printStackTrace();
                }
            });
    }
}
