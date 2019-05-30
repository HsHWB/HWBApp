package com.huehn.initword.core.utils.Log;

import com.huehn.initword.core.module.Log.BaseLogWMethod;

public class LogWImpl extends BaseLogWMethod {

    private int fileId;

    public LogWImpl(int fileId) {
        this.fileId = fileId;
    }

    /**
     * 也可以直接自己返回一个文件路径
     * @return
     */
    @Override
    protected String getFilePath() {
        return getFilePathById(fileId);
    }
}
