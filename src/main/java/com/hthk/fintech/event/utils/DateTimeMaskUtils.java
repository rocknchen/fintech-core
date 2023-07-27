package com.hthk.fintech.event.utils;

import com.hthk.common.utils.CustomCollectionUtils;

import java.util.List;
import java.util.Map;

public class DateTimeMaskUtils {

    public static String unMask(String dataMaskedStr, Map<String, String> dataMap) {

        String dateUnMasked = dataMaskedStr;
        List<String> formatOrigKeyList = CustomCollectionUtils.keyToList(dataMap);
        for (int i = 0; i < formatOrigKeyList.size(); i++) {
            String origKey = formatOrigKeyList.get(i);
            String key = formatAsMasked(origKey);
            String value = dataMap.get(origKey);
            dateUnMasked = dateUnMasked.replaceAll(key, value);
        }
        return dateUnMasked;
    }

    private static String formatAsMasked(String origKey) {
        return "#" + origKey + "#";
    }

}
