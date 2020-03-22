package com.huehn.initword.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.huehn.initword.R;
import com.huehn.initword.ui.adapter.StringAdapter;

import java.util.ArrayList;
import java.util.List;

public class BottomDialog extends BottomSheetDialog {

    private View view;
    private RecyclerView recyclerView;
    private StringAdapter stringAdapter;

    public BottomDialog(@NonNull Context context) {
        super(context);
    }

    public BottomDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected BottomDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getWindow() != null) {
            getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getWindow().setGravity(Gravity.END | Gravity.BOTTOM);
            getWindow().getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            getWindow().setAttributes(lp);
        }
        setContentView(R.layout.bottom_dialog);
    }

    //    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        if (getDialog().getWindow() != null) {
//            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//            getDialog().getWindow().setGravity(Gravity.END | Gravity.BOTTOM);
//            getDialog().getWindow().getDecorView().setPadding(0, 0, 0, 0);
//            WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
//            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//            getDialog().getWindow().setAttributes(lp);
//        }
//        view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_dialog, container, false);
//        return view;
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        initView();
//        initData();
//    }

    private void initView(){
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
