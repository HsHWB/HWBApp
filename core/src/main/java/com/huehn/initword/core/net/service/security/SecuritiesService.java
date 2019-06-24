package com.huehn.initword.core.net.service.security;

import com.huehn.initword.core.net.BaseUrl;
import com.huehn.initword.core.net.response.security.ShangHaiPlateListResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * 关于证券的api的Service
 */
public interface SecuritiesService {

    /**
     * 获取沪深股票板块列表
     * @return
     */
    @POST(BaseUrl.SHARE_BASEURL + "/131-58/")
    Observable<ShangHaiPlateListResponse> getShangHaiPlateList(@QueryMap Map<String, Object> map);

}
