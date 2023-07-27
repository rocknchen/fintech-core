package com.hthk.common.utils;

import org.springframework.util.CollectionUtils;

import javax.sql.rowset.serial.SerialException;
import java.util.*;
import java.util.stream.Collectors;

public class CustomCollectionUtils {

    public static <T> List<T> keyToList(Map<T, ?> beanMap) {
        return beanMap.keySet().stream().collect(Collectors.toList());
    }

    public static <T> List<T> valueToList(Map<?, T> beanMap) {
        return beanMap.values().stream().collect(Collectors.toList());
    }

    public static List<String> toList(String[] info) {

        List<String> list = new ArrayList<>();
        if (info == null) {
            return list;
        }
        for (int i = 0; i < info.length; i++) {
            list.add(info[i]);
        }
        return list;
    }

    public static <T> T getUnique(
            List<T> list,
            String entityName
    ) throws ServiceException {
        CustomCollectionUtils.verifyNotEmpty(list, SerialException.class, entityName);
        CustomCollectionUtils.verifyCount(list, 1, ServiceException.class, entityName);
        return list.get(0);
    }

    public static <T, E extends Exception> void verifyNotEmpty(
            List<T> list,
            Class<E> serviceExceptionClass,
            String entityName
    ) throws ServiceException {
        if (CollectionUtils.isEmpty(list)) {
            throw new ServiceException(entityName + " can not be empty");
        }
    }

    public static <T, E extends Exception> void verifyCount(
            List<T> list,
            int expectedCount,
            Class<ServiceException> serviceExceptionClass,
            String entityName
    ) throws ServiceException {
        if (list.size() != expectedCount) {
            throw new ServiceException(entityName + " expected to be " + expectedCount);
        }
    }

    public static <T> String getDisplay(List<T> list) {
        return list.stream().map(Objects::toString).collect(Collectors.joining("\r\n"));
    }

    public static String[] toArrayStr(List<?> list) {
        if (CollectionUtils.isEmpty(list)) {
            return new String[0];
        }
        String[] array = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = Optional.ofNullable(list.get(i)).map(t -> t.toString()).orElse(null);
        }
        return array;
    }

}
