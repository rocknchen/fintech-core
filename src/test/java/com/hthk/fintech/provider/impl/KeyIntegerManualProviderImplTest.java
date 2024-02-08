package com.hthk.fintech.provider.impl;

import com.hthk.fintech.model.math.DigitOffset;
import com.hthk.fintech.provider.KeyIntegerManualProvider;
import com.hthk.fintech.test.basic.AbstractAppContextTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @Author: Rock CHEN
 * @Date: 2024/2/8 13:55
 */
public class KeyIntegerManualProviderImplTest extends AbstractAppContextTest {

    private KeyIntegerManualProvider provider;

    @Before
    public void setup() {
        provider = appContext.getBean(KeyIntegerManualProvider.class);
    }

    @Test
    public void testProvider_OFFSET_1() {

        long start = 21;
        int offset = 1;
        int count = 10;

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