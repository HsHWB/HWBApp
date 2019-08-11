package com.huehn.initword.bean;

import com.huehn.initword.core.utils.Log.LogManager;

public class SubTestData extends TestData {
    public SubTestData(String name, String age) {
        super(name, age);
    }


    @Override
    protected void getSuperString() {
        System.out.println("huehn TestData sub getString");
    }
}
