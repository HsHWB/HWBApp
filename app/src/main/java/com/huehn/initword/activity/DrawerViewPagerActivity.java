package com.huehn.initword.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.huehn.initword.R;
import com.huehn.initword.activity.fragment.ViewPagerFragment;
import com.huehn.initword.basecomponent.base.BaseActivity;
import com.huehn.initword.core.utils.Log.LogManager;
import com.huehn.initword.core.utils.SystemUtils.ViewUtils;
import com.huehn.initword.ui.adapter.ViewPagerAdapter;
import com.huehn.initword.ui.view.TouchDrawerLayout;
import com.huehn.initword.ui.view.ViewPagerForDrawer;

import java.util.ArrayList;
import java.util.List;

public class DrawerViewPagerActivity extends BaseActivity {

    private TouchDrawerLayout drawerLayout;
    private FrameLayout contentFrame;
    private RecyclerView recyclerView;
    private ViewPagerForDrawer viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private GestureDetector gestureDetector;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawelayout);
        initView();
        setListener();
    }

    private void initView(){
        drawerLayout = findViewById(R.id.drawerlayout);
        contentFrame = findViewById(R.id.drawer_frame_content);
        recyclerView = findViewById(R.id.drawer_menu);

//        TouchDrawerLayout.setCustomLeftEdgeSize(drawerLayout, 1f);

        initViewPager();
        initDrawerLayout();
        initGesture();
    }

    private void setListener(){

    }

    private void initViewPager(){
        viewPager = new ViewPagerForDrawer(this);
        viewPager.setId(View.generateViewId());
        viewPager.setTouchLimitXY(0, 500);
        viewPager.setiPageChange(new ViewPagerForDrawer.IPageChange() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

            @Override
            public void requestDrawerLayout(int position, float distance) {
//                drawerLayout.openDrawer(Gravity.END);
//                drawerLayout.scrollTo(ViewUtils.getScreenWidth() - (int) distance, 0);
                LogManager.d("huehn requestDrawerLayout distance : " + (int) distance);
            }
        });
        contentFrame.addView(viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(viewPagerAdapter);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(ViewPagerFragment.getInstance(1));
        fragmentList.add(ViewPagerFragment.getInstance(2));
        fragmentList.add(ViewPagerFragment.getInstance(3));
        fragmentList.add(ViewPagerFragment.getInstance(4));
        fragmentList.add(ViewPagerFragment.getInstance(5));
        viewPagerAdapter.setFragmentList(fragmentList);
    }

    private void initDrawerLayout(){
//        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
//            @Override
//            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
//
//            }
//
//            @Override
//            public void onDrawerOpened(@NonNull View drawerView) {
//
//            }
//
//            @Override
//            public void onDrawerClosed(@NonNull View drawerView) {
//
//            }
//
//            @Override
//            public void onDrawerStateChanged(int newState) {
//
//            }
//        });
    }

    private void initGesture(){
//        gestureDetector = new GestureDetector.SimpleOnGestureListener(){
//
//        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
