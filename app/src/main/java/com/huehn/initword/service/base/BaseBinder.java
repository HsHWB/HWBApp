package com.huehn.initword.service.base;

import android.os.Binder;

public class BaseBinder<T extends BaseService> extends Binder implements IBaseBinder<T>{

    private T service;

    public BaseBinder(T service) {
        this.service = service;
    }

    @Override
    public T getService() {
        return service;
    }
}
