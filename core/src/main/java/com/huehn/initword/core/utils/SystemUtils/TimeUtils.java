package com.huehn.initword.core.utils.SystemUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 跟时间有关的工具类
 */
public class TimeUtils {

    /**
     * 传入时间戳，返回对应格式的时间
     * @param timeMillis 毫秒
     * @param format 格式，比如YYYY_MM_DD
     * @return
     */
    public static String getTimeByFormat(long timeMillis, String format){
        Date date = null;
        SimpleDateFormat df = new SimpleDateFormat(format, Locale.getDefault());
        try {
            date = df.parse(String.valueOf(timeMillis));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("huehn getTimeByFormat : " + (date == null ? "" : "" + date.getTime()));
        return date == null ? "" : "" + date.getTime();
    }

}
