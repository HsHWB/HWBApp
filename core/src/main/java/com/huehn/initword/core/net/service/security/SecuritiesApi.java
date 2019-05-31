package com.huehn.initword.core.net.service.security;

import com.huehn.initword.core.net.HttpsManager;
import com.huehn.initword.core.net.request.security.ShangHaiPlateListRequest;
import com.huehn.initword.core.net.response.security.ShangHaiPlateListResponse;
import com.huehn.initword.core.utils.Rxjava.MainThreadTransformer;

import io.reactivex.Observable;

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
