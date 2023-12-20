package com.hthk.fintech.position.asset.service.impl;

import com.hthk.common.internet.email.service.impl.EmailServiceImpl;
import com.hthk.fintech.model.finance.trade.ITrade;
import com.hthk.fintech.test.basic.AbstractTradeTest;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.hthk.fintech.config.FintechStaticData.LOG_DEFAULT;
import static com.hthk.fintech.config.FintechStaticData.LOG_WRAP;
import static org.junit.Assert.*;

/**
 * @Author: Rock CHEN
 * @Date: 2023/12/19 20:46
 */
public class AssetPositionServiceDefaultImplTest extends AbstractTradeTest {

    final static Logger logger = LoggerFactory.getLogger(AssetPositionServiceDefaultImplTest.class);

    AssetPositionServiceDefaultImpl service = new AssetPositionServiceDefaultImpl();

    @Before
    public void setUp() throws Exception {


        tradeList = null;
    }

    @Test
    public void testBuild_ONLY_FutureFX_2BUY_1SELL() {

//        logger.info(LOG_DEFAULT, tradeList);
        logger.info(LOG_WRAP, "trade", tradeList);
//        logger.info("test");
//        service.build();

    }

}