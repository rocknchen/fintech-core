package com.hthk.fintech.event.dao.impl;

import com.hthk.fintech.enumration.EventTypeTradeEnum;
import com.hthk.fintech.model.event.EventTrade;
import com.hthk.fintech.test.EventTradeTest;
import org.junit.Test;

public class EventDAOLocalFileImplTest extends EventTradeTest {

    private final String DOMAIN = "CALYPSO";

    @Test
    public void testSaveEvent_TRADE_REFRESH_CALYPSO() {

        EventTypeTradeEnum type = EventTypeTradeEnum.REFRESH;

        event = EventTrade.newInstance(DOMAIN, type, currentTime);
        logYML(event);
    }

}