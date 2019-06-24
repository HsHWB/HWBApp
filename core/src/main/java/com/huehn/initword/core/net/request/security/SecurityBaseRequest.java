package com.huehn.initword.core.net.request.security;

import com.huehn.initword.core.utils.SystemUtils.TimeUtils;

import java.util.HashMap;
import java.util.Map;

public abstract class SecurityBaseRequest {

    public final static String showapi_appid = "showapi_appid";//易源应用id
    public final static String showapi_sign = "showapi_sign";
    public final static String showapi_timestamp = "showapi_timestamp";
    public final static String showapi_res_gzip = "showapi_res_gzip";
    public final static String showapi_appsercet = "dff0b925ac3743c58ce2dd37e32654e8";//秘钥 c90dccb1a63b404391648108f01c8918
    private Map<String, Object> map = new HashMap<>();

    public final static String SHOWAPI_APPID = "96774";//93778

    protected SecurityBaseRequest() {
        toMap();
    }


    protected void toMap(){
        map.put(showapi_appid, SHOWAPI_APPID);
        map.put(showapi_sign, showapi_appsercet);
        map.put(showapi_timestamp, TimeUtils.getTimeByFormat(System.currentTimeMillis(), "yyyyMMddHHmmss"));
        map.put(showapi_res_gzip, "0");
        setMap(map);
    }

    public abstract void setMap(Map<String, Object> map);


    public Map<String, Object> getMap(){
        if (map == null){
            return new HashMap<String, Object>();
        }

        return map;
    }
}
