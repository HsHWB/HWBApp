package com.huehn.initword;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.huehn.initword.basecomponent.base.BaseActivity;
import com.huehn.initword.bean.TestData;
import com.huehn.initword.core.net.response.ShangHaiPlateListResponse;
import com.huehn.initword.core.net.service.security.SecuritiesApi;
import com.huehn.initword.core.utils.Log.LogDImpl;

import java.util.ArrayList;
import java.util.List;

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
//                LogDImpl logD = new LogDImpl();
//                List list = new ArrayList();
//                for (int i = 0; i < 10; i++){
//                    TestData testData = new TestData("name" + i, "age" + i);
//                    list.add(testData);
//                }
//                logD.write(TAG, list);

                addDisposable(SecuritiesApi.getShanghaiPlateList().subscribe(new Consumer<ShangHaiPlateListResponse>() {
                    @Override
                    public void accept(ShangHaiPlateListResponse shangHaiPlateListResponse) throws Exception {

                        if (shangHaiPlateListResponse == null || shangHaiPlateListResponse.getShowapi_res_body() == null ||
                                shangHaiPlateListResponse.getShowapi_res_body().getList() == null){
                            return;
                        }
                        LogDImpl logD = new LogDImpl();
//
//                        List list = new ArrayList();
//                        for (int i = 0; i < 10; i++){
//                            TestData testData = new TestData("name" + i, "age" + i);
//                            list.add(testData);
//                        }
                        logD.write(TAG, shangHaiPlateListResponse.getShowapi_res_body().getList());
//                        Log.d(TAG, "" + shangHaiPlateListResponse.getShowapi_res_body().getList().size());
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
