package com.huehn.initword;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.huehn.initword.basecomponent.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class MainActivity extends BaseActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}
