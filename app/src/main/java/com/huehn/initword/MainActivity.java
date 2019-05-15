package com.huehn.initword;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.huehn.initword.basecomponent.base.BaseActivity;
import com.huehn.initword.core.net.response.ShangHaiPlateListResponse;
import com.huehn.initword.core.net.service.security.SecuritiesApi;

import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity {

    public final static String TAG = "MainActivity";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDisposable(SecuritiesApi.getShanghaiPlateList().subscribe(new Consumer<ShangHaiPlateListResponse>() {
                    @Override
                    public void accept(ShangHaiPlateListResponse shangHaiPlateListResponse) throws Exception {
                        Log.d(TAG, "" + shangHaiPlateListResponse.getShowapi_res_body().getList().size());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }));
            }
        });
    }
}
