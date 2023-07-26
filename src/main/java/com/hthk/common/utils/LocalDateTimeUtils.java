package com.hthk.common.utils;

import com.hthk.fintech.exception.AttributeMissingException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeUtils {

    public static String format(String format, LocalDateTime time) {
        if (time == null) {
//            throw new ()
        }
        return time.format(DateTimeFormatter.ofPattern(format));
    }
}
