package com.hthk.fintech.position.asset.service.impl;

import com.hthk.fintech.test.basic.AbstractTradeAppContextTest;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.hthk.fintech.config.FintechStaticData.LOG_WRAP;

/**
 * @Author: Rock CHEN
 * @Date: 2023/12/20 9:05
 */
public class AssetPositionServiceDefaultImplAppContextTest extends AbstractTradeAppContextTest {

    final static Logger logger = LoggerFactory.getLogger(AssetPositionServiceDefaultImplAppContextTest.class);

    AssetPositionServiceDefaultImpl service = appContext.getBean(AssetPositionServiceDefaultImpl.class);

    @Before
    public void setUp() {
//        tradeList = MockerTrade;
    }

    @Test
    public void testBuild_ONLY_FutureFX_2BUY_1SELL() {

//        logger.info(LOG_DEFAULT, tradeList);
        logger.info(LOG_WRAP, "trade", service);
//        logger.info("test");
//        service.build();

    }

}
