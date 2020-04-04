package com.huehn.initword.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.huehn.initword.R;
import com.huehn.initword.basecomponent.base.BaseActivity;
import com.huehn.initword.ui.view.viewpager.GreatPageTransformer;
import com.huehn.initword.ui.view.viewpager.GreatViewPager;
import com.huehn.initword.ui.view.viewpager.GreatViewPagerAdapter;
import com.huehn.initword.ui.view.viewpager.IItemView;
import com.huehn.initword.ui.view.viewpager.ItemData;

import java.util.ArrayList;
import java.util.List;

public class GreatPagerActivity extends BaseActivity {

    private GreatViewPager greatViewPager;
    private GreatViewPagerAdapter<ItemData> greatViewPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_great_pager);
        greatViewPager = findViewById(R.id.great_view_pager);
        greatViewPagerAdapter = new GreatViewPagerAdapter(this);
        greatViewPager.setAdapter(greatViewPagerAdapter);
        List<ItemData> dataList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            ItemData itemData = new ItemData();
            itemData.setCardType(i % 2 == 0 ? IItemView.IType.IMAGE_TYPE : IItemView.IType.STREAM_TYPE);
            dataList.add(itemData);
        }
        greatViewPager.setPageTransformer(true, new GreatPageTransformer());
        greatViewPager.setOffscreenPageLimit(dataList.size());
        greatViewPagerAdapter.setDataList(dataList);
    }



}
