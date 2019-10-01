package com.huehn.initword.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huehn.initword.R;
import com.huehn.initword.basecomponent.base.BaseFragment;
import com.huehn.initword.ui.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerFragment extends BaseFragment {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TextView textView;
    private int position;
    public final static String BUNDLE_POSITION = "position";
    public static ViewPagerFragment getInstance(int position){
        ViewPagerFragment viewPagerFragment = new ViewPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_POSITION, position);
        viewPagerFragment.setArguments(bundle);
        return viewPagerFragment;
    }

    @Override
    public void getBundle(Bundle bundle){
        if (bundle == null){
            return;
        }
        if (bundle.containsKey(BUNDLE_POSITION)){
            position = bundle.getInt(BUNDLE_POSITION);
        }
    }

    public void initView(View view){
        if (view == null){
            return;
        }
        viewPager = view.findViewById(R.id.view_pager);
        textView = view.findViewById(R.id.view_pager_recycler);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(ViewPagerFragment2.getInstance(1, position));
        fragmentList.add(ViewPagerFragment2.getInstance(2, position));
        fragmentList.add(ViewPagerFragment2.getInstance(3, position));
        fragmentList.add(ViewPagerFragment2.getInstance(4, position));
        fragmentList.add(ViewPagerFragment2.getInstance(5, position));
        viewPagerAdapter.setFragmentList(fragmentList);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(viewPagerAdapter);
        switch (position){
            case 1:
                textView.setText("1");
                textView.setBackgroundColor(getContext().getResources().getColor(R.color.color_26d827));
                break;
            case 2:
                textView.setText("2");
                textView.setBackgroundColor(getContext().getResources().getColor(R.color.color_ff7b00fd));
                break;
            case 3:
                textView.setText("3");
                textView.setBackgroundColor(getContext().getResources().getColor(R.color.color_e6ff3882));
                break;
            case 4:
                textView.setText("4");
                textView.setBackgroundColor(getContext().getResources().getColor(R.color.color_ffd800));
                break;
            case 5:
                textView.setText("5");
                textView.setBackgroundColor(getContext().getResources().getColor(R.color.color_ff45b6ff));
                break;
        }
    }

    @Override
    public int getLayoutView() {
        return R.layout.view_pager_fragment;
    }

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.view_pager_fragment, container, false);
//        return view;
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
