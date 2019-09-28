package com.huehn.initword.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.huehn.initword.R;
import com.huehn.initword.basecomponent.base.BaseActivity;
import com.huehn.initword.core.utils.Log.LogManager;
import com.huehn.initword.core.utils.SystemUtils.AppUtils;
import com.huehn.initword.service.service.MainThreadService;
import com.huehn.initword.service.serviceConnection.MainServiceConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ServiceActivity extends BaseActivity {

    private MainThreadService mainThreadService;
    @BindView(R.id.intent_start)
    public TextView intentStart;
    @BindView(R.id.bind_start)
    public TextView bindStart;
    @BindView(R.id.intent_start_stop)
    public TextView intentStartStop;
    @BindView(R.id.bind_start_unbind)
    public TextView bindStartUnBind;

    private MainServiceConnection mainServiceConnection;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_activity);
        LogManager.d("huehn RemoteProcessActivity onCreate getNowProcessName : " + AppUtils.getNowProcessName());
        unbinder = ButterKnife.bind(this);
        initService();
    }

    private void initService(){

    }


    private void startServiceByIntent(){
        Intent intent = new Intent(ServiceActivity.this, MainThreadService.class);
        ServiceActivity.this.startService(intent);
    }

    private void startServiceByBind(){
        Intent intent = new Intent(ServiceActivity.this, MainThreadService.class);
        mainServiceConnection = new MainServiceConnection();
        bindService(intent, mainServiceConnection, BIND_AUTO_CREATE);
    }

    /**
     * 通过intent开启的service，要通过stopService或者stopSelf()去关闭
     * 如果同时通过intent和bind去启动了service，那么要stopService以及unbind才能完全关闭service
     */
    private void intentStop(){
        Intent intent = new Intent(ServiceActivity.this, MainThreadService.class);
        ServiceActivity.this.stopService(intent);
    }

    /**
     * 通过bind启动的service，要通过unBind去关闭
     */
    private void bindStop(){
        if (mainServiceConnection != null) {
            unbindService(mainServiceConnection);
        }
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
                bindStop();
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
