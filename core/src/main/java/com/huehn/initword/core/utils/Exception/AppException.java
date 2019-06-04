package com.huehn.initword.core.utils.Exception;

public class AppException {

    /**
     * 判断外部储存是否可用
     * @return
     */
    public static RuntimeException hasNotStoragePermission(){
        return new RuntimeException("外部储存不可用");
    }

    /**
     * 申请权限时传入空的id
     * @return
     */
    public static RuntimeException permissionIdNull(){
        return new RuntimeException("申请的权限为空");
    }

    /**
     * 申请权限时传入的request在之前已经被占用
     * @return
     */
    public static RuntimeException permissionIdHasExits(){
        return new RuntimeException("申请的权限的request已经被占用");
    }
}
