package com.huehn.initword.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huehn.initword.R;

import java.util.ArrayList;
import java.util.List;

public class StringAdapter extends RecyclerView.Adapter<StringAdapter.StringViewHolder> {

    private List<String> list = new ArrayList<>();

    public void setList(List<String> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StringViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.br_bottom_dialog_item, viewGroup, false);
        return new StringViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StringViewHolder viewHolder, int i) {
        viewHolder.textView.setText(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class StringViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;

        public StringViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tv_bottom_dialog_item);

        }



    }

}
