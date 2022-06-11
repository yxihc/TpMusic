package com.taopao.tpmusicdemo;

import org.apache.commons.lang3.time.DurationFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: TaoPao
 * @Date: 3/26/21 10:42 AM
 * @Description: java类作用描述
 */
public class Test {

    public static void main(String args[]) {

        System.out.print("年龄："+TpDateUtils.getAge("2021-03-13"));
        System.out.print("\n年"+TpDateUtils.getAge2Year("2021-03-13"));
        System.out.print("\n月"+TpDateUtils.getAge2Monh("2021-03-13"));
        System.out.print("\n日"+TpDateUtils.getAge2Day("2021-03-13"));

    }












}






