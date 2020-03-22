package com.huehn.initword.ui.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.heaven7.android.dragflowlayout.DragAdapter;
import com.heaven7.android.dragflowlayout.DragFlowLayout;
import com.huehn.initword.R;
import com.huehn.initword.bean.ItemTouchData;
import com.huehn.initword.core.utils.Log.LogManager;

import java.util.ArrayList;
import java.util.List;

public class ItemTouchDragAdapter extends DragAdapter<ItemTouchData> {

//    private List<ItemTouchData> list = new ArrayList<>();
//
//    public void setList(List<ItemTouchData> list) {
//        this.list.clear();
//        this.list.addAll(list);
//    }

    @Override
    public int getItemLayoutId() {
        return R.layout.touche_item_layout;
    }

    @Override
    public void onBindData(View itemView, int dragState, ItemTouchData data) {
        itemView.setTag(data);

        TextView tv = (TextView) itemView.findViewById(R.id.touch_item_text);
        tv.setText(data.getData());
        LogManager.d("huehn onBindData data : " + data.getData());
        //        //iv_close是关闭按钮。只有再非拖拽空闲的情况下才显示
//        itemView.findViewById(R.id.iv_close).setVisibility(
//                dragState!= DragFlowLayout.DRAG_STATE_IDLE
//                        && data.draggable ? View.VISIBLE : View.INVISIBLE);
    }

    @NonNull
    @Override
    public ItemTouchData getData(View itemView) {
        LogManager.d("huehn getData data");
        return (ItemTouchData) itemView.getTag();
    }
}
