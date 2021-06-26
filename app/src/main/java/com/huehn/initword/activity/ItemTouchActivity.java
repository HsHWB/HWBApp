package com.huehn.initword.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.heaven7.android.dragflowlayout.DragFlowLayout;
import com.huehn.initword.R;
import com.huehn.initword.basecomponent.base.BaseActivity;
import com.huehn.initword.bean.ItemTouchData;
import com.huehn.initword.ui.adapter.ItemTouchAdapter;
import com.huehn.initword.ui.adapter.ItemTouchDragAdapter;

import java.util.ArrayList;
import java.util.List;

public class ItemTouchActivity extends BaseActivity {

//    private RecyclerView recyclerView;
    private List<ItemTouchData> itemList = new ArrayList<>();
//    private ItemTouchAdapter itemTouchAdapter;
    private DragFlowLayout dragFlowLayout;
    private ItemTouchDragAdapter itemTouchDragAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_layout);
        initView();
        initData();
    }

    private void initView(){
//        recyclerView = findViewById(R.id.touch_recycler);
//        itemTouchAdapter = new ItemTouchAdapter();
//        recyclerView.setAdapter(itemTouchAdapter);
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(5, RecyclerView.VERTICAL));
        dragFlowLayout = findViewById(R.id.drag_flowLayout);
        itemTouchDragAdapter = new ItemTouchDragAdapter();
        dragFlowLayout.setDragAdapter(itemTouchDragAdapter);
    }

    private void initData(){
        itemList.add(new ItemTouchData("1"));
        itemList.add(new ItemTouchData("asasaas1"));
        itemList.add(new ItemTouchData("2"));
        itemList.add(new ItemTouchData("3"));
        itemList.add(new ItemTouchData("asasaas2"));
        itemList.add(new ItemTouchData("4"));
        itemList.add(new ItemTouchData("5"));
        itemList.add(new ItemTouchData("6"));
        itemList.add(new ItemTouchData("7"));
        itemList.add(new ItemTouchData("8"));
//        itemTouchAdapter.setItemList(itemList);
        for (int i = 0; i < itemList.size(); i++) {
            dragFlowLayout.getDragItemManager().addItem(i, itemList.get(i));
        }
        dragFlowLayout.beginDrag();
    }
}
