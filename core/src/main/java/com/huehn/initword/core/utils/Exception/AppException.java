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

    /**
     * 给httpURLConnection配置的方法在 @link #HttpURLType 中没找到
     * @return
     */
    public static RuntimeException httpUrlMethodNotFind(){
        return new RuntimeException("HttpURLConnection设置了错误的请求方法");
    }

    /**
     * 文件创建失败
     * @return
     */
    public static RuntimeException fileCreateError(){
        return new RuntimeException("文件创建失败");
    }
}
