package com.huehn.initword.service;

import android.os.Binder;

public class BaseBinder<T extends BaseService> extends Binder {

    private T service;

    public BaseBinder(T service) {
        this.service = service;
    }



    public T getService() {
        return service;
    }
}
