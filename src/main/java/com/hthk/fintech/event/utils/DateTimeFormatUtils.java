package com.hthk.fintech.event.utils;

import com.hthk.common.utils.CustomCollectionUtils;
import com.hthk.fintech.enumration.DateTimeFormatEnum;
import org.apache.commons.collections.map.HashedMap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DateTimeFormatUtils {

    public static Map<String, DateTimeFormatEnum> getFormatMap() {

        Map<String, DateTimeFormatEnum> formatMap = new HashedMap();
        List<DateTimeFormatEnum> formatList = Arrays.stream(DateTimeFormatEnum.values()).collect(Collectors.toList());
        formatList.forEach(t -> formatMap.put(t.getFormat(), t));
        return formatMap;
    }

    public static Map<DateTimeFormatEnum, String> build(LocalDateTime eventTime) {

        Map<DateTimeFormatEnum, String> dateTimeMap = new HashedMap();
        Map<String, DateTimeFormatEnum> formatMap = getFormatMap();
        List<String> formatList = CustomCollectionUtils.keyToList(formatMap);
        formatList.forEach(f -> {
            DateTimeFormatEnum formatEnum = formatMap.get(f);
            String formatStr = eventTime.format(DateTimeFormatter.ofPattern(f));
            dateTimeMap.put(formatEnum, formatStr);
        });
        return dateTimeMap;
    }
}
