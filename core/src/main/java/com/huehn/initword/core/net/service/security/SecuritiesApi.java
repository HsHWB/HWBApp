package com.huehn.initword.core.net.service.security;

import com.huehn.initword.core.net.HttpsManager;
import com.huehn.initword.core.net.request.ShangHaiPlateListRequest;
import com.huehn.initword.core.net.response.ShangHaiPlateListResponse;

import io.reactivex.Observable;

public class SecuritiesApi {

    public static SecuritiesService getSecuritiesService(){
        return HttpsManager.getInstance().getRetrofit().create(SecuritiesService.class);
    }

    public static Observable<ShangHaiPlateListResponse> getShanghaiPlateList(){
        ShangHaiPlateListRequest request = new ShangHaiPlateListRequest();
        return getSecuritiesService().getShangHaiPlateList(request.getMap());
    }
}
