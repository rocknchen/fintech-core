package com.hthk.common.utils;

import com.hthk.fintech.test.EventTradeTest;
import org.junit.Before;
import org.junit.Test;

import static com.hthk.fintech.config.FintechStaticData.KW_GET;
import static org.junit.Assert.*;

public class CustomReflectionUtilsTest extends EventTradeTest {

    String fieldName;

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getMethodName_DEFAULT() {

        fieldName = "id";
        String methodName = CustomReflectionUtils.getMethodName(fieldName, KW_GET);
        System.out.println(methodName);
    }
}