package com.huehn.initword.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huehn.initword.R;
import com.huehn.initword.basecomponent.base.BaseActivity;
import com.huehn.initword.ui.adapter.StringAdapter;


import java.util.ArrayList;
import java.util.List;

public class BehaviorActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private StringAdapter stringAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.br_bottom_sheet_dialog);
        initView();
        initData();
    }

    private void initView(){
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        stringAdapter = new StringAdapter();
        recyclerView.setAdapter(stringAdapter);
    }
    private void initData(){
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        list.add("11");
        list.add("12");
        list.add("13");
        list.add("14");
        list.add("15");
        list.add("16");
        list.add("17");
        list.add("18");
        list.add("19");
        list.add("20");
        list.add("21");
        list.add("22");
        list.add("23");
        list.add("24");
        list.add("25");
        list.add("26");
        list.add("27");
        list.add("28");
        list.add("29");
        list.add("30");
        stringAdapter.setList(list);
    }
}
