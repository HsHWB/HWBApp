package com.huehn.initword;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.huehn.initword.basecomponent.base.BaseActivity;
import com.huehn.initword.core.net.HttpsManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class MainActivity extends BaseActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpsManager.getInstance().getDemo();
            }
        });
    }
}
