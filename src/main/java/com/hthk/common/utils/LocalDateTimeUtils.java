package com.hthk.common.utils;

import com.hthk.fintech.exception.AttributeEmptyException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.hthk.fintech.config.FintechStaticData.*;

public class LocalDateTimeUtils {

    public static String format(String format, LocalDateTime time) throws AttributeEmptyException {

        if (time == null) {
            throw new AttributeEmptyException("time");
        }
        return time.format(DateTimeFormatter.ofPattern(format));
    }

    public static LocalDateTime getNow() {
        return LocalDateTime.now();
    }

    public static LocalDateTime parse(Date date) {
        return LocalDateTime.parse(DEFAULT_DATE_TIME_FORMAT_SDF.format(date), DEFAULT_DATE_TIME_FORMAT_FORMATTER);
    }

}
