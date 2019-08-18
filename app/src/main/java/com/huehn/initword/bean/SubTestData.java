package com.huehn.initword.bean;

import com.huehn.initword.core.utils.Log.LogManager;

public class SubTestData extends TestData {

    private String title = "";

    public SubTestData(String name, String age) {
        super(name, age);
        this.title = "huehn TestData super title";
    }


    @Override
    protected void getSuperString() {
        System.out.println("huehn TestData sub getString");
    }
}
