package com.huehn.initword;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.huehn.initword.bean.SubTestData;
import com.huehn.initword.bean.TestData;
import com.huehn.initword.core.utils.Log.LogManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MoneySum{

    public final static float money = 30000f;
    public final static float rate = 0.0003f;
    public final static int months = 10;
    public final static String beginData = "2019-05-09 00:00:00";
    public final static String endData = "2019-06-09 00:00:00";

    public final static String[] beginDataArray = {
            "2019-03-12 00:00:00",
            "2019-04-12 00:00:00",
            "2019-05-12 00:00:00",
            "2019-06-12 00:00:00",
            "2019-07-12 00:00:00",
            "2019-08-12 00:00:00",
            "2019-09-12 00:00:00",
            "2019-10-12 00:00:00",
            "2019-11-12 00:00:00",
            "2019-12-12 00:00:00"};

    public final static String[] endDataArray = {
            "2019-04-12 00:00:00",
            "2019-05-12 00:00:00",
            "2019-06-12 00:00:00",
            "2019-07-12 00:00:00",
            "2019-08-12 00:00:00",
            "2019-09-12 00:00:00",
            "2019-10-12 00:00:00",
            "2019-11-12 00:00:00",
            "2019-12-12 00:00:00",
            "2020-01-12 00:00:00"
    };

//    三万，0.004
//    两万四 0.004
    public static void main(String[] args){
//        getAll();
//        int a = 2;
//        System.out.println("a 非的结果是："+(~a));
//        System.out.println(formatString("abcaba", "A", "B"));
//        try {
//            printConstrutor(TestData.class, String.class, String.class);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
        SubTestData subTestData = new SubTestData("1", "2");
//        try {
//            Method method = TestData.class.getDeclaredMethod("getPrivateString");
//            method.setAccessible(true);
//            method.invoke(subTestData);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
        //        subTestData.printSuperString();
        subTestData.printTitle();
        System.out.println("huehn subTestData name : " + SubTestData.class.getName());
    }

//    public static String formatString(@NonNull String source, Object... args){
//        String formatString = source;
//        if (source == null || source == "" || args == null || args.length == 0){
//            return formatString;
//        }
//        try {
//            formatString = String.format(source, args);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return formatString;
//    }

    private static void printConstrutor(Class<?> classObject, Class<?>... paramsType) throws NoSuchMethodException {
        if (classObject != null){
            System.out.println(classObject.getDeclaredConstructors());
            System.out.println(classObject.getDeclaredConstructor(paramsType));
        }
    }

    public static float getAll(){
        float nowMoney = money;
        int nowMonth = months;
        float oneMonthMoney = 0;
        for (int i = 0; i < months; i++){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date = format.parse(beginDataArray[i]);
                Date date2 = format.parse(endDataArray[i]);
                oneMonthMoney = getOneMonthAllMoney(differentDays(date,date2), nowMoney, nowMonth);
                System.out.println("月份：" + endDataArray[i]);
                System.out.println("还款：" + oneMonthMoney + "\n");
                nowMoney = nowMoney - getOneMonthMoney(nowMoney, nowMonth);
                nowMonth--;
//                System.out.println("huehn getOneMonthMoney nowMoney : " + nowMoney);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 一个月要还的总额
     * money:目前钱的总额
     * @return
     */
    public static float getOneMonthAllMoney(int days, float money, int months){
        float oneDataMoney = getOneMonthMoney(money, months);//一月要还多少本金
        float oneDataRate = getOneDataMoneyRate(money);//一天要还多少利息
        float oneMonth = days * oneDataRate + oneDataMoney;
//        System.out.println("huehn getOneMonthMoney oneDataMoney : " + oneDataMoney + "    oneDataRate : " + oneDataRate
//                + "   oneMonth : " + oneMonth);

        return oneMonth;
    }

    /**
     * 一月要还的本金
     * @return
     */
    public static float getOneMonthMoney(float money, int months){
        return money / months;
    }

    /**
     * 一天要还的利息
     * @return
     */
    public static float getOneDataMoneyRate(float allMoney){
        return allMoney * rate;
    }

    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
//            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }
}
