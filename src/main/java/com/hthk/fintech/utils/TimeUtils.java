package com.hthk.fintech.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: Rock CHEN
 * @Date: 2024/3/22 17:37
 */
public class TimeUtils {

    public static Calendar getTodayStart() {
        Calendar todayStart = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdfTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        try {
            todayStart.setTime(sdfTime.parse(sdf.format(new Date()) + " 00:00:01"));
            return todayStart;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date getTodayStartDate() {
        Calendar todayStart = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdfTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        try {
            return sdfTime.parse(sdf.format(new Date()) + " 00:00:01");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
