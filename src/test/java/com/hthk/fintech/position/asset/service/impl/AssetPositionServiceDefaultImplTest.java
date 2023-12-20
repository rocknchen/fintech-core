package com.hthk.fintech.position.asset.service.impl;

import com.hthk.common.internet.email.service.impl.EmailServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * @Author: Rock CHEN
 * @Date: 2023/12/19 20:46
 */
public class AssetPositionServiceDefaultImplTest {

    final static Logger logger = LoggerFactory.getLogger(AssetPositionServiceDefaultImplTest.class);

    AssetPositionServiceDefaultImpl service = new AssetPositionServiceDefaultImpl();

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testBuild_ONLY_FutureFX_2BUY_1SELL() {

        logger.info("test");
//        service.build();

    }

}