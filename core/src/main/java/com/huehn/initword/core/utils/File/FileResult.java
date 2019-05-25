package com.huehn.initword.core.utils.File;

import java.io.File;

/**
 * 创建文件返回结果
 */
public class FileResult {

    private int resultCode = 0;
    private File file;

    public final static int FILE_HAS_EXITS = 1;//文件已经存在
    public final static int FILE_CREATE_SUCCESS = 2;//创建文件成功
    public final static int FILE_CREATE_FAIL = 3;//创建文件失败

    public FileResult(int resultCode, File file) {
        this.resultCode = resultCode;
        this.file = file;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    /**
     * 获取是否创建文件或者打开文件成功
     * @return
     */
    public boolean isSuccessToGetFile(){
        return (resultCode == FILE_HAS_EXITS || resultCode == FILE_CREATE_SUCCESS) && file != null;
    }
}
