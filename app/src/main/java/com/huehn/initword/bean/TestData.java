package com.huehn.initword.bean;

import com.huehn.initword.core.utils.Log.LogManager;

public class TestData {

    private String name;
    private String age;

    private String title;

    public TestData(String name, String age) {
        this.name = name;
        this.age = age;
        this.title = "huehn TestData super title";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    private void getString(){
        System.out.println("huehn TestData super getString");
    }
    protected void getSuperString(){
        getString();
    }

    public void printSuperString(){
        getSuperString();
    }

    private void getPrivateString(){
        getString();
    }

    public void printTitle(){
        System.out.println(title);
    }
}
