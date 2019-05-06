package com.huehn.initword.core.net.request.security;

import com.huehn.initword.core.utils.TimeUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SecurityBaseRequest {

    public final static String showapi_appid = "showapi_appid";//易源应用id
    public final static String showapi_sign = "showapi_sign";
    public final static String showapi_timestamp = "showapi_timestamp";
    public final static String showapi_res_gzip = "showapi_res_gzip";
    private Map<String, Object> map = new HashMap<>();

    public final static String SHOWAPI_APPID = "93778";

    public SecurityBaseRequest() {
        toMap();
    }


    protected void toMap(){
        map.put(showapi_appid, SHOWAPI_APPID);
        map.put(showapi_sign, showapi_sign);
        map.put(showapi_timestamp, TimeUtils.getTimeByFormat(System.currentTimeMillis(), "yyyyMMddHHmmss"));
        map.put(showapi_res_gzip, showapi_res_gzip);
        setMap(map);
    }

    public abstract void setMap(Map<String, Object> map);

    public Map<String, Object> getMap(){

        if (map == null){
            return new HashMap<String, Object>();
        }
        /*
        您首先需要设置除了showapi_sign之外的所有必传参数，例如：

http://route.showapi.com/109-35?title=足球&page=1&pag=for_test&showapi_appid=123

step2.字典排序

对上述参数key进行排序按照字典序(a-z)，请注意byte[]类型的参数不参与排序和计算签名，比如上传的文件；空值的参数也不参与排序和计算签名。排序后以key+value方式拼装字符串如下：

pagfor_testpage1showapi_appid123title足球

请注意上述的pag字段排在page字段之前

step3.md5签名

String str="pagfor_testpage1showapi_appid123title足球"

str=str+secret

也就是str=str+"006513e01bd344fca03610d1fd0145f0" //secret用小写

最后str="pagfor_testpage1showapi_appid123title足球006513e01bd344fca03610d1fd0145f0"

注意在签名计算时,中文依然是中文,并没有被urlencode

String sign=DigestUtils.md5Hex(str.getBytes("utf-8"))

最后得到 sign="030554F4F9375B4DCFEF5ECEC4488737"

step4.加入sign字段

把摘要后得到的32位字符串以showapi_sign参数发送至接口中心，即以下格式

http://route.showapi.com/109-35?title=足球&page=1&pag=for_test&showapi_appid=123&showapi_sign=030554F4F9375B4DCFEF5ECEC4488737
         */
//        List<>
//        Collections.sort(list, new Comparator<CLBean.CompanyBean>() {
//            @Override
//            public int compare(CLBean.CompanyBean o1, CLBean.CompanyBean o2) {
//                return o1.pinyin.compareTo(o2.pinyin) ;
//            }
//        });
//
//        for (Map.Entry<String, Object> entry : map.entrySet()) {
//
//            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//
//        }

        return map;
    }
}
