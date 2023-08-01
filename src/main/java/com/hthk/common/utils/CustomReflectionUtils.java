package com.hthk.common.utils;

import com.hthk.common.exception.ServiceException;
import com.hthk.fintech.enumration.CSVField;
import org.apache.commons.collections.map.HashedMap;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomReflectionUtils {

    public static String getMethodName(String fieldName, String methodStart) {
        return methodStart + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public static void setField(Object model, String cp, Object value) throws ServiceException {

        try {
            Field field = model.getClass().getDeclaredField(cp);
            field.setAccessible(true);
            field.set(model, value);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public static Map<String, Method> buildFunctionMap(Class<?> clz) {

        Map<String, Method> map = new HashedMap();
        List<Field> fieldList = Arrays.stream(clz.getDeclaredFields()).collect(Collectors.toList());
        fieldList.forEach(t -> {
            String csvHeader = t.getAnnotation(CSVField.class).header();
            String fieldName = t.getName();
            String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            try {
                Method method = clz.getDeclaredMethod(setterName, String.class);
                map.put(csvHeader, method);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
        return map;
    }

}
