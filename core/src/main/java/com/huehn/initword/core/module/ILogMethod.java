package com.huehn.initword.core.module;

/**
 * ILog下，每一个impl都应该实现的功能
 * 不同的impl对应着不同的功能，比如debug输出，正式包输出，输出的同时写文件到本地。但是它们都是重写write功能
 */
public interface ILogMethod {

    void write(String tag, Object object);
}
