package com.huehn.initword.core.utils.Exception;

public class AppException {

    /**
     * 判断外部储存是否可用
     * @return
     */
    public static RuntimeException hasNotStoragePermission(){
        return new RuntimeException("外部储存不可用");
    }

}
