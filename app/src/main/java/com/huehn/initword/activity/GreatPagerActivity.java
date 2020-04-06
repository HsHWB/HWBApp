package com.huehn.initword.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.huehn.initword.R;
import com.huehn.initword.basecomponent.base.BaseActivity;
import com.huehn.initword.core.utils.Log.LogManager;
import com.huehn.initword.ui.view.viewpager.GreatPageTransformer;
import com.huehn.initword.ui.view.viewpager.GreatViewPager;
import com.huehn.initword.ui.view.viewpager.GreatViewPagerAdapter;
import com.huehn.initword.ui.view.viewpager.IItemView;
import com.huehn.initword.ui.view.viewpager.ItemData;
import com.huehn.initword.ui.view.viewpager.PageStreamLayout;

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

        greatViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (dataList.get(i).getCardType() == IItemView.IType.STREAM_TYPE){
                    ((PageStreamLayout)greatViewPager.getChildAt(i)).play();
                }
                if (i - 1>= 0 && dataList.get(i - 1).getCardType() == IItemView.IType.STREAM_TYPE){
                    LogManager.d("huehn onPageSelected i - 1 : " + (i - 1) + " is a video");
                    ((PageStreamLayout)greatViewPager.getChildAt(i - 1)).pause();
                }
                if (i + 1 < dataList.size() && dataList.get(i + 1).getCardType() == IItemView.IType.STREAM_TYPE){
                    LogManager.d("huehn onPageSelected i + 1 : " + (i + 1) + " is a video");
                    ((PageStreamLayout)greatViewPager.getChildAt(i + 1)).pause();
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }



}
