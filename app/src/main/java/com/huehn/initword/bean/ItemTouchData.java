package com.huehn.initword.bean;

import com.heaven7.android.dragflowlayout.IDraggable;

public class ItemTouchData implements IDraggable {

    private String data;

    public ItemTouchData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    @Override
    public boolean isDraggable() {
        return true;
    }
}
