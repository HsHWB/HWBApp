package com.huehn.initword.core.module.Log;

/**
 * 打印功能应该实现的功能
 */
public interface ILog {

    /**
     * debug模式，正式包不会打印这个
     * @param tag
     * @param value
     */
    void d(String tag, Object value);
    void d(Object value);

    /**
     * 正式包也会打印这个
     * @param tag
     * @param value
     */
    void i(String tag, Object value);
    void i(Object value);

    /**
     * log写入文件
     * @param tag
     * @param subPath
     * @param value
     */
    void w(String tag, String subPath, Object value);
    void w(String subPath, Object value);
}
