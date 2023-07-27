package com.hthk.fintech.event.utils;

import com.hthk.fintech.enumration.DateTimeFormatEnum;
import org.apache.commons.collections.map.HashedMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DateTimeFormatUtils {

    public static Map<String, DateTimeFormatEnum> getFormatMap() {

        Map<String, DateTimeFormatEnum> formatMap = new HashedMap();
        List<DateTimeFormatEnum> formatList = Arrays.stream(DateTimeFormatEnum.values()).collect(Collectors.toList());
        formatList.forEach(t -> formatMap.put(t, t));
        return formatMap;
    }

}
