package com.huehn.initword.ui.view.viewpager;

public interface IItemStreamView extends IItemView{

    String getStreamUrl();

    /**
     * 播放
     */
    void play();

    /**
     * 暂停
     */
    void pause();

    /**
     * 停止
     */
    void stop();

    /**
     * 回收
     */
    void realse();
}
