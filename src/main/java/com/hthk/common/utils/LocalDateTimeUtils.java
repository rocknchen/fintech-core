package com.hthk.common.utils;

import com.hthk.fintech.exception.AttributeEmptyException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeUtils {

    public static String format(String format, LocalDateTime time) throws AttributeEmptyException {

        if (time == null) {
            throw new AttributeEmptyException("time");
        }
        return time.format(DateTimeFormatter.ofPattern(format));
    }
}
