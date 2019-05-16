package com.huehn.initword.core.net.service.security;

import com.huehn.initword.core.net.HttpsManager;
import com.huehn.initword.core.net.request.ShangHaiPlateListRequest;
import com.huehn.initword.core.net.response.ShangHaiPlateListResponse;
import com.huehn.initword.core.utils.MainThreadTransformer;
import com.huehn.initword.core.utils.RxJavaUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SecuritiesApi {

    public static SecuritiesService getSecuritiesService(){
        return HttpsManager.getInstance().getRetrofit().create(SecuritiesService.class);
    }

    public static Observable<ShangHaiPlateListResponse> getShanghaiPlateList(){
        ShangHaiPlateListRequest request = new ShangHaiPlateListRequest();
        return getSecuritiesService().getShangHaiPlateList(request.getMap())
                .compose(MainThreadTransformer.<ShangHaiPlateListResponse>create());
    }
}
