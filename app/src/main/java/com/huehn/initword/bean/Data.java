package com.huehn.initword.bean;

import com.huehn.initword.bean.TestData;

public class Data<T extends TestData> {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
//        Thread.sleep();
    }


//    public static <T> T get(){
//        return new TestData("a", "1");

//    }
}
