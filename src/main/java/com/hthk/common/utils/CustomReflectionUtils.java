package com.hthk.common.utils;

import com.hthk.common.exception.ServiceException;

import java.lang.reflect.Field;

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

}
