package com.hthk.common.utils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomCollectionUtils {

    public static <T> List<T> keyToList(Map<T, ?> beanMap) {
        return beanMap.keySet().stream().collect(Collectors.toList());
    }

    public static <T> List<T> valueToList(Map<?, T> beanMap) {
        return beanMap.values().stream().collect(Collectors.toList());
    }

}
