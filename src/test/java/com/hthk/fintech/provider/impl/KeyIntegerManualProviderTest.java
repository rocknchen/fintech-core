package com.hthk.fintech.provider.impl;

import com.hthk.fintech.model.math.DigitOffset;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Rock CHEN
 * @Date: 2024/2/8 11:30
 */
public class KeyIntegerManualProviderTest {

    private final static Logger logger = LoggerFactory.getLogger(KeyIntegerManualProviderTest.class);

    @Test
    public void testProvider_OFFSET_1() {

        long start = 21;
        int offset = 1;
        int count = 10;

        KeyIntegerManualProvider provider = new KeyIntegerManualProvider();
        DigitOffset digitOffset = new DigitOffset(start, offset);
        provider.init(digitOffset);

        long[] keys = provider.process(count);

        Assert.assertTrue("key length incorrect", keys.length == count);
        Assert.assertTrue("key start incorrect", keys[0] == start);
        Assert.assertTrue("key end incorrect", keys[count - 1] == start + offset * (count - 1));
    }

    @Test
    public void testProvider_OFFSET_1_TWICE() {

        long start = 21;
        int offset = 1;
        int count = 10;
        int count2 = 2;

        KeyIntegerManualProvider provider = new KeyIntegerManualProvider();
        DigitOffset digitOffset = new DigitOffset(start, offset);
        provider.init(digitOffset);

        long[] keys = provider.process(count);

        Assert.assertTrue("key length incorrect", keys.length == count);
        Assert.assertTrue("key start incorrect", keys[0] == start);
        Assert.assertTrue("key end incorrect", keys[count - 1] == start + offset * (count - 1));

        long[] keys2 = provider.process(count2);

        long newStart = start + offset * (count - 1) + offset;

        Assert.assertTrue("key length incorrect", keys2.length == count2);
        Assert.assertTrue("key start incorrect", keys2[0] == newStart);
        Assert.assertTrue("key end incorrect", keys2[count2 - 1] == newStart + offset * (count2 - 1));
    }

}