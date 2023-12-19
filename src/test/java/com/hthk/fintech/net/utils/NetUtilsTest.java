package com.hthk.fintech.net.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author: Rock CHEN
 * @Date: 2023/12/8 16:37
 */
public class NetUtilsTest {

    private String tmpIP_87 = "168.64.17.87";

    private int gatewayPort87 = 8800;

    private String tmpIP_129 = "168.64.37.129";

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testTELNET_BASIC() {
        NetUtils.telnet(tmpIP_87, gatewayPort87);
    }

}