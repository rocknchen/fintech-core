package com.hthk;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: Rock CHEN
 * @Date: 2024/3/22 17:33
 */
public class Test1 {

    public static void main(String[] args) {

        try {
            Calendar todayStart = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat sdfTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
            todayStart.setTime(sdfTime.parse(sdf.format(new Date()) + " 00:00:01"));
            System.out.println(todayStart.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
