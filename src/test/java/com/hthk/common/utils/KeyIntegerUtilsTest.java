package com.hthk.common.utils;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Rock CHEN
 * @Date: 2024/2/8 10:53
 */
public class KeyIntegerUtilsTest {

    private final static Logger logger = LoggerFactory.getLogger(KeyIntegerUtilsTest.class);

    @Test
    public void testGenerate_OFFSET_1() {

        long start = 21;
        int offset = 1;
        int length = 10;

        long[] keys = KeyIntegerUtils.generate(start, offset, length);
        Assert.assertTrue("key length incorrect", keys.length == length);
        Assert.assertTrue("key start incorrect", keys[0] == start);
        Assert.assertTrue("key end incorrect", keys[length - 1] == start + offset * (length - 1));
    }

}