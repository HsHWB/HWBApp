package com.huehn.initword.activity.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class ViewPagerFragment2 extends BaseFragment {

    private TextView textView;
    private int position;
    private int parentPosition;
    public final static String BUNDLE_POSITION = "position";
    public final static String BUNDLE_PARENT_POSITION = "parent_position";
    public static ViewPagerFragment2 getInstance(int position, int parentPosition){
        ViewPagerFragment2 viewPagerFragment = new ViewPagerFragment2();
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_POSITION, position);
        bundle.putInt(BUNDLE_PARENT_POSITION, parentPosition);
        viewPagerFragment.setArguments(bundle);
        return viewPagerFragment;
    }

    private void getBundle(Bundle bundle){
        if (bundle == null){
            return;
        }

        if (bundle.containsKey(BUNDLE_POSITION)){
            position = bundle.getInt(BUNDLE_POSITION);
        }

        if (bundle.containsKey(BUNDLE_PARENT_POSITION)){
            parentPosition = bundle.getInt(BUNDLE_PARENT_POSITION);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getBundle(getArguments());
        initView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_pager_text, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initView(){
        textView = getView().findViewById(R.id.view_pager_text);

        textView.setText("" + position);
        switch (parentPosition){
            case 1:
                textView.setBackgroundColor(getContext().getResources().getColor(R.color.color_26d827));
                break;
            case 2:
                textView.setBackgroundColor(getContext().getResources().getColor(R.color.color_ff7b00fd));
                break;
            case 3:
                textView.setBackgroundColor(getContext().getResources().getColor(R.color.color_e6ff3882));
                break;
            case 4:
                textView.setBackgroundColor(getContext().getResources().getColor(R.color.color_ffd800));
                break;
            case 5:
                textView.setBackgroundColor(getContext().getResources().getColor(R.color.color_ff45b6ff));
                break;
        }
    }
}
