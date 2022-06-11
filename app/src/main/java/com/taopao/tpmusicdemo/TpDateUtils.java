package com.taopao.tpmusicdemo;

import org.apache.commons.lang3.time.DurationFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: TaoPao
 * @Date: 3/26/21 4:29 PM
 * @Description: java类作用描述
 */
public class TpDateUtils {

    public static int getYear(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);
            return getBaseDateType(startDate, endDate, "yyyy");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getYear(Date startDate, Date endDate) {
        return getBaseDateType(startDate, endDate, "yyyy");
    }


    public static int getMonth(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);
            return getBaseDateType(startDate, endDate, "MM");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getMonth(Date startDate, Date endDate) {
        return getBaseDateType(startDate, endDate, "MM");
    }


    public static int getDay(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);
            return getBaseDateType(startDate, endDate, "dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getDay(Date startDate, Date endDate) {
        return getBaseDateType(startDate, endDate, "dd");
    }

    private static int getBaseDateType(Date startDate, Date endDate, String format) {
        String nowDateString = "";
        if (endDate.getTime() >= startDate.getTime()) {
            nowDateString = DurationFormatUtils.formatPeriod(
                    startDate.getTime(),
                    endDate.getTime(),
                    format);
        } else {
            nowDateString = DurationFormatUtils.formatPeriod(
                    endDate.getTime(),
                    startDate.getTime(),
                    format);
        }
        return Integer.parseInt(nowDateString);
    }

    public static int getAge2Day(String startTime) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(date);
        return getDay(startTime, format);
    }

    public static int getAge2Monh(String startTime) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(date);
        return getMonth(startTime, format);
    }

    public static int getAge2Year(String startTime) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(date);
        return getYear(startTime, format);
    }

    public static String getAge(String startTime) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(date);
        return getAge(startTime, format);
    }

    public static String getAge(String startTime, String endTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);

            String nowDateString = "";
            if (endDate.getTime() >= startDate.getTime()) {
                nowDateString = DurationFormatUtils.formatPeriod(
                        startDate.getTime(),
                        endDate.getTime(),
                        "yyyy-MM-dd");
            } else {
                nowDateString = DurationFormatUtils.formatPeriod(
                        endDate.getTime(),
                        startDate.getTime(),
                        "yyyy-MM-dd");
            }

            System.out.print("====:"+nowDateString+"\n");

//            Date nowDate = sdf.parse(nowDateString);
//            Calendar nowCalendar = Calendar.getInstance();
//            nowCalendar.setTime(nowDate);



            String[] splitStr = nowDateString.split("-");
            int nowYear = Integer.parseInt(splitStr[0]);
            int nowMonth = Integer.parseInt(splitStr[1]);
            int nowDay = Integer.parseInt(splitStr[2]);



            String age = "";
            if (nowYear > 0) {
                age = nowYear + "年";
            }
            if (nowMonth > 0) {
                age = age + nowMonth + "月";
            } else {
                if (nowYear > 0) {
                    age = age + "零";
                }
            }
            if (nowDay >= 0) {
                age = age + nowDay + "天";
            }
            if (endDate.getTime() >= startDate.getTime()) {
                return age;
            } else {
                return "距离出生还有" + age;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}