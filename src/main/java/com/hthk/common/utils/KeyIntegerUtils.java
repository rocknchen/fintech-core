package com.hthk.common.utils;

/**
 * @Author: Rock CHEN
 * @Date: 2024/2/8 10:53
 */
public class KeyIntegerUtils {

    public static long[] generate(long start, int offset, int length) {

        long[] result = new long[length];
        long cursor;
        for (int i = 0; i < length; i++) {
            cursor = start + offset * i;
            result[i] = cursor;
        }
        return result;
    }
}
