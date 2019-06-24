package com.huehn.initword.basecomponent.base;

import com.huehn.initword.basecomponent.bean.permission.PermissionResult;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

public class PermissionObservable {
    private Observable observable;//事件发出和观察
    private ObservableEmitter<PermissionResult> observableEmitter;//发送事件

    public PermissionObservable() {
    }

    public PermissionObservable(Observable observable, ObservableEmitter<PermissionResult> observableEmitter) {
        this.observable = observable;
        this.observableEmitter = observableEmitter;
    }

    public Observable getObservable() {
        return observable;
    }

    public void setObservable(Observable observable) {
        this.observable = observable;
    }

    public ObservableEmitter<PermissionResult> getObservableEmitter() {
        return observableEmitter;
    }

    public void setObservableEmitter(ObservableEmitter<PermissionResult> observableEmitter) {
        this.observableEmitter = observableEmitter;
    }
}
