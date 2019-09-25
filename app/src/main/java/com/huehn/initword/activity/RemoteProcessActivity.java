package com.huehn.initword.activity;

import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.huehn.initword.R;
import com.huehn.initword.basecomponent.base.BaseActivity;
import com.huehn.initword.core.utils.Log.LogManager;
import com.huehn.initword.core.utils.SystemUtils.AppUtils;
import com.huehn.initword.service.MainThreadService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RemoteProcessActivity extends BaseActivity {

    private MainThreadService mainThreadService;
    @BindView(R.id.intent_start)
    public TextView intentStart;
    @BindView(R.id.bind_start)
    public TextView bindStart;
    @BindView(R.id.intent_start_stop)
    public TextView intentStartStop;
    @BindView(R.id.bind_start_unbind)
    public TextView bindStartUnBind;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_process);
        LogManager.d("huehn RemoteProcessActivity onCreate getNowProcessName : " + AppUtils.getNowProcessName());
        unbinder = ButterKnife.bind(this);
        initService();
    }

    private void initService(){

    }


    private void startServiceByIntent(){
        Intent intent = new Intent(RemoteProcessActivity.this, MainThreadService.class);
        RemoteProcessActivity.this.startService(intent);
    }

    private void startServiceByBind(){

    }

    /**
     * 通过intent开启的service，要通过stopService或者stopSelf()去关闭
     * 如果同时通过intent和bind去启动了service，那么要stopService以及unbind才能完全关闭service
     */
    private void intentStop(){
        Intent intent = new Intent(RemoteProcessActivity.this, MainThreadService.class);
        RemoteProcessActivity.this.stopService(intent);
    }

    /**
     * 通过bind启动的service，要通过unBind去关闭
     */
    private void bindStop(){
//        unbindService();
    }

    @OnClick({R.id.intent_start, R.id.bind_start, R.id.intent_start_stop, R.id.bind_start_unbind})
    public void click(View view){
        switch (view.getId()){
            case R.id.intent_start:
                startServiceByIntent();
                break;
            case R.id.bind_start:
                startServiceByBind();
                break;

            case R.id.intent_start_stop:
                intentStop();
                break;

            case R.id.bind_start_unbind:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null){
            unbinder.unbind();
        }
    }
}
