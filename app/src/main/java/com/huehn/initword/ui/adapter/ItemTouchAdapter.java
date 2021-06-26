package com.huehn.initword.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huehn.initword.R;

import java.util.ArrayList;
import java.util.List;

public class ItemTouchAdapter extends RecyclerView.Adapter<ItemTouchAdapter.ViewHolder> {

    private List<String> itemList = new ArrayList<>();

    public void setItemList(List<String> itemList) {
        this.itemList.clear();
        this.itemList.addAll(itemList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.touche_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private ImageView closeImg;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.touch_item_text);
            closeImg = itemView.findViewById(R.id.touch_item_close);
        }

    }

}
