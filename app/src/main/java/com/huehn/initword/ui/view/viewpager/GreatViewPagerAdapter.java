package com.huehn.initword.ui.view.viewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class GreatViewPagerAdapter<T extends IGreatViewPagerAdapterData> extends PagerAdapter {

    private Context context;
    private List<T> dataList = new ArrayList<>();
    private List<IItemView> itemViewList = new ArrayList<>();

    public GreatViewPagerAdapter(Context context) {
        this.context = context;
    }

    public void setDataList(List<T> dataList) {
        if (dataList != null && this.dataList != null){
            append(dataList);
        }
    }


    public void append(List<T> data){
        if (dataList != null && data != null && data.size() > 0) {
            this.dataList.addAll(data);
            notifyList();
            notifyDataSetChanged();
        }
    }

    private void notifyList(){
        itemViewList.clear();
        if (dataList != null && dataList.size() > 0){
            for (int i = 0; i < dataList.size(); i++){
                if (dataList.get(i) == null){
                    continue;
                }
                IItemView iItemView = getViewByPosition(dataList.get(i).cardType());
                if (iItemView == null){
                    continue;
                }
                itemViewList.add(iItemView);
            }
        }
        notifyDataSetChanged();
    }


    private IItemView getViewByPosition(int dataType){
        if (dataType == IItemView.IType.IMAGE_TYPE){
            return new PageImageLayout(this.context);
        }else if (dataType == IItemView.IType.STREAM_TYPE){
            return new PageStreamLayout(this.context);
        }else {
            return null;
        }
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        return super.instantiateItem(container, position);
    }

    @Override
    public int getCount() {
        return dataList != null ? dataList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
