package com.hthk.common.utils;

public class CustomReflectionUtils {

    public static String getMethodName(String fieldName, String methodStart) {
        return methodStart + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

}
