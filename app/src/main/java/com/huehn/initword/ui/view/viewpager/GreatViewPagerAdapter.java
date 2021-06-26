package com.huehn.initword.ui.view.viewpager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.huehn.initword.core.utils.Log.LogManager;

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
            this.dataList.clear();
            append(dataList);
        }
    }


    public void append(List<T> data){
        if (dataList != null && data != null && data.size() > 0) {
            if (data.size() == 1){
                this.dataList.addAll(data);
            }else if (data.size() > 1){
                this.dataList.add(data.get(data.size() - 1));
                this.dataList.addAll(data);
                this.dataList.add(data.get(0));
            }
            notifyList();
            notifyDataSetChanged();
        }
    }

    public List<T> getDataList() {
        return dataList;
    }

    private void notifyList(){
        itemViewList.clear();
        if (dataList != null && dataList.size() > 0){
            for (int i = 0; i < dataList.size(); i++){
                if (dataList.get(i) == null){
                    continue;
                }
                IItemView iItemView = getViewByPosition(dataList.get(i).cardType(), i);
                if (iItemView == null){
                    continue;
                }
                itemViewList.add(iItemView);
            }
        }
        notifyDataSetChanged();
    }


    private IItemView getViewByPosition(int dataType, int position){
        if (dataType == IItemView.IType.IMAGE_TYPE){
            return new PageImageLayout(this.context);
        }else if (dataType == IItemView.IType.STREAM_TYPE){
            return new PageStreamLayout(this.context, position);
        }else {
            return null;
        }
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        if (position < itemViewList.size() && itemViewList.get(position) != null && itemViewList.get(position).getView() != null){
            container.addView(itemViewList.get(position).getView());
            LogManager.d("huehn instantiateItem position : " + position);
            return itemViewList.get(position).getView();
        }else {
            return null;
        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);

        if (container != null && position < itemViewList.size() && itemViewList.get(position) != null && itemViewList.get(position).getView() != null){
            container.removeView(itemViewList.get(position).getView());
        }

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
